(ns bank-account.transactions-repository-types.in-memory-transactions
  (:require
    [com.stuartsierra.component :as component]
    [bank-account.transactions :as transactions]))

(defrecord InMemoryTransactions [current-date-fn transactions]
  component/Lifecycle
  (start [this]
    (println ";; Starting InMemoryTransactions")
    (assoc this :transactions (atom [])))

  (stop [this]
    (println ";; Stopping InMemoryTransactions")
    (assoc this :transactions nil))

  transactions/TransactionsOperations
  (register! [this amount]
    (assoc
      this
      :transactions
      (swap! (:transactions this)
             conj
             {:amount amount :date (current-date-fn)})))

  (balanced-transactions [this]
    (transactions/add-balances @(:transactions this))))

(defn make [current-date-fn]
  (println ";; creating InMemoryTransactions")
  (map->InMemoryTransactions
    {:current-date-fn current-date-fn}))
