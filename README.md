# BookNest – Online Bookstore (Spring Boot + MySQL + Thymeleaf)

## Run locally (2 minutes)
Needs Java 17+ and Maven (or open the folder in IntelliJ / Eclipse / VS Code and run `BookstoreApplication`).

    cd bookstore          # the folder that contains pom.xml
    mvn spring-boot:run

Wait for the line `Started BookstoreApplication`, then open **http://localhost:8080/**
Admin: admin@bookstore.com / admin123

With no configuration it uses an in-memory H2 database (data resets on restart).
To use MySQL, create a database and set environment variables:

    DB_URL=jdbc:mysql://localhost:3306/bookstore?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true
    DB_USER=root
    DB_PASSWORD=yourpassword

## Pages
/ , /books?q=java (search) · /register · /login · /cart · /checkout · /orders · /admin/books · /admin/orders

## Deploy on Railway (app + MySQL)
1. Push this folder to GitHub. Railway -> New Project -> Deploy from GitHub (uses the Dockerfile).
2. In the project: + New -> Database -> MySQL.
3. App service -> Variables:
   DB_URL = jdbc:mysql://${{MySQL.MYSQLHOST}}:${{MySQL.MYSQLPORT}}/${{MySQL.MYSQLDATABASE}}?useSSL=false&allowPublicKeyRetrieval=true
   DB_USER = ${{MySQL.MYSQLUSER}}
   DB_PASSWORD = ${{MySQL.MYSQLPASSWORD}}
   ADMIN_PASSWORD = a-strong-password
4. Settings -> Networking -> Generate Domain.

## Deploy on Render
New -> Web Service -> Docker. Add DB_URL / DB_USER / DB_PASSWORD from any MySQL host (Aiven, Railway).
Without DB_URL it still runs, but on H2 (data lost on restart).

NOTE: this is a Java server app. It will NOT work on static or PHP hosting
(GitHub Pages, Netlify, InfinityFree, cPanel shared hosting) - those show 404.

## Troubleshooting 404
- Use http://localhost:8080/ (not /index.html).
- Run `mvn` from the folder containing pom.xml.
- Port busy? run `mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=9090`
- Deployed 404 "application not found": open Deploy Logs, the app crashed on startup.

## Real payments
Implement `PaymentGateway` (Razorpay/Stripe) and delete `MockPaymentGateway`.
