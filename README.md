# How to run

Open project directory in console
```bash
./mvnw clean package -DskipTests
docker compose up
```

Port exposes
- API: http://localhost:8686/
- PostgreSQL: jdbc:postgresql://localhost:5782/postgres
    + rd_notification
    + postgres
    + 1234