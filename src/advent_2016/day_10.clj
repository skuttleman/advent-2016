(ns advent-2016.day-10
    (:require [advent-2016.utils.core :as u]))

(def ^:private bots (atom {}))

(def ^:private bins (atom {}))

(defn ^:private get-receiver [type number]
    (-> (if (= type "bot") bots bins)
        (deref)
        (get number)
        (:receive identity)))

(defn ^:private make-bot [name low-to-type low-to-num high-to-type high-to-num]
    (let [processed    (atom [])
          stored-value (atom nil)]
        {:receive   (fn [value]
                        (if @stored-value
                            (let [[min' max'] (map #(% value @stored-value) [min max])]
                                ((get-receiver low-to-type low-to-num) min')
                                ((get-receiver high-to-type high-to-num) max')
                                (reset! stored-value nil)
                                (swap! processed conj [min' max']))
                            (reset! stored-value value)))
         :name      name
         :processed (fn [] @processed)}))

(defn ^:private make-bin [name]
    (let [values (atom [])]
        {:receive   (fn [value]
                        (swap! values conj value))
         :name      name
         :processed (fn [] @values)}))

(defn ^:private build [line]
    (let [[_ bot-number low-to-type low-to-number high-to-type high-to-number]
          (re-find #"bot (\d+) gives low to ([a-z]+) (\d+) and high to ([a-z]+) (\d+)" line)]
        (cond
            (= "output" low-to-type) (swap! bins assoc low-to-number (make-bin low-to-number))
            (= "output" high-to-type) (swap! bins assoc high-to-number (make-bin high-to-number)))
        (swap! bots assoc bot-number (make-bot bot-number low-to-type low-to-number high-to-type high-to-number))))

(defn ^:private run [line]
    (let [[_ value type number]
          (re-find #"value (\d+) goes to ([a-z]+) (\d+)" line)]
        ((get-receiver type number) (u/parse-int value))))

(defn ^:private find-processor [bots low high]
    (->> bots
        (vals)
        (filter (comp #(contains? % [low high]) set #(%) :processed))
        (first)
        (:name)))

(defn ^:private build-and-run [instructions]
    (doall (map build (get instructions \b)))
    (doall (map run (get instructions \v))))

;; 116
(defn step-1 []
    (->> (u/read-file 10 #"\n")
        (group-by first)
        (build-and-run))
    (find-processor @bots 17 61))
