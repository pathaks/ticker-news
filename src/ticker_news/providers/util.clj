(ns ticker-news.providers.util
  (:require [clojure.core.memoize :as memo]
            [clojure.xml :as xml]))

(defn get-url-xml-data
  "Uses clojure.xml/parse to get xml/html data for a URL,
 returns a tree of struct-map"
  [url]
  (let [_ (println (format "fetching data for %s" url))]
   (xml/parse url)))

(def get-url-xml-data-memoized (memo/ttl get-url-xml-data :ttl/threshold 600000)) ; cache data for 600s

(defn get-url-data
  "Returns a string of URL's content"
  [url]
  (let [_ (println (format "fetching data for %s" url))]
   (slurp url)))

(def get-url-data-memoized (memo/ttl get-url-data :ttl/threshold 600000)) ; cache data for 600s