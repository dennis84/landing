(ns landing.markdown
  (:use [pl.danieljanus.tagsoup :only (parse-string tag children)])
  (:import [org.pegdown PegDownProcessor Extensions]))

(defn- make-partitions [xs]
  (partition-by #(or (= :h1 (tag %)) (= :h2 (tag %))) xs))

(defn- make-sections [xs]
  (for [[a b] (partition 2 xs)](concat a b)))

(defn- make-hiccup-sections [nodes]
  (->> nodes
       make-partitions
       make-sections
       (map #(vector :section {} (vector :div {} %)))))

(defn parse-markdown [md]
  (let [processor (PegDownProcessor. Extensions/ALL)
        html (.markdownToHtml processor md)]
    (-> html
        parse-string
        children
        first
        children
        make-hiccup-sections)))
