(ns ticker-news.handler
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [ticker-news.news-app :as app]
            [ticker-news.views.news-html :as news-html]))

(defroutes app-routes
  
  (GET "/" [] (news-html/get-form-page))
  
  (POST "/" {params :params} (app/get-news params))
  
  (GET "/news" {params :params} (app/get-news params))
  
  (route/resources "/" "/news")
  
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
