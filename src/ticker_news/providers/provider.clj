(ns ticker-news.providers.provider
  (:require [ticker-news.model.source :as src]
            [ticker-news.providers.yahoo-news :as yn]))

(def registered-sources [{:name yn/source-name}])

(defmulti get-ticker-news
  (fn [ticker source] (:name source)))

(defmethod get-ticker-news :default
  [ticker source]
  (println (format "Source % not supported" (:name source))))

(defmethod get-ticker-news yn/source-name
  [ticker source]
  (yn/get-ticker-yahoo-news ticker))

(defn get-news 
  [ticker source]
  {:pre [(src/valid-source? source)]}
    (get-ticker-news ticker source))