(ns advent-2016.day-1
    (:require [clojure.string :as s]))

(defn ^:private parse-input []
    (->> (s/split (slurp "resources/day1.txt") #",")
        (map s/trim)
        (remove empty?)
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
    (println pt1 pt2)
    (+ (Math/abs (- (:x pt1) (:x pt2)))
        (Math/abs (- (:y pt1) (:y pt2)))))

(defn ^:private go-from [position direction distance]
    (let [distance' (if (#{:left :up} direction) (* -1 distance) distance)
          key (if (#{:left :right} direction) :x :y)]
        (-> {:x 0 :y 0}
            (assoc key distance')
            (add position))))

(defn ^:private apply-turn [{:keys [direction position]} [turn-f distance]]
    (let [direction' (turn-f (get turn direction))
          position' (go-from position direction' distance)]
        {:direction direction' :position position'}))

(defn step-1 []
    (->> (parse-input)
        (reduce apply-turn {:direction :up :position {:x 0 :y 0}})
        (:position)
        (grid-distance {:x 0 :y 0})))

(defn step-2 [] (println "step 2"))
