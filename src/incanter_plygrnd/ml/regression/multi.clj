(ns incanter-plygrnd.ml.regression.multi
  (:require [clatrix.core :as cl]
            [clojure.core.matrix :as m]
            [clojure.core.matrix.operators :as M]
            [incanter.charts :as charts]
            [incanter.core :as inct]
            [incanter.stats :as st]
            [clojure.string :as string]
            [clatrix.core :as cl]))

;(ns clj-ml2.multivar
;  (:use [clojure.core.matrix :only [column-count]]
;        [incanter.datasets :only [get-dataset]]
;        [incanter.charts :only [scatter-plot xy-plot add-points]]
;        [incanter.stats :only [linear-model]]
;        [incanter.core :only [view sel to-matrix]])
;  (:require [clatrix.core :as cl]))


(defn select-values [map ks]
  (remove nil? (reduce #(conj %1 (map %2)) [] ks)))

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
            size (read-string (nth content 2))
            bedrooms (read-string (nth content 3))
            bathrms (read-string (nth content 4))
            stories (read-string (nth content 5))]
        {:price pri :lotsize size :bedrooms bedrooms :bathrms bathrms :stories stories})
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
                    (swap! XX conj (select-values parsed [:lotsize :bedrooms :bathrms :stories]))
                    (swap! YY conj (:price parsed)))))
              lines)))))

(parse-tsv-and-create-matrices! "test-resources/ml/housing.csv")

(reset! XX (cl/matrix @XX))
(reset! YY (cl/matrix @YY))

(def housing-lm
  (st/linear-model @YY @XX))

;; number of coefficients
(= (count (:coefs housing-lm)) (+ 1 (m/column-count @XX)))

(defn plot-housing-lm []
  (let [x (range -100 100)
        y (:fitted housing-lm)]
    (inct/view (charts/xy-plot x y :x-label "X" :y-label "Y"))))


(plot-housing-lm)



