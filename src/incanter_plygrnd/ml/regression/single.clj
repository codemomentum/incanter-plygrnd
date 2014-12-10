(ns incanter-plygrnd.ml.regression.single
  (:require [incanter.charts :as charts]
            [incanter.core :as inct]
            [incanter.stats :as st]
            [clojure.string :as string]))

(def X (atom []))
(def Y (atom []))

(defn parse-single-tsv-line
  "read the entire tsv line and return a parsed data structure."
  [^String line]
  (let [content (string/split line #"\,")
        content-count (count content)]
    (if (and (= 13 content-count) (not (.startsWith line "\"")))
      (let [pri (read-string (nth content 1))
            size (read-string (nth content 2))]
        {:price pri :lotsize size})
      nil)))

(defn parse-tsv-and-create-matrices!
  "given the path to the tsv-file, create a sequence of of the lines and parse them based on the indexing config"
  [tsv-path]
  (with-open [rdr (clojure.java.io/reader tsv-path)]
    (let [lines (line-seq rdr)]
      (dorun
        (pmap #(let [parsed (parse-single-tsv-line %1)]
                (if-not (nil? parsed)
                  (do
                    (swap! X conj (:lotsize parsed))
                    (swap! Y conj (:price parsed)))))
              lines)))))

(parse-tsv-and-create-matrices! "test-resources/ml/housing.csv")
(count @X)
(count @Y)

(defn make-scatter-plot-chart [X Y]
  (charts/scatter-plot X Y))

(defn display [X Y]
  (inct/view (make-scatter-plot-chart X Y)))

(defn ols-linear-model [Y X]
  (st/linear-model Y X))

(defn plot-model [X Y] (inct/view
                         (charts/add-lines (make-scatter-plot-chart X Y)
                                           X (:fitted (ols-linear-model Y X)))))

(display @X @Y)

(plot-model @X @Y)

