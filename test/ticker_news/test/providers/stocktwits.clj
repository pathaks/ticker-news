(ns ticker-news.test.providers.stocktwits
  (:use [clojure.test]
        [ticker-news.providers.stocktwits])
  (:require [ticker-news.providers.util :as util]))

(def data (str "{\"messages\":[{\"links\": [{\"title\": \"foo\",\"url\":\"http://foo.bar.com\",\"created_at\":\"2013-10-10T04:33:38Z\"}]}]}"))

(deftest test-get-ticker-news
  (testing "get ticker stories"
           (with-redefs [util/get-url-data-memoized (constantly data)]
             (is (= {:title "foo" :link "http://foo.bar.com" :timestamp 1381404818000} 
                    (first (get-stories "foo")))))))