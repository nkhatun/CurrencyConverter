# CurrencyConverter

The goal of this test assignment is to demonstrate an "Exchange Rate Service". The service
exposes an API, which can be consumed by a frontend application.

##User Stories
As a user, who accesses this service through a user interface, ...
1. I want to retrieve the ECB reference rate for a currency pair, e.g. USD/EUR or
HUF/EUR.
2. I want to retrieve an exchange rate for other pairs, e.g. HUF/USD.
3. I want to retrieve a list of supported currencies and see how many times they were
requested.
4. I want to convert an amount in a given currency to another, e.g. 15 EUR = ??? GBP
5. I want to retrieve a link to a public website showing an interactive chart for a given
currency pair.

##Implementation
The European Central Bank publishes reference exchange rates on a daily basis.
You can find them here:
https://www.ecb.europa.eu/stats/policy_and_exchange_rates/euro_reference_excha
nge_rates/html/index.en.html

