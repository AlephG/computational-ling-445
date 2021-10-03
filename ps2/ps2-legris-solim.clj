
;; Problem 1: define a function `absval`
(defn absval [x] (Math/sqrt (* x x)))


;; Problem 2: fix the functions `take-square` and `sum-of-squares`
(defn take-square [x] (* x x))

(defn sum-of-squares [x y] (+ (take-square x) (take-square y)))


;; Problem 3: define expressions `exp-13-1`, `exp-13-2`, `exp-13-3`, and `exp-13-4`
(def exp-13-1 '(- 15 2))

(def exp-13-2 '(* 3.25 4))

(def exp-13-3 '(/ 26 2))

(def exp-13-4 '(+ 7 6))


;; Problem 4: define a function `third`
(defn third [lst] (first (rest (rest lst))))


;; Problem 5:
;; [let's import define `sqrt` and `abs` from Java.lang.Math for the example]
(defn sqrt [x] (Math/sqrt x))
(defn abs [x] (Math/abs x))

;; define a function `compose` 
(defn compose [f1 f2] (fn [x] (f1 (f2 x))))


;; Problem 6: define a function `first-two`
(defn first-two [lst] (cons (first lst) (cons (first (rest lst)) '())))


;; Problem 7: define a function `remove-second`
(defn remove-second [lst] (cons (first lst) (rest (rest lst))))


;; Problem 8: define a function `add-to-end`
(defn add-to-end [lst x]
  (if (empty? lst)
    (cons x '())
    (cons (first lst) (add-to-end (rest lst) x))))


;; Problem 9: define a function `reverse`
;;           (Note that this funciton will overwrite a built in function. 
;;            This is okay.)
(defn reverse [lst]
  (if (empty? lst)
    '()
    (add-to-end (reverse (rest lst)) (first lst))))


;; Problem 10: define a function `count-to-1`

(defn count-to-1 [n]
  (if (= n 0)
    '()
    (cons n (count-to-1 (- n 1)))))


;; Problem 11: define a function `count-to-n`
(defn count-to-n [n] (reverse (count-to-1 n)))


;; Problem 12: define a function `get-max`
(defn get-max [lst]
  (if (= (count lst) 1)
    (first lst)
    (if (> (first lst) (get-max (rest lst)))
      (first lst)
      (get-max (rest lst)))))


;; Problem 13: define a function `greater-than-five?`
(defn greater-than-five? [lst] (map > lst (repeat (count lst) 5)))


;; Problem 14: define a function `concat-three`
(defn concat-two [x y]
  (if (empty? x)
    y
    (cons (first x) (concat-two (rest x) y))))

(defn concat-three [x y z] (concat-two x (concat-two y z)))


;; Problem 15: define a function `sequence-to-power`
(defn sequence-to-power [seq n]
  (if (= n 0)
    '()
    (if (= n 1)
      seq
      (concat-two seq (sequence-to-power seq (- n 1))))))


;; Problem 16: define a function `in-L-star?`
(defn in-L-star [lst]
  (if (empty? lst)
    true
    (and (= (first lst) 'a) (in-L-star (rest lst)))))



