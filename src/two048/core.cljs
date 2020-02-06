(ns two048.core
 (:require [reagent.core :as reagent :refer [atom]]
					 [two048.logic :as l]))

(enable-console-print!)

(def initial-state {:board '((0 0 0 0)
														 (2 2 0 0)
														 (0 0 0 2)
														 (0 0 0 0))
										:over  false})

(defonce state (reagent/atom initial-state))

(defn get-cell-representation [cell]
 (str (if (= 0 cell) "" cell)))

(defn create-cell [cell]
 [:div {:class (str "cell _" cell)} (get-cell-representation cell)])

(defn create-row [row]
 [:div {:class "row"}
	(map create-cell row)])

(defn update-state [board]
 (if (l/game-over? board)
	{:board board :over true}
	{:board (l/place-new-cell board) :over false}))

(defn move-up [state]
 (update-state (l/move-board-up (:board state))

(defn move-down [state]
 (update-state (l/move-board-down (:board state))

(defn move-left [state]
 (update-state (l/move-board-left (:board state))

(defn move-right [state]
 (update-state (l/move-board-right (:board state))

(defn reset-game []
 (reset! state initial-state ))

(defn game-over-view [state]
 (if (:over state)
	[:div {:class "game-over"}
	 [:div "Game over"]
	 [:button {:class "play-again" :on-click reset-game} "Play again"]]
	[:div]))

(defn board []
 [:div {:class "container"}
	[:div {:class "board" :autofocus 1 :tabindex 1 :on-key-down
								#(case (.-which %)
									38 (swap! state move-up)
									40 (swap! state move-down)
									37 (swap! state move-left)
									39 (swap! state move-right)
									nil)}

	 (map create-row (:board @state))]
	(game-over-view @state)])

(reagent/render-component [board]
													(. js/document (getElementById "app")))
