(ns cljtry.core-test
  (:require [midje.sweet :refer :all]
            [cljtry.core :refer :all]))

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

(defn direction-of [position]
  (last position))

(defn rotate [where, position, other-commands]
  (let [direction (where (direction-of position))]
    ((land-rover (conj (subvec position 0 2) direction)) other-commands)))

(defn move [position, other-commands]
  (let [next-position (cond
                        (= (direction-of position) "N") (vec (map + position [0, 1]))
                        (= (direction-of position) "E") (vec (map + position [1, 0]))
                        (= (direction-of position) "S") (vec (map + position [0, -1]))
                        (= (direction-of position) "W") (vec (map + position [-1, 0]))
                        )]
    ((land-rover (conj next-position (direction-of position))) other-commands)))

(defn exec [position, command-string]
  (def command (subs command-string 0 1))
  (def other-commands (subs command-string 1))
  (cond
    (= command "M") (move position other-commands)
    (= command "R") (rotate to-the-right position other-commands)
    (= command "L") (rotate to-the-left position other-commands)))

(defn land-rover [position]
  (fn [command-string]
    "executes commands"
    (if
      (> (count command-string) 0)
      (exec position command-string)
      position)))

(def rover (land-rover [0, 0, "N"]))


(fact "rover rotates to the right"
      (rover "R")    => [0, 0, "E"]
      (rover "RR")   => [0, 0, "S"]
      (rover "RRR")  => [0, 0, "W"]
      (rover "RRRR") => [0, 0, "N"])

(fact "rover rotates to the left"
      (rover "L")    => [0, 0, "W"]
      (rover "LL")   => [0, 0, "S"]
      (rover "LLL")  => [0, 0, "E"]
      (rover "LLLL") => [0, 0, "N"])

(fact "rover moves north"
      (rover "M")   => [0, 1, "N"]
      (rover "MM")  => [0, 2, "N"]
      (rover "MMM") => [0, 3, "N"])

(fact "rover moves south"
      (rover "RRM")   => [0, -1, "S"]
      (rover "RRMM")  => [0, -2, "S"]
      (rover "RRMMM") => [0, -3, "S"])

(fact "rover moves east"
      (rover "RM")   => [1, 0, "E"]
      (rover "RMM")  => [2, 0, "E"]
      (rover "RMMM") => [3, 0, "E"])

(fact "rover moves west"
      (rover "LM")   => [-1, 0, "W"]
      (rover "LMM")  => [-2, 0, "W"]
      (rover "LMMM") => [-3, 0, "W"])

