(ns ticker-news.news-app
  (:require [ticker-news.model.aggregator :as aggregator]))

(defmulti stories->response
  "Multi-method to support various output formats, currently this app
 only supports html but can be easily extended"
  (fn [stories ticker output-format] output-format))

(defmethod stories->response :default
  [stories ticker output-format]
  (format "Output format %s not supported" output-format))

(defn get-news
  [{:keys [ticker output] :as params}]
  (let [stories           (aggregator/get-aggregated-ticker-news ticker)
        output-format     (if (clojure.string/blank? output) "html" output)]
    (stories->response stories ticker output-format)))