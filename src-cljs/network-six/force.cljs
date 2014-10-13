(ns network-six.force)

(comment
  (cemerick.austin.repls/exec
    :exec-cmds ["open" "-ga" "/Applications/Google Chrome.app"])
  )

(js/alert "Hello from ClojureScript!")

(defonce app-state (atom {:text "Hello Clojurescript!"}))

