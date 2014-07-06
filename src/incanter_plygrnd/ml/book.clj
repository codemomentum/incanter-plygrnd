(ns incanter-plygrnd.ml.book
  (:require [clatrix.core :as cl]
            [clojure.core.matrix :as m]
            [clojure.core.matrix.operators :as M]))

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






