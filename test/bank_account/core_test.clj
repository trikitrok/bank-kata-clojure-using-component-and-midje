(ns bank-account.core-test
  (:require
    [midje.sweet :refer :all]
    [bank-account.core :refer :all]
    [bank-account.test-helpers :refer [output-lines make-dates]]
    [com.stuartsierra.component :as component]
    [bank-account.transactions :as transactions]))

(unfinished date-fn)

(facts
  "printing an account statement"

  (let [config {:printer {:header "date || credit || debit || balance"}}
        make-dates (partial make-dates "dd/MM/yyyy")
        system (assoc (make-system config)
                      :transactions
                      (transactions/use-in-memory #(date-fn)))
        account (-> system component/start :account)]

    (do
      (deposit! account 2000)
      (deposit! account 500)
      (withdraw! account 1000)
      (output-lines
        print-statement account)) => ["date || credit || debit || balance"
                                      "21/07/2016 || 2000.00 || || 2000.00"
                                      "22/07/2016 || 500.00 || || 2500.00"
                                      "23/07/2016 || || -1000.00 || 1500.00"]

    (provided (date-fn) =streams=> (make-dates ["21/07/2016" "22/07/2016" "23/07/2016"]))))
