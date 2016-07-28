(ns bank-account.statement-printing.statement-printer)

(defprotocol StatementPrinter
  (print-statement [this balanced-transactions]))
