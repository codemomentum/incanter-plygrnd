(use '(incanter core charts excel io))
(require '[clj-time.format :as f])
(require '[clj-time.coerce :as c])

(def fr (f/formatter "dd/MM/yy"))


(def borsa (read-dataset "/Users/halit/Downloads/fiyatendXU100.csv" :delim \; :header true))

(view borsa)

(def borsa-with-milis
  (col-names
    (conj-cols
      ($ :CLOSE borsa)
      ($map #(c/to-long (f/parse fr %)) :DATE borsa))
    [:DATE :CLOSE]))


(view (time-series-plot :DATE :CLOSE :x-label "DATE" :y-label "CLOSE" :data mod-data :points true))






