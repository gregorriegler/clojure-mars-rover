(ns cljtry.rover)

(defn to-the-right [direction]
  (cond
    (= direction "N") "E"
    (= direction "E") "S"
    (= direction "S") "W"
    (= direction "W") "N"))

(defn to-the-left [direction]
  (cond
    (= direction "N") "W"
    (= direction "E") "N"
    (= direction "S") "E"
    (= direction "W") "S"))

(defn vector-towards [direction]
  (cond
    (= direction "N") [0, 1]
    (= direction "E") [1, 0]
    (= direction "S") [0, -1]
    (= direction "W") [-1, 0]))

(defn direction-of [rover-state]
  (last rover-state))

(defn coordinates-of [rover-state]
  (subvec rover-state 0 2))

(defn rotate [rotate, rover-state]
  (let [new-direction (rotate (direction-of rover-state))]
    (conj (coordinates-of rover-state) new-direction)))

(defn move [rover-state]
  (let [new-coordinates (vec (map + rover-state (vector-towards (direction-of rover-state))))]
    (conj new-coordinates (direction-of rover-state))))

(defn has-commands [command-string]
  (> (count command-string) 0))

(defn next-command-of [command-string]
  (subs command-string 0 1))

(defn rest-of-commands [command-string]
  (subs command-string 1))

(defn a-land-rover-with [rover-state]
  (fn [command-string]
    "executes commands"
    (if (has-commands command-string)
      (let [next-state
            (let [command (next-command-of command-string)]
              (cond
                (= command "M") (move rover-state)
                (= command "R") (rotate to-the-right rover-state)
                (= command "L") (rotate to-the-left rover-state)))]
        ((a-land-rover-with next-state) (rest-of-commands command-string)))
      rover-state)))

(def rover (a-land-rover-with [0, 0, "N"]))