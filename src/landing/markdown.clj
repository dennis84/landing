(ns landing.markdown
  (:require [clj-http.client :as client]))

(def github-markdown-url
  (let [token (System/getenv "GITHUB_TOKEN")]
    (str "https://api.github.com/markdown/raw?access_token=" token)))

(defn- markdown-to-html [md]
  (:body (client/post github-markdown-url {:content-type "text/x-markdown"
                                           :body md})))

(defn parse-markdown [md]
  (markdown-to-html md))
