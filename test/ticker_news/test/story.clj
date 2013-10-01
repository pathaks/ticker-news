(ns ticker-news.test.story
  (:use clojure.test
        ticker-news.story))

(deftest test-valid-story?
  (testing "valid-story?"
           (let [valid-story {:title "foo" :link "http://foo.bar.com" :timestamp 1234}
                 bad-story (dissoc valid-story :link)
                 invalid-story {:title "" :link "http://foo.bar.com" :timestamp 1234}]
             (is (valid-story? valid-story))
             (is (thrown? java.lang.AssertionError (valid-story? bad-story)))
             (is (not (valid-story? invalid-story))))))