(ns ticker-news.providers.provider
  (:require [ticker-news.model.source :as src]
            [ticker-news.providers.yahoo-news :as yn]
            [ticker-news.providers.stocktwits :as st]))

(def registered-sources [{:name yn/source-name} {:name st/source-name}])

(defmulti get-ticker-news
  (fn [ticker source] (:name source)))

(defmethod get-ticker-news :default
  [ticker source]
  (println (format "Source % not supported" (:name source))))

(defmethod get-ticker-news yn/source-name
  [ticker source]
  (yn/get-stories ticker))

(defmethod get-ticker-news st/source-name
  [ticker source]
  (st/get-stories ticker))

(defn get-news 
  [ticker source]
  {:pre [(src/valid-source? source)]}
  (let [stories (get-ticker-news ticker source)
        _       (println (format "returning %d stories from source %s" (count stories) (:name source)))]
    stories))