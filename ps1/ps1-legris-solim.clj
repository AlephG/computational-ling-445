;; Problem 1
;; Define a variable `year`
(def year 2021)


;; Problem 5
;; Define a function `add-up`
(defn add-up [x y] (+ x y))


;; Problem 6
;; Define a function `is-it-four?` 
;;   (Note: Don't forget the question mark in the name!)
(defn is-it-four? [x] (= 4 x))


;; Problem 7
; add your code in the indicated space below,
; (so that the symbol `problem-7` evaluates to `true`)
(def problem-7
  (= (quote platypus) 'platypus))


;; Problem 8
;; Define a function `func` and an expression `expr` 
(defn func [x] '3)


;; Problem 9
;; Define a function `both-same-type?`
;;   (Note: Don't forget the question mark in the name!)
(defn both-same-type? [a b] (= (type a) (type b)))


;; Problem 10
;; Define a function `list-longer-than?`
;;   (Note: Don't forget the question mark in the name!)
(defn list-longer-than? [n lst] (> (count lst) n))


;; Problem 11
;; Define a function `dot-product`
(defn dot-product [x y] (apply + (map * x y)))


;; Problem 12
;; Define a function `swap-arg-order`
(defn swap-arg-order [func] (fn [& args] (apply func (reverse args))))
;; The answer to this problem was found using insights from 
;; https://stackoverflow.com/questions/35541142/flip-order-of-passed-arguments


;; Problem 13
;; Define a higher order function `g`
(defn g [func] (apply func '(10)))