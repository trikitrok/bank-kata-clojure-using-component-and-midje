(ns bank-account.transactions-operations.calendar
  (:require
    [clj-time.core :as t]))

(defn current-date []
  (t/now))
