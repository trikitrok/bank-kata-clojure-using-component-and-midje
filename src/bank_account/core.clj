(ns bank-account.core
  (:require
    [com.stuartsierra.component :as component]
    [bank-account.statement-printer :as printer]
    [bank-account.transactions :as transactions]
    [bank-account.calendar :as calendar]
    [bank-account.account :as account]))

(defn make-system [conf]
  (component/system-map
    :transactions (transactions/use-in-memory
                    calendar/current-date)
    :printer (printer/use-console-printer)
    :account (component/using
               (account/make)
               {:transactions :transactions
                :printer :printer})))

(defn main []
  (component/start
    (make-system {})))
