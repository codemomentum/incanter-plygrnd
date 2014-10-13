(defproject incanter-plygrnd "0.1.0-SNAPSHOT"
            :description "FIXME: write description"
            :url "http://example.com/FIXME"
            :license {:name "Eclipse Public License"
                      :url  "http://www.eclipse.org/legal/epl-v10.html"}
            :dependencies [
                            [org.clojure/clojure "1.6.0"]
                            [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                            [http-kit "2.1.16"]


                            [incanter/incanter-core "1.5.4"]
                            [incanter/incanter-io "1.5.4"]
                            [incanter/incanter-charts "1.5.4"]
                            [incanter/incanter-excel "1.5.4"]
                            [clj-time "0.7.0"]

                            [org.clojure/math.numeric-tower "0.0.4"]

                            [clatrix "0.3.0"]
                            [net.mikera/core.matrix "0.26.0"]

                            ;[incanter "1.5.5"]

                            [org.clojure/clojurescript "0.0-2371"]

                            ;mcda
                            [me.raynes/fs "1.4.4"]

                            ]

            :plugins [[lein-cljsbuild "1.0.3"]]

            :resource-paths ["test" "test-resources"]

            :cljsbuild {:builds
                         [{:source-paths ["src-cljs"],
                           :compiler     {:pretty-printer true,
                                          :output-to      "www/js/main.js",
                                          :optimizations  :whitespace
                                          :source-map     true}}]}

            :profiles {:dev {:plugins [[com.cemerick/austin "0.1.5"]]}}
            )
