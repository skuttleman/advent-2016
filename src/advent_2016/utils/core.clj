(ns advent-2016.utils.core
    (:require [clojure.string :as s]))

(def letter-seq (iterate (fn [l] (if (= l \z) \a (char (inc (int l))))) \a))

(defn split-n-trim [re input]
    (->> (s/split input re)
        (map s/trim)
        (remove empty?)))

(defn read-file [day re]
    (->> (slurp (str "resources/day" day ".txt"))
        (split-n-trim re)
        (map s/trim)
        (remove empty?)))
