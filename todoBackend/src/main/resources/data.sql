insert into todos (id, description, done, target_date,username)
values (10001, 'Learn AWS', false, CURRENT_DATE(), 'g4stly');

insert into todos (id, description, done, target_date,username)
values (10002, 'Get AWS Certified', false, CURRENT_DATE(), 'g4stly');

insert into todos (id, description, done, target_date,username)
values (10003, 'Learn DevOps', false, CURRENT_DATE(), 'g4stly');

insert into todos (id, description, done, target_date,username)
values (10004, 'Learn qwewqeqw', false, CURRENT_DATE(), 'user2');

/* connecting your db to the app
    in application.properties, add these(ofc edit the required parts first):

    spring.datasource.url=jdbc:mysql://<your-rds-endpoint>:3306/<your-database-name>
    spring.datasource.username=<your-username>
    spring.datasource.password=<your-password>
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

    create application.yml and add these(ofc edit the required parts first):

    spring:
        datasource:
            url: <url>
            username: <username>
            password: <pwd>
            driver-class-name: com.mysql.cj.jdbc.Driver
        jpa:
            hibernate:
                ddl-auto: update
            properties:
            hibernate:
                dialect: org.hibernate.dialect.MySQL8Dialect

*/