(ns bank-account.statement-printer
  (:require
    [bank-account.statement-format :as formatting]))

(defprotocol StatementPrinter
  (print-statement [this balanced-transactions]))

(defn- print-lines [lines]
  (doall (map println lines)))

(defrecord ConsoleStatementPrinter [print-fn format]
  StatementPrinter
  (print-statement [_ balanced-transactions]
    ((or print-fn println) (formatting/header format))
    (print-lines (formatting/format-statement-lines format balanced-transactions))))
