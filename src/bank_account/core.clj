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

(defn withdraw! [account amount]
  (account/withdraw! account amount))

(defn deposit! [account amount]
  (account/deposit! account amount))

(defn print-statement [account]
  (account/print-statement account))

(defn start-account []
  (-> (make-system {})
      component/start
      :account))
