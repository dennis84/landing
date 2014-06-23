(ns landing.templating)

(defn replace-params [text params]
  (clojure.string/replace text 
    #"\{(\w+)}"
    (fn [[k groups]]
      (get params (keyword groups) k))))
