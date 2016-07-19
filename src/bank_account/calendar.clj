(ns bank-account.calendar
  (:require
    [clj-time.core :as t]))

(defn current-date []
  (t/now))
