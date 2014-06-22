(ns landing.test.templating
  (:use [clojure.test]
        [landing.templating]))

(deftest replace-params-test
  (is (= "foo bar baz" (replace-params "foo {bar} baz" {:bar "bar"}))))
