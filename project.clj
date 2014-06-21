(defproject landing "0.1.0"
  :description "A landing page generator"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.1.8"]
                 [ring/ring-jetty-adapter "1.3.0"]
                 [clj-http "0.9.2"]
                 [hiccup "1.0.5"]]
  :plugins [[lein-ring "0.8.11"]]
  :ring {:handler landing/app}
  :uberjar-name "landing-standalone.jar"
  :min-lein-version "2.0.0")
