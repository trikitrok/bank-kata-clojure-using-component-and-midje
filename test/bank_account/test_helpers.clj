(ns bank-account.test-helpers
  (:require
    [clojure.string :as string]
    [clj-time.format :as f]))

(defn output-lines [fn & args]
  (string/split (with-out-str (apply fn args)) #"\n"))

(defn make-date [format date-str]
  (let [custom-formatter (f/formatter format)]
    (f/parse custom-formatter date-str)))

(defn make-dates [format date-strs]
  (map (partial make-date format) date-strs))
