(ns ticker-news.model.source)

(comment
  ; source is identified by its name and other source specific details
  {:name "foo-news"
   :blah "more blah"})

(defn valid-source?
  [source]
  (not (clojure.string/blank? (:name source))))