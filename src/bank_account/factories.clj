(ns bank-account.factories
  (:require
    [com.stuartsierra.component :as component]
    [bank-account.statement-printer :as printer]
    [bank-account.transactions :as transactions]
    [bank-account.calendar :as calendar]
    [bank-account.account :as account]
    [bank-account.statement-line-formatting :as statement-line-formatting]))

(defn account-component-map []
  (component/using
    (account/map->Account {})
    {:transactions :transactions
     :printer :printer}))

(defn in-memory-transactions [current-date-fn]
  (transactions/in-memory current-date-fn))

(defn make-system [conf]
  (component/system-map
    :transactions (in-memory-transactions calendar/current-date)
    :formatter (statement-line-formatting/nice-formatter (:formatter conf))
    :printer (printer/console-printer (:printer conf) :formatter)
    :account (account-component-map)))
