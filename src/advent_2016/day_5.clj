(ns advent-2016.day-5
    (:require [advent-2016.utils.core :as u]
              [clojure.string :as s]))

(def ^:private empty-pw [\- \- \- \- \- \- \- \-])

(defn find-pw [input re]
    (->> (range)
        (map (comp u/md5 (partial str input)))
        (filter (partial re-matches re))))

(defn ^:private update-empty [pw i c]
    (update pw (Integer/parseInt (str i)) #(if (= \- %) c %)))

(defn ^:private complete [coll]
    (->> coll
        (reduce (fn [pw [i c]] (update-empty pw i c)) empty-pw)))

(defn ^:private incomplete? []
    (let [pw (atom empty-pw)]
        (fn [[i c]]
            (let [result (contains? (set @pw) \-)]
                (swap! pw update-empty i c)
                result))))

;; d4cd2ee1
(defn step-1 []
    (->> (find-pw "ugkcyxxp" #"00000.*")
        (take 8)
        (map #(subs % 5 6))
        (apply str)))

;; f2c730e5
(defn step-2 []
    (->> (find-pw "ugkcyxxp" #"00000[0-7].*")
        (map #(subs % 5 7))
        (take-while (incomplete?))
        (complete)
        (apply str)))
