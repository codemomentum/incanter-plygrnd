(ns network-six.force)

(js/alert "Hello from ClojureScript!")

(defonce app-state (atom {:text "Hello Clojurescript!"}))

(defn main []
  (om/root
    (fn [app owner]
      (reify
        om/IRender
        (render [_]
          (dom/h1 {{#not-om-tools?}}nil {{/not-om-tools?}}(:text app)))))
app-state
{:target (. js/document (getElementById "app"))}))
