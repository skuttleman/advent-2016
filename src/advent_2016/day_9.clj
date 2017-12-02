(ns advent-2016.day-9
    (:require [advent-2016.utils.core :as u]))

(defn ^:private do-cmd [processed cmd chars]
    (let [[len times] (->> cmd
                          (re-find #"(\d+)x(\d+)")
                          (rest)
                          (map u/parse-int))]
        [(apply str processed (flatten (repeat times (take len chars)))) (drop len chars)]))

(defn ^:private expand [processed char chars]
    (let [cmd (loop [cmd (str char) [char' & more'] chars]
                  (if (and (seq more') (not=  char' ")"))
                      (recur (str cmd char') more')
                      (str cmd char')))]
        (do-cmd processed cmd (drop (dec (count cmd)) chars))))

(defn ^:private read-and-process [chars]
    (loop [[processed [char & more]] ["" chars]]
        (if-not (seq more)
            (str processed char)
            (if (= char "(")
                (recur (expand processed char more))
                (recur [(str processed char) more])))))

;; 115118
(defn step-1 []
    (->> (u/read-file 9 #"")
        (read-and-process)
        (count)))
