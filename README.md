# SaaS-subscription-API
billing subscription service API

# Tech Stack
- Java 17
- Spring Boot
- docker
- MongoDB

# Endpoints
- http://localhost:8080/graphql
- http://localhost:8080/Orchestrator

# MongoDB scripts

db.getCollection("SubscriptionPriceReference").insertMany([
    {
        "subscriptionType": "Basic",
        "price": 10
    },
    {
        "subscriptionType": "Pro",
        "price": 20
    },
    {
        "subscriptionType": "Ultimate",
        "price": 30
    }
]);

# Postman calls
http://localhost:8080/Orchestrator

{
    "username" : "username",
    "password" : "password",
    "email" : "username.password@test.net"
}

http://localhost:8080/graphql

query { getSubscriptionType(subscriptionType: "Ultimate") { subscriptionType price } }