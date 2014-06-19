(defproject landing "0.1.0"
  :description "A landing page generator"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.1.8"]
                 [clj-http "0.9.2"]
                 [hiccup "1.0.5"]
                 [endophile "0.1.2"]
                 [markdown-clj "0.9.44"]]
  :plugins [[lein-ring "0.8.11"]]
  :ring {:handler landing/app})
