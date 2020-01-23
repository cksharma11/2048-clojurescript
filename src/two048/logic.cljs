(ns two048.logic)

(def remove-zeroes (partial filter pos?))

(defn append-zeroes [size coll]
  (take size (concat coll (repeat 0))))

(def move-left-row-without-zeroes
  (comp
    (partial map (partial apply +))
    (partial mapcat (partial partition-all 2))
    (partial partition-by identity)
    (partial remove-zeroes)))

(def move-left-row
  (comp
    (partial apply append-zeroes)
    (juxt count move-left-row-without-zeroes)))

(def move-board-left (partial map move-left-row))

(def reverse-board (partial map reverse))

(def transpose (partial apply map list))

(def move-board-right (comp reverse-board move-board-left reverse-board))

(def move-board-up (comp transpose move-board-left transpose))

(def move-board-down (comp transpose move-board-right transpose))

