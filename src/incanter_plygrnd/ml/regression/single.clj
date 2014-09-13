(ns incanter-plygrnd.ml.regression.single
  (:require [clatrix.core :as cl]
            [clojure.core.matrix :as m]
            [clojure.core.matrix.operators :as M]
            [incanter.charts :as charts]
            [incanter.core :as inct]
            [incanter.stats :as st]
            [clojure.string :as string]))

(defn linear-samp-scatter [X Y]
  (charts/scatter-plot X Y))

(defn plot-scatter [X Y]
  (inct/view (linear-samp-scatter X Y)))

(defn samp-linear-model [Y X]
  (st/linear-model Y X))

(defn plot-model [X Y] (inct/view
                         (charts/add-lines (linear-samp-scatter X Y)
                                           X (:fitted (samp-linear-model Y X)))))

(def XX (atom []))
(def YY (atom []))

(defn parse-single-tsv-line
  "read the entire tsv line and return a parsed data structure."
  [^String line]
  ;"Age","Gender","Impressions","Clicks","Signed_In"
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
                    (swap! XX conj (:lotsize parsed))
                    (swap! YY conj (:price parsed)))))
              lines)))))

(parse-tsv-and-create-matrices! "test-resources/ml/housing.csv")
(count @XX)
(count @YY)

(plot-scatter @XX @YY)

(plot-model @XX @YY)

