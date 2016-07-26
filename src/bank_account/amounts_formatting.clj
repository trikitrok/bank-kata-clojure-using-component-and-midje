(ns bank-account.amounts-formatting
  (:require
    [clojure.string :as string]))

(defn- num-zeros-to-pad [dec-part num-decimals]
  (- num-decimals (count dec-part)))

(defn format-amount [amount num-decimals]
  (let [[int-part dec-part] (string/split (str amount) #"\.")]
    (apply str int-part "." dec-part (repeat (num-zeros-to-pad dec-part num-decimals) "0"))))
