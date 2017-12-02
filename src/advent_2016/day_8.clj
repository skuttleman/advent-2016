(ns advent-2016.day-8
    (:require [advent-2016.utils.core :as u]))

(defn make-grid [width height]
    (repeat height (repeat width \.)))

(defn rect [grid [width height]]
    (->> grid
        (map-indexed (fn [i row] (if (>= i height)
                                     row
                                     (concat (repeat width \#) (drop width row)))))))

(defn rotate-row [grid [y n]]
    (->> grid
        (map-indexed (fn [i row]
                         (let [n' (- (count row) n)]
                             (if (not= i y)
                                 row
                                 (concat (drop n' row) (take n' row))))))))

(defn rotate-col [grid [x n]]
    (let [col  (map #(nth % x) grid)
          n'   (- (count col) n)
          col' (concat (drop n' col) (take n' col))]
        (->> grid
            (map-indexed (fn [i row]
                             (concat (take x row) [(nth col' i)] (drop (inc x) row)))))))

(defn ^:private parse-instruction [instruction]
    (->> [[rect #"rect (\d+)x(\d+)"]
          [rotate-row #"rotate row y=(\d+) by (\d+)"]
          [rotate-col #"rotate column x=(\d+) by (\d+)"]]
        (u/map-second (comp seq (partial map u/parse-int) rest #(re-matches % instruction)))
        (filter second)
        (first)))

(defn step-1 []
    (->> (u/read-file 8 #"\n")
        (map parse-instruction)
        (reduce (fn [grid [f args]] (f grid args)) (make-grid 50 6))
        (reduce (fn [total row] (+ total (count (filter (partial = \#) row)))) 0)))