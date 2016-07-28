(ns bank-account.statement-printer
  (:require
    [bank-account.statement-format :as formatting]))

(defprotocol StatementPrinter
  (print-statement [this balanced-transactions]))

