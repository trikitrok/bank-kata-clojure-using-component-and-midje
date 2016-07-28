(ns bank-account.statement-format)

(defprotocol StatementFormat
  (header [this])
  (format-statement-lines [this statement-lines]))
