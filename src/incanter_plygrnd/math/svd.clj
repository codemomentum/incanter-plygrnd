(ns incanter-plygrnd.math.svd
  (:require [incanter-plygrnd.ml.regression.multi :as lr]
            [clatrix.core :as cl]
            [incanter.core :as inc]))

(def XX (atom nil))

(lr/parse-tsv-and-create-matrices! "test-resources/ml/housing.csv")



(reset! XX (cl/matrix @lr/XX))

@XX

(inc/decomp-svd @XX)


