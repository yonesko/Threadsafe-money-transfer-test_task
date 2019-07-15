#!/usr/bin/env bash
myAccountId=$(curl  -X POST  "http://localhost:9998/account")
wifeAccountId=$(curl  -X POST  "http://localhost:9998/account")

echo "Created accounts in Glebio Bank"

curl "http://localhost:9998/account/$myAccountId"; echo
curl "http://localhost:9998/account/$wifeAccountId"; echo

curl -X POST "http://localhost:9998/account/$myAccountId/replenish?cents=500000"

echo 'Add $5000 to my account'

curl "http://localhost:9998/account/$myAccountId"; echo

echo 'Transfer $1000 to my wife'

curl -X POST "http://localhost:9998/transfer?from=$myAccountId&to=$wifeAccountId&cents=100000"; echo

echo "My account: $(curl "http://localhost:9998/account/$myAccountId")"

