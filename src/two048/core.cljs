(ns two048.core
	(:require [reagent.core :as reagent :refer [atom]]
						[two048.logic :as l]))

(enable-console-print!)

(defonce state (reagent/atom '((0 0 0 0)
															 (2 2 0 0)
															 (4 0 0 2)
															 (0 0 0 0))))

(defn create-cell [cell]
	[:div {:class (str "cell _" cell)} (str cell)])

(defn create-row [row]
	[:div {:class "row"}
	 (map create-cell row)])

(defn board []
	[:div {:class "board" :tabindex -1 :on-key-down #(case (.-which %)
																										 38 (swap! state (comp l/move-board-up l/place-new-cell))
																										 40 (swap! state (comp l/move-board-down l/place-new-cell))
																										 37 (swap! state (comp l/move-board-left l/place-new-cell))
																										 39 (swap! state (comp l/move-board-right l/place-new-cell))
																										 nil)}
	 (map create-row @state)])

(reagent/render-component [board]
													(. js/document (getElementById "app")))
