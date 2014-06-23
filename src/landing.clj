(ns landing
  (:use [compojure.core]
        [landing.markdown])
  (:require [clj-http.client :as client]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.adapter.jetty :as jetty]
            [landing.templating :as tpl]))

(defn- get-theme []
  (:body (client/get "https://raw.githubusercontent.com/dennis84/landing-theme/gh-pages/index.html")))

(defn- get-readme [user repo]
  (let [token (System/getenv "GITHUB_TOKEN")
        url (str "https://api.github.com/repos/" user "/" repo "/readme?access_token=" token)]
    (:body (client/get url {:accept "application/vnd.github.VERSION.raw"}))))

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
