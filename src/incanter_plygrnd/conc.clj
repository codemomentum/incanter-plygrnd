(ns incanter-plygrnd.conc
  (:require [clojure.core.async :as async :refer :all :exclude [map into reduce merge partition partition-by take]]
            [org.httpkit.client :as http]))


;async io doing multiple http gets
(defn async-get [url result-channel]
  (http/get url #(go (>! result-channel %))))

(time
  (def hk-data
    (let [c (chan)
          res (atom [])]
      ;; fetch em all
      (doseq [i (range 10 100)]
        (async-get (format "http://fssnip.net/%d" i) c))
      ;; gather results
      (doseq [_ (range 10 100)]
        (swap! res conj (<!! c)))
      @res
      )))


(defn poll-fn [interval action]
  (let [seconds (* interval 1000)]
    (go (while true
          (action)
          (<! (timeout seconds))))))

(defmacro poll [interval & body]
  `(let [seconds# (* ~interval 1000)]
     (go (while true
           (do ~@body)
           (<! (timeout seconds#))))))


(poll-fn 10 #(println "polling " (System/currentTimeMillis)))

(poll 3 (println "Polled from macro " (System/currentTimeMillis)))


(defn report-error [response]
  (println "Error" (:status response) "retrieving URL:" (get-in response [:opts :url])))

(defn http-get [url]
  (let [ch (chan)]
    (http/get url (fn [response]
                    (if (= 200 (:status response))
                      (put! ch response)
                      (do (report-error response) (close! ch)))))
    ch))
