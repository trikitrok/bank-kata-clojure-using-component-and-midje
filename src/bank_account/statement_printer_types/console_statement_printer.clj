(ns bank-account.statement-printer-types.console-statement-printer
  (:require
    [bank-account.statement-format :as formatting]
    [bank-account.statement-printer :as printer]))

(defn- print-lines [lines]
  (doall (map println lines)))

(defrecord ConsoleStatementPrinter [print-fn format]
  printer/StatementPrinter
  (print-statement [_ balanced-transactions]
    ((or print-fn println) (formatting/header format))
    (print-lines (formatting/format-statement-lines format balanced-transactions))))

(defn make []
  (map->ConsoleStatementPrinter {}))
