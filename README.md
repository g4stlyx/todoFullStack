# Todo App Full Stack Project 

    *for documentation, goto http://localhost:8080/swagger-ui/index.html#/ after running the java spring boot application.

## Techonologies
    - [x] React (+React Router, Bootstrap, Axios, Formik...) 
    - [x] Java Spring Boot(+Spring JWT Security...)
    - [x] MySQL, AWS
    - [ ] JUnit, Jest
    - [x] Swagger
    - [x] Typescript

## How to Run/Use the App
    *first of all, you have to have a mysql database for everything to function properly.
        then connect it via application.properties in the backend folder.(/src/main/resources)
    
    -----

    * to run the api, open 01-Backend folder as a Java Maven project:
        * run `mvn spring-boot:run` in terminal or
        * simply compile and run the java app from your IDE
    
    * to run the frontend part, open 02-Frontend folder:
        * run `npm i || npm start` in terminal

## What's next?
    [x] database connection with mysql and aws
    [x] managing users
        [x] users will be able to manage their profiles, some attributes (only pwd actually)
        [x] admins will be able to CRUD users
        [x] "profile" and "users" pages
        [x] another protection for "users", only admins will be able to see that page
        [x] can i see other users' profiles using their ids or usernames in url? i shouldnt
    [x] sign up logic and page
    [x] better error handling in frontend
    [x] validations
        [x] field validations with spring in backend (password, username length. also validation for todos)
        [x] input validations in frontend (use <Formik validate={validate}>)
        [x] username's must be unique
    [x] footer
    [ ] unit testing
       [ ] java unit tests using JUnit
       [ ] js/ts unit tests using Jest
    [x] documentation with swagger
    [x] .tsx in .jsx out?
    [x] better frontend with css? (can be used material ui or bootstrap; or can be skipped entirely)
