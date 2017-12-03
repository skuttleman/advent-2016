(ns advent-2016.day-12
    (:require [advent-2016.utils.core :as u]))

(defn ?->type [input]
    (try (u/parse-int input)
         (catch Exception e
             (keyword input))))

(defn ^:private assign [registers line [value register]]
    (swap! registers assoc register value)
    (inc line))

(defn ^:private copy [registers line [from to]]
    (swap! registers (fn [reg] (assoc reg to (get reg from))))
    (inc line))

(defn ^:private jump->line [value line amount]
    (if-not (zero? value)
        (+ line amount)
        (inc line)))

(defn ^:private jump-when [registers line [check amount]]
    (jump->line (get @registers check) line amount))

(defn ^:private jump [_ line [value amount]]
    (jump->line value line amount))

(defn ^:private incr [registers line [register]]
    (swap! registers update register inc)
    (inc line))

(defn ^:private decr [registers line [register]]
    (swap! registers update register dec)
    (inc line))

(defn ^:private parse-instruction [instruction]
    (->> {assign    #"cpy (-?\d+) ([a-d])"
          copy      #"cpy ([a-d]) ([a-d])"
          jump-when #"jnz ([a-d]) (-?\d+)"
          jump      #"jnz ([1-9]\d*) (-?\d+)"
          incr      #"inc ([a-d])"
          decr      #"dec ([a-d])"}
        (u/map-second (comp (partial map ?->type) rest #(re-matches % instruction)))
        (remove (comp empty? second))
        (first)))

(defn ^:private run
    ([registers instructions] (run registers instructions 0))
    ([registers instructions line]
     (if (or (neg? line) (>= line (count instructions)))
         @registers
         (let [[cmd args] (get instructions line)]
             (recur registers instructions (cmd registers line args))))))

(defn ^:private prep []
    (->> (u/read-file 12 #"\n")
        (mapv parse-instruction)))

;; 318117
(defn step-1 []
    (->> (prep)
        (run (atom {:a 0 :b 0 :c 0 :d 0}))))

;; 9227771
(defn step-2 []
    (->> (prep)
        (run (atom {:a 0 :b 0 :c 1 :d 0}))))
