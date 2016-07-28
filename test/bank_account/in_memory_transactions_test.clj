(ns bank-account.in-memory-transactions-test
  (:require
    [midje.sweet :refer :all]
    [midje.open-protocols :refer [defrecord-openly]]
    [com.stuartsierra.component :as component]
    [bank-account.factories :as factories]
    [bank-account.transactions-repository.transactions :as transactions]
    [bank-account.test-helpers :refer [make-date]]))

(unfinished date-fn)

(defn new-in-memory-transactions [date-fn]
  (component/start (factories/in-memory-transactions date-fn)))

(fact
  "about transactions"

  (facts
    "in memory"

    (fact
      "returns balanced transactions lines for all registered transactions"

      (let [date (partial make-date "dd/MM/yyyy")
            first-transaction {:amount 1000 :date (date "10/02/2016")}
            second-transaction {:amount 1500 :date (date "13/05/2016")}
            third-transaction {:amount -500 :date (date "14/08/2016")}
            in-memory-transactions (new-in-memory-transactions #(date-fn))]

        (do
          (transactions/register! in-memory-transactions (:amount first-transaction))
          (transactions/register! in-memory-transactions (:amount second-transaction))
          (transactions/register! in-memory-transactions (:amount third-transaction))
          (transactions/balanced-transactions in-memory-transactions))

        => [(assoc first-transaction :balance 1000)
            (assoc second-transaction :balance 2500)
            (assoc third-transaction :balance 2000)]

        (provided (date-fn) =streams=> [(:date first-transaction)
                                        (:date second-transaction)
                                        (:date third-transaction)])))))
