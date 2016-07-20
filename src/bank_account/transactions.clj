(ns bank-account.transactions
  (:require
    [com.stuartsierra.component :as component]))

(defn- transactions->statement-lines [transactions]
  (second
    (reduce
      (fn [[accumulated-balance statement-lines] transaction]
        (let [balance (+ accumulated-balance (:amount transaction))]
          [balance (conj statement-lines (assoc transaction :balance balance))]))
      [0 []]
      transactions)))

(defprotocol Transactions
  (register! [this amount])
  (statement-lines [this]))

(defrecord InMemoryTransactions [current-date-fn]
  component/Lifecycle
  (start [this]
    (println ";; Starting InMemoryTransactions")
    (assoc this :transactions (atom [])))

  (stop [this]
    (println ";; Stopping InMemoryTransactions")
    this)

  Transactions
  (register! [this amount]
    (assoc
      this
      :transactions
      (swap! (:transactions this)
             conj
             {:amount amount :date (current-date-fn)})))

  (statement-lines [this]
    (transactions->statement-lines
      @(:transactions this))))

(defn use-in-memory [current-date-fn]
  (println ";; creating InMemoryTransactions")
  (map->InMemoryTransactions
    {:current-date-fn current-date-fn}))