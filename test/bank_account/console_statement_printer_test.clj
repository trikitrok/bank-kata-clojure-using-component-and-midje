(ns bank-account.console-statement-printer-test
  (:require
    [midje.sweet :refer :all]
    [midje.open-protocols :refer [defrecord-openly]]
    [com.stuartsierra.component :as component]
    [bank-account.factories :as factories]
    [bank-account.statement-formatting.statement-format :refer [StatementFormat]]
    [bank-account.statement-printing.statement-printer :as printer]
    [bank-account.test-helpers :refer [output-lines]]))

(unfinished format-statement-lines)
(unfinished header)

(defrecord-openly FakeFormat []
  StatementFormat
  (header [this])
  (format-statement-lines [this statement-lines]))

(defn new-console-printer
  ([format print-fn]
   (component/start (merge (factories/console-printer)
                           {:format format
                            :print-fn print-fn})))
  ([format]
   (new-console-printer format identity)))

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
          ...some-balanced-transactions...) => ...some-formatted-statement-lines-to-print... :times 1)))

  (fact
    "it prints the header and formatted lines"

    (let [some-header "some-header"
          some-formatted-statement-lines-to-print ["statement-line-1" "statement-line-2"]
          fake-format (->FakeFormat)
          a-printer (new-console-printer fake-format println)]

      (output-lines
        printer/print-statement
        a-printer ...some-balanced-transactions...) => ["some-header"
                                                        "statement-line-1"
                                                        "statement-line-2"]

      (provided
        (header fake-format) => some-header

        (format-statement-lines
          fake-format
          ...some-balanced-transactions...) => some-formatted-statement-lines-to-print))))
