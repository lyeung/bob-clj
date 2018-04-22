(defproject bob "0.1.0-SNAPSHOT"
  :description " Experimental buid server in CLJ (elwood-parent reboot)"
  :url "https://github.com/lyeung/bob-clj"
  :license {:name "Apache License Version 2.0"
            :url "https://www.apache.org/licenses/LICENSE-2.0"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/tools.logging "0.4.0"]
                 [compojure "1.6.0"]
                 [com.taoensso/carmine "2.18.1"]
                 [cprop "0.1.11"]
                 [environ "1.1.0"]
                 [metosin/ring-http-response "0.9.0"]
                 [mount "0.1.12"]
                 [org.clojure/tools.logging "0.4.0"]
                 [ring/ring-core "1.6.3"]
                 [ring/ring-jetty-adapter "1.6.3"]
                 [ring-middleware-format "0.7.2"]
                 [org.clojure/core.async "0.4.474"]]
  :ring {:handler bob.core/main}
  :plugins [[lein-ring "0.12.1"]
            [lein-environ "1.1.0"]]
  :main ^:skip-aot bob.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :source-paths ["env/prod/clj"]
                       :resource-paths ["env/prod/resources"]}
             :dev [:project/dev :profiles/dev]
             :test [:project/test :profiles/test]
             :project/dev {:dependencies
                           [[ring/ring-devel "1.6.3"]]
                           :source-paths ["env/dev/clj"]
                           :resource-paths ["env/dev/resources"]}
             :profiles/dev {}
             :project/test {:resource-paths ["env/test/resources"]}
             :profiles/test {}
             }
  )
