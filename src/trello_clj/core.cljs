(ns trello-clj.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [markdown.core :refer [md->html]]
            [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]))

(enable-console-print!)

(def url "https://api.trello.com/1/lists/53e54ef3fd6ad89ffbd9c55e/cards?key=95172e308ff513379c78db7941798325")

(def app-state
  (atom
   {:posts []}))

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
    om/IWillMount
    (will-mount [this]
      (go (let [response (<! (http/get url {:with-credentials? false}))]
            (swap! app-state assoc :posts (:body response)))))
    om/IRender
    (render [this]
      (dom/div nil
               (dom/h1 nil "Posts")
               (apply dom/div nil
                      (om/build-all post-view (:posts app)))))))

(om/root posts-view app-state
         {:target (. js/document (getElementById "app"))})
