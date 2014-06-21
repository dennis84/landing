(ns landing
  (:use [compojure.core]
        [landing.markdown])
  (:require [clj-http.client :as client]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [landing.view :as view]))

(defn- make-github-url [user repo path]
  (str "https://raw.githubusercontent.com/" user "/" repo "/master/" path))

(defn landing-page [user repo]
  (let [get-file (partial make-github-url user repo)
        resp (client/get (get-file "README.md"))
        markdown (:body resp)
        html (parse-markdown markdown)]
    (view/landing-page html)))

(defroutes app-routes
  (route/resources "/" {:root ""})
  (GET "/:user/:repo" [user repo] (landing-page user repo))
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
