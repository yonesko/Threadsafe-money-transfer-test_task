### Bank Example
You can create and replenish account and transfer money between them.

Start server:
```bash
./gradlew run
```

API Usage Example:
```bash
#Create account
curl -X POST http://localhost:9998/account
{"id":"86c8d1d0-a205-4c84-ba2b-620d63ff9606","cents":0}
#Put $5000 on it
curl -X POST http://localhost:9998/account/86c8d1d0-a205-4c84-ba2b-620d63ff9606/replenish?cents=500000
#Check account
curl http://localhost:9998/account/86c8d1d0-a205-4c84-ba2b-620d63ff9606
{"id":"86c8d1d0-a205-4c84-ba2b-620d63ff9606","cents":500000}
#Create account for wife
curl -X POST http://localhost:9998/account
{"id":"e7420bac-cd4e-4e92-b5c6-afac25e4db5b","cents":0}
#Send to her $1200
curl -X POST "http://localhost:9998/transfer?from=86c8d1d0-a205-4c84-ba2b-620d63ff9606&to=e7420bac-cd4e-4e92-b5c6-afac25e4db5b&cents=120000"
#Check accounts
curl http://localhost:9998/account/86c8d1d0-a205-4c84-ba2b-620d63ff9606
curl http://localhost:9998/account/e7420bac-cd4e-4e92-b5c6-afac25e4db5b
#Check transfers
curl "http://localhost:9998/transfer?from=86c8d1d0-a205-4c84-ba2b-620d63ff9606" | jq
```
