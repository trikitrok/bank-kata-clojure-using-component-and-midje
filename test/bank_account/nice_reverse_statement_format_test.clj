(ns bank-account.nice-reverse-statement-format-test
  (:require
    [midje.sweet :refer :all]
    [midje.open-protocols :refer [defrecord-openly]]
    [bank-account.statement-formats-types.nice-reverse-statement-format :as nice-reverse-statement-format]
    [bank-account.statement-format :as statement-format]
    [com.stuartsierra.component :as component]
    [bank-account.test-helpers :refer [make-date]]))

(defn- new-nice-reverse-format [config]
  (component/start (nice-reverse-statement-format/make config)))

(fact
  "about formatting statements"

  (facts
    "using NiceReverseStatementFormat"

    (let [config {:date-format "dd/MM/yyyy"
                  :separator "||"
                  :num-decimals 2
                  :header "date || credit || debit || balance"}
          date (partial make-date (:date-format config))
          nice-reverse-format (new-nice-reverse-format config)]

      (fact
        "it returns the configured header"

        (statement-format/header nice-reverse-format) => (:header config))

      (fact
        "it formats the statement lines"

        (let [balanced-transactions [{:balance 1000 :amount 1000 :date (date "10/02/2016")}
                                     {:balance 2500 :amount 1500 :date (date "13/05/2016")}
                                     {:balance 2000 :amount -500 :date (date "14/08/2016")}]]

          (statement-format/format-statement-lines
            nice-reverse-format
            balanced-transactions) => ["14/08/2016 || || 500.00 || 2000.00"
                                       "13/05/2016 || 1500.00 || || 2500.00"
                                       "10/02/2016 || 1000.00 || || 1000.00"])))))
