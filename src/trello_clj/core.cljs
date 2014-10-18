(ns trello-clj.core
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]))

(enable-console-print!)

(def app-state
  (atom
   {:posts [{:name "a" :desc "test a"}
            {:name "b" :desc "test b"}]}))

(defn content [post]
  (:desc post))

(defn post-view [post owner]
  (reify
    om/IRender
    (render [this]
      (apply dom/div nil [(dom/h1 nil (:name post))
                          (dom/p nil (:desc post))]))))

(defn posts-view [app owner]
  (reify
    om/IRender
    (render [this]
      (dom/div nil
               (dom/h1 nil "Posts")
               (apply dom/div nil
                      (om/build-all post-view (:posts app)))))))

(om/root posts-view app-state
         {:target (. js/document (getElementById "app"))})
