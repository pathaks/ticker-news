(ns ticker-news.news-html-provider
  (:require [hiccup.page :as p]
            [ticker-news.yahoo-news-provider :as y]
            [ticker-news.story :as s]))

(defn ^:private story->div-element
  [story]
  (let [link (:link story)
        link-idx (-> link (.indexOf "*")) ; actual article link is after *
        article-link (if (> link-idx 0) 
                       (-> link (.substring (+ link-idx 1)))
                       link)
        article-domain-idx (-> article-link (.indexOf "/" 8)) ; 8 should cover http[s]://
        display-link (if (> article-domain-idx 0) 
                       (-> article-link (.subSequence 0 article-domain-idx))
                       article-link)
        hostname (clojure.string/replace display-link #"http(s*)://" "")] 
  [:div {:class "span8"}
   [:h5 [:a {:href (:link story)} (:title story)]]
   [:cite hostname [:span (format " (%s) " (s/story->date story))]]
   [:hr]]))

(defn get-news-html
  "Get latest news for a ticker symbol"
  [ticker]
  (let [stories (y/get-ticker-stories ticker)
        _       (println (format "found %s stories for %s" (count stories) ticker))]
    (if (empty? stories)
      (p/html5 [:html (format "No news found for the ticker symbol \"%s\"" ticker)])
      (p/html5 [:html 
                [:head
                 [:title (format "News for %s" ticker)]
                 [:link {:rel "stylesheet" :type "text/css" :href "//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css"}]] 
                [:body 
                 [:div {:class "jumbotron"}
                  [:div {:class "container"}
                   [:h2 {:align "left"} (format "News for ticker %s" ticker)]
                   ]]
                 [:div {:class "container"}
                  [:div {:class "row"}
                   (for [story stories] 
                     (story->div-element story))]]
                 [:script {:src "//netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"}]
                 [:script {:src "http://code.jquery.com/jquery-1.10.1.min.js"}]]]))))

(defn get-form-page
  []
  (p/html5 [:html
           [:body
            [:form {:method "post"} "Enter ticker symbol " 
             [:input {:type "text" :name "ticker"}]
             [:input {:type "submit"}]]]]))