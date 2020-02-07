(ns two048.logic)

(def board-size 4)

(defn get-new-cell [] (rand-nth [2 4]))

(def transpose (partial apply map list))
(def remove-zeroes (partial filter pos?))

(defn append-zeroes [size coll]
  (take size (concat coll (repeat 0))))

(defn map-2d-index [pos]
  [(quot pos board-size) (mod pos board-size)])

(defn get-empty-positions [board]
  (map map-2d-index (keep-indexed (fn [k v] (when (zero? v) k)) (flatten board))))

(defn create-new-cell [board]
  (let [empty-cells (get-empty-positions board)]
    (if (zero? (count empty-cells))
      [-1 -1]
      (rand-nth empty-cells))))

(defn place-new-cell [board]
  (map (partial apply list)
       (assoc-in (mapv vec board)
                 (create-new-cell board) (get-new-cell))))

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

(def move-board-right (comp reverse-board move-board-left reverse-board))

(def move-board-up (comp transpose move-board-left transpose))

(def move-board-down (comp transpose move-board-right transpose))

(defn not-empty-cell? [board]
  (not (some zero? (flatten board))))

(defn no-same-adjacent [coll]
  (= coll (dedupe coll)))

(defn game-over? [board]
  (and (every? no-same-adjacent board)
       (every? no-same-adjacent (transpose board))
       (not-empty-cell? board)))
