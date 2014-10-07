(ns incanter-plygrnd.math.elr)


"1 - multiples od 3 and 5"
(comment
  (reduce + (filter #(or (= 0 (mod % 3)) (= 0 (mod % 5))) (range 10)))
  (reduce + (filter #(or (= 0 (mod % 3)) (= 0 (mod % 5))) (range 1000)))
  )

"2 Fibonacci"
(comment
  (defn fibn [one two upper]
    (println "calling" one two upper)
    (if (<= one upper)
      (concat [one] (fibn two (+ one two) upper))
      )
    )

  (reduce + (filter even? (fibn 1 2 4000000)))
  )


"3 Largest prime factor"
(comment
  (def primes (atom []))

  (defn prime-factor [bignum]
    (let [root (num (int (Math/sqrt bignum)))
          lst (range 2 root)]
      (map (fn [num] (do
                       (if (= 0 (mod bignum num))
                         (swap! primes conj num))
                       )) lst)
      )
    )

  (defn any? [l]
    (reduce #(or %1 %2) l))

  (defn prime? [n]
    (if (even? n) false
                  (let [root (num (int (Math/sqrt n)))]
                    (loop [i 3]
                      (if (> i root) true
                                     (if (zero? (mod n i)) false
                                                           (recur (+ i 2))))))))

  (prime-factor 600851475143)
  @primes
  (filter prime? @primes)
  )


"4 Largest palindrome product"
(comment
  (defn is-palindrome? [num]
    ;(println "is-palindrome?" num)
    (= (str num) (clojure.string/reverse (str num))))

  (def palindromes (atom []))

  (loop [num1 999]
    (loop [num2 999]
      ;(println num1 num2)
      (when (> num2 99)
        (if (is-palindrome? (* num1 num2))
          (swap! palindromes conj (* num1 num2))
          (recur (dec num2))))
      )
    (if (> num1 99)
      (recur (dec num1)))
    )
  (reduce max @palindromes)
  )



