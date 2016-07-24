(ns bank-account.statement-line-formatting
  (:require
    [clj-time.format :as f]))

(def ^:private date-formatter
  (f/formatter "dd/MM/yyyy"))

(defn- format-date [date]
  (f/unparse date-formatter date))

(defn- pad-num [num]
  (str num ".00"))

(defn- format-balance [balance]
  (pad-num balance))

(defn- format-amount [amount]
  (if (< amount 0)
    (str " || || " (pad-num amount) " || ")
    (str " || " (pad-num amount) " || || ")))

(defn format-statement-line [{:keys [amount balance date]}]
  (str (format-date date)
       (format-amount amount)
       (format-balance balance)))