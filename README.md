# BookNest – Online Bookstore (Spring Boot + MySQL + Thymeleaf)

## Features
- Registration / login (Spring Security, BCrypt), roles USER and ADMIN
- Search books by title, author, category
- Session-based cart, checkout, order history
- Payments recorded per order (pluggable `PaymentGateway`, mock included)
- Admin: add books, update stock, view all orders

## Run locally
**Option A: no database needed (H2 in memory)**
    mvn spring-boot:run -Dspring-boot.run.profiles=h2

**Option B: MySQL**
    mysql -u root -p -e "CREATE DATABASE bookstore"
    # edit DB_USER / DB_PASSWORD env vars or application.properties
    mvn spring-boot:run

Open http://localhost:8080
Admin login: admin@bookstore.com / admin123  (change via ADMIN_EMAIL / ADMIN_PASSWORD env vars)

## Environment variables
| Variable | Meaning |
|---|---|
| DB_URL | jdbc:mysql://HOST:PORT/DBNAME |
| DB_USER / DB_PASSWORD | database credentials |
| ADMIN_EMAIL / ADMIN_PASSWORD | seeded admin account |
| PORT | set automatically by most hosts |

## Deploy on Railway (easiest, app + MySQL in one place)
1. Push this folder to a GitHub repo.
2. railway.app -> New Project -> Deploy from GitHub repo (it detects the Dockerfile).
3. In the same project: + New -> Database -> MySQL.
4. Open your app service -> Variables and add:
   - DB_URL  = jdbc:mysql://${{MySQL.MYSQLHOST}}:${{MySQL.MYSQLPORT}}/${{MySQL.MYSQLDATABASE}}
   - DB_USER = ${{MySQL.MYSQLUSER}}
   - DB_PASSWORD = ${{MySQL.MYSQLPASSWORD}}
   - ADMIN_PASSWORD = (a strong password)
5. Settings -> Networking -> Generate Domain. Done.

## Deploy on Render
1. Render -> New -> Web Service -> connect the repo, Runtime = Docker.
2. Render has no free MySQL, so create a MySQL database on Aiven, Railway or PlanetScale-compatible host,
   then set DB_URL, DB_USER, DB_PASSWORD in the service's Environment tab.

## Real payments (Razorpay / Stripe)
Create a class implementing `PaymentGateway`, call the provider API in `charge()`, and delete `MockPaymentGateway`.
For card/UPI, verify the payment signature on the server before returning `success=true`.

## Production checklist
- Change the default admin password
- Set `spring.jpa.hibernate.ddl-auto=validate` and use Flyway migrations
- Never store card numbers; let the payment provider handle card data
