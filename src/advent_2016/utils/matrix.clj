(ns advent-2016.utils.matrix)

(defn rotate [matrix]
    (->> matrix
        (reduce (fn [result row]
                    (->> row
                        (map-indexed (fn [col result']
                                         (concat [result'] (get (vec result) col []))))))
            [])))

(defn rotate-row [matrix y n]
    (->> matrix
        (map-indexed (fn [i row]
                         (let [n' (- (count row) n)]
                             (if (not= i y)
                                 row
                                 (concat (drop n' row) (take n' row))))))))

(defn rotate-col [matrix x n]
    (let [col  (map #(nth % x) matrix)
          n'   (- (count col) n)
          col' (concat (drop n' col) (take n' col))]
        (->> matrix
            (map-indexed (fn [i row]
                             (concat (take x row) [(nth col' i)] (drop (inc x) row)))))))
