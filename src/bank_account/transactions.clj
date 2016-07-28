(ns bank-account.transactions)

(defn add-balances [transactions]
  (second
    (reduce
      (fn [[accumulated-balance balanced-transaction] transaction]
        (let [balance (+ accumulated-balance (:amount transaction))]
          [balance (conj balanced-transaction (assoc transaction :balance balance))]))
      [0 []]
      transactions)))

(defprotocol TransactionsOperations
  (register! [this amount])
  (balanced-transactions [this]))
