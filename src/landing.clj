(ns landing
  (:use [compojure.core]
        [landing.markdown])
  (:require [clj-http.client :as client]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.adapter.jetty :as jetty]
            [ring.util.response :as resp]
            [landing.templating :as tpl]))

(defn- get-theme []
  (:body (client/get "https://raw.githubusercontent.com/dennis84/landing-theme/gh-pages/index.html")))

(defn- get-readme [user repo]
  (let [token (System/getenv "GITHUB_TOKEN")
        url (str "https://api.github.com/repos/" user "/" repo "/readme?access_token=" token)]
    (:body (client/get url {:accept "application/vnd.github.VERSION.raw"}))))

(defn- homepage []
  (resp/resource-response "index.html" {:root ""}))

(defn- landing-page [user repo]
  (try
    (tpl/replace-params (get-theme)
      {:user user :repo repo :body (parse-markdown (get-readme user repo))})
    (catch Exception e (homepage))))

(defroutes app-routes
  (GET "/" [] (homepage))
  (GET "/:user/:repo" [user repo] (landing-page user repo))
  (route/resources "/" {:root ""})
  (route/not-found (homepage)))

(def app (handler/site app-routes))

(defn -main [port]
  (jetty/run-jetty app {:port (Integer. port) :join? false}))
