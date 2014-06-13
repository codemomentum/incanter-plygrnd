(ns incanter-plygrnd.ml.knn)
;http://pages.cs.wisc.edu/~andrzeje/research/

; Standard k nearest neighbors classifier
; Does not handle
;   > 2 classes
;   even values of k
;   any bad input, really
;
; David Andrzejewski (andrzeje@cs.wisc.edu)
;
; Command-line arguments
; 0 training data filename
; 1 test data filename
; 2 value of k (how many neighbors to use)
;
; EX: clj knn.clj train.txt test.txt 3
;
; Data file format is one example per line, with
; space-separated vector values. By convention, the
; first element of each vector is the class label.
;

(require '[clojure.math.numeric-tower :as m])

(defstruct example :label :data)

(defn euclidean-distance
  "L2 distance between 2 vectors
  http://en.wikipedia.org/wiki/Euclidean_distance"
  [v1 v2]
  (m/sqrt (reduce + (map #(m/expt (- %1 %2) 2) v1 v2))))

(euclidean-distance [4 4 2] [4 4 11])

(defn parse-line
  "parse a line of text-separated numbers"
  [line]
  (map #(Float/parseFloat %1) (.split line " ")))

(defn read-vector-file
  "one space-separated vector per line, where the
  first element of each vector is the label"
  [filename]
  (for [line (line-seq (clojure.java.io/reader filename))]
    (#(struct example (first %1) (rest %1)) (parse-line line)))
  )

(defn value-count
  "how many times does val occur in values?"
  [values val]
  (count (filter (partial == val) values)))

(defn majority-label
  "what is the majority label among this set of examples?"
  [examples]
  (let [labels (map :label examples)]
    (last (sort-by (partial value-count labels) (set labels)))))

(defn nearest-neighbors
  "return the k nearest neighbors"
  [example dataset k]
  (take k (sort-by #(euclidean-distance (:data example)
                                        (:data %1)) dataset)))

(defn classify-test
  "classify each testset example"
  [trainset testset k]
  (for [testex testset]
    (majority-label (nearest-neighbors testex trainset k))))

(defn distance-matrix
  "all pairwise distances as a vector of vectors"
  [vectors]
  (vec (for [v vectors] (map (partial euclidean-distance v) vectors))))


(let [trainfile "test-resources/ml/knn-train.txt"
      testfile "test-resources/ml/knn-test.txt"
      k 3]
  (doseq [[i label] (map-indexed vector (classify-test (read-vector-file trainfile)
                                                    (read-vector-file testfile) k))]
    (println (format "Test example %d classified as %s" i (int label)))))