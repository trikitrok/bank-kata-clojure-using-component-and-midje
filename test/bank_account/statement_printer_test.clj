(ns bank-account.statement-printer-test
  (:require
    [midje.sweet :refer :all]
    [midje.open-protocols :refer [defrecord-openly]]
    [com.stuartsierra.component :as component]
    [bank-account.factories :as factories]
    [bank-account.statement-formatting.statement-format :refer [StatementFormat]]
    [bank-account.statement-printing.statement-printer :as printer]))

(unfinished format-statement-lines)
(unfinished header)

(defrecord-openly FakeFormat []
  StatementFormat
  (header [this])
  (format-statement-lines [this statement-lines]))

(defn new-console-printer [format]
  (component/start (merge (factories/console-printer)
                          {:format format
                           :print-fn identity})))

(fact
  "about printing statements"

  (fact
    "it asks the format for the header"

    (let [fake-format (->FakeFormat)
          a-printer (new-console-printer fake-format)]

      (printer/print-statement a-printer :not-used-in-this-test) => irrelevant

      (provided
        (header fake-format) => irrelevant :times 1)))

  (fact
    "it asks the format to format all balanced transactions"

    (let [fake-format (->FakeFormat)
          a-printer (new-console-printer fake-format)]

      (printer/print-statement a-printer ...some-balanced-transactions...) => irrelevant

      (provided
        (format-statement-lines
          fake-format
          ...some-balanced-transactions...) => ...some-formatted-statement-lines-to-print... :times 1))))
