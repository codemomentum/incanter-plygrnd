(ns incanter-plygrnd.ml.book
  (:require [clatrix.core :as cl]
            [clojure.core.matrix :as m]
            [clojure.core.matrix.operators :as M]
            [incanter.charts :as charts]
            [incanter.core :as inct]
            [incanter.stats :as st]))

(def A (cl/matrix [[0 1 2] [3 4 5]]))
(def B (cl/matrix [[0 0 0] [0 0 0]]))
(def C (cl/matrix [[1 1 1] [1 1 1]]))
(def D (cl/matrix [[1 1 1] [1 1 1] [1 1 1]]))
(def E (cl/matrix [[0 1 2] [3 4 5] [6 7 8]]))

(m/pm A)


(defn square-mat
  "Creates a square matrix of size n x n
  whose elements are all e"
  [n e]
  (let [repeater #(repeat n %)]
    (cl/matrix (-> e repeater repeater))))

(defn id-mat
  "Creates an identity matrix of n x n size"
  [n]
  (let [init (square-mat n 0)
        identity-f (fn [i j n]

                     (if (= i j) 1 n))]
    (cl/map-indexed identity-f init)))

(defn lmatrix [n]
  (m/compute-matrix :clatrix [n (+ n 2)]
                    (fn [i j] ({0 -1, 1 2, 2 -1} (- j i) 0))))



;linear regression
(def X (cl/matrix [8.401 14.475 13.396 12.127 5.044
                   8.339 15.692 17.108 9.253 12.029]))
(def Y (cl/matrix [-1.57 2.32 0.424 0.814 -2.3
                   0.01 1.954 2.296 -0.635 0.328]))


(def linear-samp-scatter
  (charts/scatter-plot X Y))

(defn plot-scatter []
  (inct/view linear-samp-scatter))

(plot-scatter)
;view humidity, rainfall plot


(def samp-linear-model
  (st/linear-model Y X))

(:coefs samp-linear-model)
(:sse samp-linear-model)
(:r-square samp-linear-model)

(defn plot-model [] (inct/view
                      (charts/add-lines samp-scatter-plot
                                        X (:fitted linear-samp-scatter))))

(plot-model)


;simpilifed GD
(def gradient-descent-precision 0.001)
(defn gradient-descent
  "Find the local minimum of the cost function's plot"
  [F' x-start step]
  (loop [x-old x-start]
    (let [x-new (- x-old
                   (* step (F' x-old)))
          dx (- x-new x-old)]
      (if (< dx gradient-descent-precision)
        x-new
        (recur x-new)))))





