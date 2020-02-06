(ns two048.logic)

(def board-size 4)

(defn get-random [] (rand-int 4))
(defn get-new-cell [] (rand-nth [2 4]))

(def transpose (partial apply map list))
(def remove-zeroes (partial filter pos?))

(defn append-zeroes [size coll]
  (take size (concat coll (repeat 0))))

(defn any-cell-not-empty? [board]
  (not (some zero? (flatten board))))

(defn create-new-cell [board]
  (if (any-cell-not-empty? board)
    [-1 -1]
    (loop [row (get-random)
           col (get-random)]
      (if (= 0 (get-in (mapv vec board) [row col]))
        [row col]
        (recur (get-random) (get-random))))))

(defn place-new-cell [board]
  (map (partial apply list)
       (assoc-in (mapv vec board)
                 (create-new-cell board) (get-new-cell))))

(defn any-adjacent-not-same? [coll]
  (= (count coll)
     (count (dedupe coll))))

(defn game-over? [board]
  (and (every? any-adjacent-not-same? board)
       (every? any-adjacent-not-same? (transpose board))
       (any-cell-not-empty? board)))

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

