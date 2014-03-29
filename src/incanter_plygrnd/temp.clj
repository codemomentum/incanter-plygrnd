(use '(incanter core charts excel))

(with-data (read-xls "http://incanter.org/data/aus-airline-passengers.xls")
           (view $data)
           (let [to-millis (fn [dates] (map #(.getTime %) dates))]
             (view (time-series-plot (to-millis ($ :date)) ($ :passengers))))
           )




