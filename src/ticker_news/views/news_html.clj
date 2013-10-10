(ns ticker-news.views.news-html
  (:require [hiccup.page :as p]
            [ticker-news.news-app :as app]
            [ticker-news.model.story :as s]))

(defn ^:private story->div-element
  [story]
  (let [link         (:link story)
        domain-idx   (-> link (.indexOf "/" 8)) ; 8 should cover http[s]://
        display-link (if (> domain-idx 0) 
                       (-> link (.subSequence 0 domain-idx))
                       link)
        hostname     (clojure.string/replace display-link #"http(s*)://" "")] 
  [:div {:class "span12"}
   [:h5 [:a {:href (:link story)} (:title story)]]
   [:cite hostname [:span (format " (%s) " (s/story->date story))]]
   [:hr]]))

(defn get-news-html
  [stories ticker]
  (if (empty? stories)
    nil
    (p/html5 [:html 
              [:head
               [:title (format "News for %s" ticker)]
               [:link {:rel "stylesheet" :type "text/css" :href "//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css"}]] 
              [:body 
               [:div {:class "jumbotron"}
                [:h1 {:align "left"} "News for " [:font {:color "green"} ticker]]
                [:div {:class "container"}
                 ]]
               [:div {:class "container"}
                [:div {:class "row"}
                 (for [story stories] 
                   (story->div-element story))]]
               [:script {:src "//netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"}]
               [:script {:src "http://code.jquery.com/jquery-1.10.1.min.js"}]]])))

(defn get-form-page
  []
  (p/html5 [:html
            [:head
             [:link {:rel "stylesheet" :type "text/css" :href "//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css"}]] 
            [:body
             [:div {:class "container"}
              [:div {:class "row"}
               [:div {:class "span4"}
                [:nbsp]]
               [:div {:class "span8"}
                [:br][:br]
                [:form {:method "post"}
                 [:input {:type "search" :name "ticker" :placeholder "nflx"}]
                 [:input {:type "submit"}]
                 ]]
               ]]
             [:script {:src "//netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"}]
             [:script {:src "http://code.jquery.com/jquery-1.10.1.min.js"}]
             ]]))


(defmethod app/stories->response "html"
  [stories ticker output-format]
  (get-news-html stories ticker))
