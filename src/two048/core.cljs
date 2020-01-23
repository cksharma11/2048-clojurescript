(ns two048.core
  (:require [reagent.core :as reagent :refer [atom]]
            [two048.logic :as l]))

(enable-console-print!)

(defonce state (reagent/atom '((0 0 0 0)
                               (2 2 0 0)
                               (4 0 0 2)
                               (0 0 0 0))))

(defn create-cell [cell]
  [:div {:class "cell"} (str cell)])

(defn create-row [row]
  [:div {:class "row"}
   (map create-cell row)])

#(case (.-which %)
   38 (fn [] (swap! state l/move-board-up))
   40 (fn [] (swap! state l/move-board-up))
   37 (fn [] (swap! state l/move-board-left))
   39 (fn [] (swap! state l/move-board-right))
   nil)

(defn board []
  [:div {:class "board" :tabindex -1 :on-key-down #(case (.-which %)
                                                     38 (swap! state l/move-board-up)
                                                     40 (swap! state l/move-board-down)
                                                     37 (swap! state l/move-board-left)
                                                     39 (swap! state l/move-board-right)
                                                     nil)}
   (map create-row @state)])

(reagent/render-component [board]
                          (. js/document (getElementById "app")))
