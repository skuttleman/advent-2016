(ns advent-2016.day-1
    (:require [advent-2016.utils.core :as u]))

(def ^:private visited (atom #{}))

(defn ^:private parse-input []
    (->> (u/read-file 1 #",")
        (map (fn [[turn & distance]]
                 [(if (= turn \L) first second)
                  (Integer/parseInt (apply str distance))]))))

(def ^:private turn
    {:up [:left :right]
     :down [:right :left]
     :left [:down :up]
     :right [:up :down]})

(defn ^:private add [pt1 pt2]
    {:x (+ (:x pt1) (:x pt2))
     :y (+ (:y pt1) (:y pt2))})

(defn grid-distance [pt1 pt2]
    (+ (Math/abs (- (:x pt1) (:x pt2)))
        (Math/abs (- (:y pt1) (:y pt2)))))

(defn ^:private go-from [position direction distance]
    (let [distance' (if (#{:left :up} direction) (* -1 distance) distance)
          key (if (#{:left :right} direction) :x :y)]
        (-> {:x 0 :y 0}
            (assoc key distance')
            (add position))))

(defn ^:private apply-turn [{:keys [direction position]} [turn-f distance]]
    (let [direction' (turn-f (get turn direction))]
        {:direction direction' :position (go-from position direction' distance)}))

(defn ^:private about-to-visit [position direction distance]
    (loop [distance' distance positions [position]]
        (if (zero? distance')
            (butlast positions)
            (let [position' (go-from (last positions) direction 1)]
                (recur (dec distance') (conj positions position'))))))

(defn ^:private apply-memoized-turn [{:keys [direction position solution]} [turn-f distance]]
    (let [direction' (turn-f (get turn direction))]
        (if solution
            {:solution solution}
            (let [positions (about-to-visit position direction' distance)]
                (if-let [solution' (->> positions (filter @visited) (first))]
                    {:solution solution'}
                    (do (swap! visited into positions)
                        {:direction direction' :position (go-from position direction' distance)}))))))

;; 253
(defn step-1 []
    (->> (parse-input)
        (reduce apply-turn {:direction :up :position {:x 0 :y 0}})
        (:position)
        (grid-distance {:x 0 :y 0})))

;126
(defn step-2 []
    (->> (parse-input)
        (reduce apply-memoized-turn {:direction :up :position {:x 0 :y 0}})
        (:solution)
        (grid-distance {:x 0 :y 0})))
