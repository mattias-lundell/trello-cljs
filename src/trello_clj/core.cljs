(ns trello-clj.core
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [markdown.core :refer [md->html]]
            ))

(enable-console-print!)

(def app-state
  (atom
   {:posts [{:name "a" :desc "---\ntest\n---"}
            {:name "b" :desc "- test b"}]}))

(defn title [post]
  (dom/h1 nil (:name post)))

(defn content [post]
  (let [html (md->html (:desc post))]
    (dom/div #js {:dangerouslySetInnerHTML #js {:__html html}} nil)))

(defn post-view [post owner]
  (reify
    om/IRender
    (render [this]
      (apply dom/div nil [(title post) (content post)]))))

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
