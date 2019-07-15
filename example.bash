accountId=$(curl  -X POST  "http://localhost:9998/account")

echo "Created account $accountId in Glebio Bank"

curl "http://localhost:9998/account/$accountId"; echo

curl -X POST "http://localhost:9998/account/$accountId/replenish?cents=545688"

echo "Add money to account"

curl "http://localhost:9998/account/$accountId"; echo
