(ns two048.core
	(:require [reagent.core :as reagent :refer [atom]]
						[two048.logic :as l]))

(enable-console-print!)

(defonce state (reagent/atom '((0 0 0 0)
															 (2 2 0 0)
															 (4 0 0 2)
															 (0 0 0 0))))

(defn get-cell-representation [cell]
	(str (if (= 0 cell) "" cell)))

(defn create-cell [cell]
	[:div {:class (str "cell _" cell)} (get-cell-representation cell)])

(defn create-row [row]
	[:div {:class "row"}
	 (map create-cell row)])

(defn board []
	[:div {:class "board" :tabindex -1 :on-key-down
								#(case (.-which %)
									 38 (swap! state (comp l/place-new-cell l/move-board-up))
									 40 (swap! state (comp l/place-new-cell l/move-board-down))
									 37 (swap! state (comp l/place-new-cell l/move-board-left))
									 39 (swap! state (comp l/place-new-cell l/move-board-right))
									 nil)}

	 (map create-row @state)])

(reagent/render-component [board]
													(. js/document (getElementById "app")))
