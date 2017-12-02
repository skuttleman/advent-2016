(ns advent-2016.day-5
    (:require [advent-2016.utils.core :as u]
              [clojure.string :as s]))

(defn find-pw [input re length n]
    (->> (range)
        (map (comp u/md5 (partial str input)))
        (filter (partial re-matches re))
        (take length)))

;; d4cd2ee1
(defn step-1 []
    (->> (find-pw "ugkcyxxp" #"00000.*" 8 5)
        (map #(subs % n (inc n)))
        (apply str)))
