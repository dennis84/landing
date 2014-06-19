(ns landing
  (:use [compojure.core]
        [markdown.core :only (md-to-html-string)]
        [hiccup.page :only (html5 include-js include-css)])
  (:require [clj-http.client :as client]
            [compojure.handler :as handler]
            [compojure.route :as route]))

(defn- make-github-url [user repo path]
  (str "https://raw.githubusercontent.com/" user "/" repo "/master/" path))

(defn- make-landing-page [html]
  (html5
    [:head [:title ""]
           (include-css "/css/index.css")
           (include-js "/js/landing.js")]
    [:body html]))

(defn landing-page [user repo]
  (let [get-file (partial make-github-url user repo)
        resp (client/get (get-file "README.md"))
        markdown (:body resp)
        html (md-to-html-string markdown)]
    (make-landing-page html)))

(defroutes app-routes
  (route/resources "/" {:root ""})
  (GET "/:user/:repo" [user repo] (landing-page user repo))
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
