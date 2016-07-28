(ns bank-account.statement-formatting.statement-format)

(defprotocol StatementFormat
  (header [this])
  (format-statement-lines [this statement-lines]))
