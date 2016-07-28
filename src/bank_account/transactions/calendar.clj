(ns bank-account.transactions.calendar
  (:require
    [clj-time.core :as t]))

(defn current-date []
  (t/now))
