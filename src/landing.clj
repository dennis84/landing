(ns landing
  (:use [compojure.core]
        [landing.markdown])
  (:require [clj-http.client :as client]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.adapter.jetty :as jetty]
            [landing.templating :as tpl]))

(defn- make-github-url [user repo branch path]
  (str "https://raw.githubusercontent.com/" user "/" repo "/" branch "/" path))

(defn- get-theme []
  (:body (client/get (make-github-url "dennis84" "landing-theme" "gh-pages" "index.html"))))

(defn- get-readme [user repo]
  (:body (client/get (make-github-url user repo "master" "README.md"))))

(defn- homepage [] "Usage: username/repo")

(defn- not-found [] "Not found")

(defn- landing-page [user repo]
  (try
    (tpl/replace-params (get-theme)
      {:title "" :body (parse-markdown (get-readme user repo))})
    (catch Exception e (not-found))))

(defroutes app-routes
  (GET "/" [] (homepage))
  (GET "/:user/:repo" [user repo] (landing-page user repo))
  (route/not-found (not-found)))

(def app (handler/site app-routes))

(defn -main [port]
  (jetty/run-jetty app {:port (Integer. port) :join? false}))
