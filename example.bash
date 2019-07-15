#!/usr/bin/env bash
myAccountId=$(curl -s -X POST  "http://localhost:9998/account")
wifeAccountId=$(curl -s -X POST  "http://localhost:9998/account")

echo "Created accounts in Glebio Bank"

echo "My account: $(curl -s "http://localhost:9998/account/$myAccountId")"
echo "Wife's account: $(curl -s "http://localhost:9998/account/$wifeAccountId")"

curl -s -X POST "http://localhost:9998/account/$myAccountId/replenish?cents=500000"

echo 'Add $5000 to my account'

echo "My account: $(curl -s "http://localhost:9998/account/$myAccountId")"
echo "Wife's account: $(curl -s "http://localhost:9998/account/$wifeAccountId")"

echo 'Transfer $1000 to my wife'

curl -s -X POST "http://localhost:9998/transfer?from=$myAccountId&to=$wifeAccountId&cents=100000"; echo

echo "My account: $(curl -s "http://localhost:9998/account/$myAccountId")"
echo "Wife's account: $(curl -s "http://localhost:9998/account/$wifeAccountId")"

