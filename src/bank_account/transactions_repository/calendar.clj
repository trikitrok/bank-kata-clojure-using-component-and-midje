(ns bank-account.transactions-repository.calendar
  (:require
    [clj-time.core :as t]))

(defn current-date []
  (t/now))
