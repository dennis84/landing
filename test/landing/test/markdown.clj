(ns landing.test.markdown
  (:use [clojure.test]
        [clojure.string :only (join)]
        [hiccup.page :only (html5)]
        [landing.markdown :only (parse-markdown)]))

(def markdown (join "\n" ["# Foo" "blah" "## Bar" "blub"]))

(def html (str "<!DOCTYPE html>\n<html>"
               "<section><div><h1>Foo</h1><p>blah</p></div></section>"
               "<section><div><h2>Bar</h2><p>blub</p></div></section></html>"))

(deftest foo
  (is (= html (html5 (parse-markdown markdown)))))
