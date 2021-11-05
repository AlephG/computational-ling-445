(comment "Make sure that you follow the instructions carefully
          and use the right procedure names, inputs, and outputs")

(defn square [n] (* n n))

(defn mean [a] (/ (reduce + a) (count a)))

(defn standarddev [a]
  (Math/sqrt (/
              (reduce + (map square (map - a (repeat (mean a)))))
              (- (count a) 1))))

;;Preliminaries
(def vocabulary '(call me ishmael))
(def theta1 (list (/ 1 2) (/ 1 4) (/ 1 4)))
(def theta2 (list (/ 1 4) (/ 1 2) (/ 1 4)))
(def thetas (list theta1 theta2))
(def theta-prior (list (/ 1 2) (/ 1 2)))

(defn score-categorical [outcome outcomes params]
  (if (empty? params)
    (print "no matching outcome")
    (if (= outcome (first outcomes))
      (first params)
      (score-categorical outcome (rest outcomes) (rest params)))))

(defn list-foldr [f base lst]
  (if (empty? lst)
    base
    (f (first lst)
       (list-foldr f base (rest lst)))))

(defn log2 [n]
  (/ (Math/log n) (Math/log 2)))

(defn score-BOW-sentence [sen probabilities]
  (list-foldr
   (fn [word rest-score]
     (+ (log2 (score-categorical word vocabulary probabilities))
        rest-score))
   0
   sen))

(defn score-corpus [corpus probabilities]
  (list-foldr
   (fn [sen rst]
     (+ (score-BOW-sentence sen probabilities) rst))
   0
   corpus))

(defn logsumexp [log-vals]
  (let [mx (apply max log-vals)]
    (+ mx
       (log2
        (apply +
               (map (fn [z] (Math/pow 2 z))
                    (map (fn [x] (- x mx)) log-vals)))))))

(def my-corpus '((call me)
                 (call ishmael)))

;; (score-categorical theta1 thetas theta-prior)

;;Problem 1: define `theta-corpus-joint`
(defn theta-corpus-joint [theta corpus theta-probs]
  (+ (score-corpus corpus theta)
     (log2 (score-categorical theta thetas theta-probs))))

;;(Math/pow 2 (theta-corpus-joint theta2 my-corpus theta-prior))

;;Problem 2: define `compute-marginal`
(defn compute-marginal [corpus theta-probs]
  (logsumexp (list (theta-corpus-joint theta1 corpus theta-probs)
                   (theta-corpus-joint theta2 corpus theta-probs))))

;;(Math/pow 2 (compute-marginal my-corpus theta-prior))

;;Problem 3: define `compute-conditional-prob`
(defn compute-conditional-prob [theta corpus theta-probs]
  (- (theta-corpus-joint theta corpus theta-probs)
     (compute-marginal corpus theta-probs)))

;;(compute-conditional-prob theta1 '((call me ishmael)) '(1/4 3/4))
;;(compute-conditional-prob theta2 my-corpus theta-prior)

;;Problem 4: define `compute-conditional-dist`
(defn compute-conditional-dist [corpus theta-probs]
  (list (compute-conditional-prob theta1 corpus theta-probs)
        (compute-conditional-prob theta2 corpus theta-probs)))

;;(def dist (compute-conditional-dist my-corpus theta-prior))
;;(Math/pow 2 (first dist))
;;(Math/pow 2 (second dist))

;;Problem 6: define `compute-posterior-predictive`

(defn compute-posterior-predictive [observed-corpus new-corpus theta-probs]
  (let [conditional-dist (compute-conditional-dist observed-corpus theta-probs)]
    (compute-marginal new-corpus (map (fn [p] (Math/pow 2 p)) conditional-dist))))

;;(Math/pow 2 (compute-posterior-predictive my-corpus my-corpus theta-prior))

;Problem 7: define `sample-BOW-corpus`
(defn normalize [params]
  (let [sum (apply + params)]
    (map (fn [x] (/ x sum)) params)))

(defn flip [weight]
  (if (< (rand 1) weight)
    true
    false))

(defn sample-categorical [outcomes params]
  (if (flip (first params))
    (first outcomes)
    (sample-categorical (rest outcomes)
                        (normalize (rest params)))))

(defn sample-BOW-sentence [len probabilities]
  (if (= len 0)
    '()
    (cons (sample-categorical vocabulary probabilities)
          (sample-BOW-sentence (- len 1) probabilities))))

(defn sample-BOW-corpus [theta sent-len corpus-len]
  (repeatedly corpus-len (fn [] (sample-BOW-sentence sent-len theta))))

;;(sample-BOW-corpus theta1 3 4)

;;Problem 8
(defn sample-theta-corpus [sent-len corpus-len theta-probs]
  (let [theta (sample-categorical thetas theta-probs)]
    (list theta (sample-BOW-corpus theta sent-len corpus-len))))

;;(sample-theta-corpus 2 2 theta-prior)


;;Problem 9: define `estimate-corpus-marginal`
(defn get-theta [theta-corpus]
  (first theta-corpus))

(defn get-corpus [theta-corpus]
  (first (rest theta-corpus)))


; ;uncomment the following after you have defined `sample-theta-corpus` above
(defn sample-thetas-corpora [sample-size sent-len corpus-len theta-probs]
  (repeatedly sample-size (fn [] (sample-theta-corpus sent-len corpus-len theta-probs))))

;;(sample-thetas-corpora 2 2 2 theta-prior)

(defn count-corpora [corpus pairs n]
  (if (empty? pairs)
    n
    (if (= (get-corpus (first pairs)) corpus)
      (count-corpora corpus (rest pairs) (+ n 1))
      (count-corpora corpus (rest pairs) n))))


(defn estimate-corpus-marginal [corpus sample-size sent-len corpus-len theta-probs]
  (/
   (count-corpora corpus (sample-thetas-corpora sample-size sent-len corpus-len theta-probs) 0)
   sample-size))

;;(defn std-mean-marginal [sample-size] (let [sample (repeatedly 100 (fn [] (estimate-corpus-marginal my-corpus sample-size 2 2 theta-prior)))]
                               ;;(list (float (mean sample)) (standarddev sample))))

;;(std-mean-marginal 50)

;;(std-mean-marginal 10000)

;;Problem 11: define `rejection-sampler`
(defn get-count [obs observation-list count]
  (if (empty? observation-list)
    count
    (if (= obs (first observation-list))
      (get-count obs (rest observation-list) (+ 1 count))
      (get-count obs (rest observation-list) count))))

(defn get-counts [outcomes observation-list]
  (let [count-obs (fn [obs] (get-count obs observation-list 0))]
    (map count-obs outcomes)))


(defn rejection-sampler
  [theta observed-corpus sample-size sent-len corpus-len theta-probs]
  (let [observation-list (sample-thetas-corpora sample-size sent-len corpus-len theta-probs)
        theta-counts (get-count (list theta observed-corpus) observation-list 0)
        obs-count (get-count observed-corpus (map get-corpus observation-list) 0)]
    (if (= 0 obs-count)
      nil
      (/ theta-counts obs-count))))

;(/ (apply + (repeatedly 100 (fn [] (rejection-sampler theta1 my-corpus 500 2 2 theta-prior)))) 100)


;; (rejection-sampler theta1 my-corpus 10000 2 2 theta-prior)


;; (defn std-mean [sample-size] (let [sample (remove nil? (repeatedly 100 (fn [] (rejection-sampler theta1 my-corpus sample-size 2 2 theta-prior))))]
;;                                (list (float (mean sample)) (standarddev sample))))
;; (std-mean 100)

;; (std-mean 500)

;; (std-mean 1000)

;; (std-mean 5000)

;; (std-mean 20000)