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


"5 smallest multiple
highest exponent for 2, 3 and all other primes
19 = 19
17 = 17
16 = 2^4
13 = 13
11 = 11
9 = 3^2
7 = 7
5 = 5
"

"6 Sum square difference"
(comment
  (long (- (Math/pow (reduce + (range 1 101)) 2)
           (reduce + (map #(Math/pow % 2) (range 1 101)))
           ))
  )


"7 10001st prime"

(comment

  ;(require '[clojure.core.async :as async :refer :all :exclude [map into reduce merge partition partition-by take]])
  ;
  ;(defn factor? [x y]
  ;  (zero? (mod y x)))
  ;
  ;(defn get-primes [limit]
  ;  (let [primes (chan)
  ;        numbers (to-chan (range 2 limit))]
  ;    (go-loop [ch numbers]
  ;             (when-let [prime (<! ch)]
  ;               (>! primes prime)
  ;               (recur (remove< (partial factor? prime) ch)))
  ;             (close! primes))
  ;    primes))

  (defn primes3 [max]
    (let [enqueue (fn [sieve n factor]
                    (let [m (+ n (+ factor factor))]
                      (if (sieve m)
                        (recur sieve m factor)
                        (assoc sieve m factor))))
          next-sieve (fn [sieve candidate]
                       (if-let [factor (sieve candidate)]
                         (-> sieve
                             (dissoc candidate)
                             (enqueue candidate factor))
                         (enqueue sieve candidate candidate)))]
      (cons 2 (vals (reduce next-sieve {} (range 3 max 2))))))

  (last (sort (primes3 104750)))
  )

"8 Largest product in a series"
(comment

  ;moving window
  (def num (clojure.string/replace
             "73167176531330624919225119674426574742355349194934
             96983520312774506326239578318016984801869478851843
             85861560789112949495459501737958331952853208805511
             12540698747158523863050715693290963295227443043557
             66896648950445244523161731856403098711121722383113
             62229893423380308135336276614282806444486645238749
             30358907296290491560440772390713810515859307960866
             70172427121883998797908792274921901699720888093776
             65727333001053367881220235421809751254540594752243
             52584907711670556013604839586446706324415722155397
             53697817977846174064955149290862569321978468622482
             83972241375657056057490261407972968652414535100474
             82166370484403199890008895243450658541227588666881
             16427171479924442928230863465674813919123162824586
             17866458359124566529476545682848912883142607690042
             24219022671055626321111109370544217506941658960408
             07198403850962455444362981230987879927244284909188
             84580156166097919133875499200524063689912560717606
             05886116467109405077541002256983155200055935729725
             71636269561882670428252483600823257530420752963450" "\n" ""))

  (def nums (map (comp read-string str) (seq num)))


  (reduce max (map #(apply * %) (partition 4 1 nums)))
  (reduce max (map #(apply * %) (partition 13 1 nums)))
  )



"9 triplet"

(for [a (range 1 300)
      b (range a 500)
      c [(- 1000 a b)]
      :when (= (+ (* a a) (* b b)) (* c c))]
  (println a b c))

(for [a (range 5)
      b (range 5)]
  (println a b))

"10 summation of primes"

(reduce + (primes3 2000000))




