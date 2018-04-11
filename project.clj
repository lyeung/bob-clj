(defproject bob "0.1.0-SNAPSHOT"
  :description " Experimental buid server in CLJ (elwood-parent reboot)"
  :url "https://github.com/lyeung/bob-clj"
  :license {:name "Apache License Version 2.0"
            :url "https://www.apache.org/licenses/LICENSE-2.0"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [ring/ring-core "1.6.3"]
                 [ring/ring-jetty-adapter "1.6.3"]
                 [ring/ring-json "0.4.0"]
                 [ring-middleware-format "0.7.2"]
                 [metosin/ring-http-response "0.9.0"]]
  :ring {:handler bob.core/main}
  :plugins [[lein-ring "0.12.1"]]
  :main ^:skip-aot bob.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}
             :dev {:dependencies
                   [[ring/ring-devel "1.6.3"]]}})
