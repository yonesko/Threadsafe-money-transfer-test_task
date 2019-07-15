#!/usr/bin/env bash

function accountsStatus() {
    echo "My account: $(curl -s "http://localhost:9998/account/$myAccountId")"
    echo "Wife's account: $(curl -s "http://localhost:9998/account/$wifeAccountId")"
    echo "Mom's account: $(curl -s "http://localhost:9998/account/$momAccountId")"
}

echo "Creating accounts in Glebio Bank"
myAccountId=$(curl -s -X POST  "http://localhost:9998/account")
wifeAccountId=$(curl -s -X POST  "http://localhost:9998/account")
momAccountId=$(curl -s -X POST  "http://localhost:9998/account")

accountsStatus
echo 'Add $5000 to my account'
curl -I -X POST "http://localhost:9998/account/$myAccountId/replenish?cents=500000"

accountsStatus
echo 'Transfer $1000 to my wife'
curl -I -X POST "http://localhost:9998/transfer?from=$myAccountId&to=$wifeAccountId&cents=100000"; echo

echo 'Transfer $1000 to my mom'
curl -I -X POST "http://localhost:9998/transfer?from=$myAccountId&to=$momAccountId&cents=100000"; echo

accountsStatus
echo 'All my transfers:'
curl -s "http://localhost:9998/transfer?from=$myAccountId"|jq

