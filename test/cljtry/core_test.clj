(ns cljtry.core-test
  (:require [midje.sweet :refer :all]
            [cljtry.core :refer :all]))

(defn rotate-right [position, other-commands]
  ((land-rover (conj (subvec position 0 2) "E")) other-commands))

(defn move [position, other-commands]
    ((land-rover (conj (vec (map + position [0, 1])) (last position))) other-commands))

(defn exec [position, command-string]
  (def command (subs command-string 0 1))
  (def other-commands (subs command-string 1))
  (cond
    (= command "M") (move position other-commands)
    (= command "R") (rotate-right position other-commands)))

(defn land-rover [position]
  (fn [command-string]
    "executes commands"
    (if
      (> (count command-string) 0)
      (exec position command-string)
      position)))

(def rover (land-rover [0, 0, "N"]))

(fact "rover moves"
      (rover "M")   => [0, 1, "N"]
      (rover "MM")  => [0, 2, "N"]
      (rover "MMM") => [0, 3, "N"])

(fact "rover rotates"
      (rover "R")    => [0, 0, "E"]
    ;  (rover "RR")   => [0, 0, "S"]
    ;  (rover "RRR")  => [0, 0, "W"]
    ;  (rover "RRRR") => [0, 0, "N"]
    )
