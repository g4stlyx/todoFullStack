# Todo App Full Stack Project 

## Techonologies
    - [x] React (+React Router, Bootstrap, Axios, Formik, Material UI?...) 
    - [x] Java Spring Boot(+Spring JWT Security, H2 Database...)
    - [x] MySQL, AWS
    - [ ] JUnit
    - [ ] Swagger
    - [ ] Flutter/React Native?
    - [ ] Typescript?

## How to Run/Use the App
    *first of all, you have to have a mysql database for everything to function properly.
        then connect it via application.properties in the backend folder.(/src/main/resources)
    
    -----

    * to run the api, open 01-Backend folder as a Java Maven project:
        * run `mvn spring-boot:run` in terminal or
        * simply compile and run the java app from your IDE
    
    * to run the frontend part, open 02-Frontend folder:
        * run `npm i || npm start` in terminal

    * to run the mobile part (if exists), open 03-Mobile folder:
        * run your emulator from Android Studio or connect your own phone.
        * if you choose to connect your own phone, activate the "developer mode" and allow "USB debugging".
        * run `flutter pub get || flutter run` in terminal

## What's next?
    [ ] backend optimization, todos should be fetched faster
    [x] database connection with mysql and aws
    [x] managing users
        [x] users will be able to manage their profiles, some attributes (only pwd actually)
        [x] admins will be able to CRUD users
        [x] "profile" and "users" pages
        [x] another protection for "users", only admins will be able to see that page
        [x] can i see other users' profiles using their ids or usernames in url? i shouldnt
    [ ] sign up logic and page
    [ ] better error handling in frontend
    [ ] validations
        [ ] field validations with spring in backend (password, username length. also validation for todos)
        [ ] input validations in frontend (use <Formik validate={validate}>)
        [ ] username's must be unique
    [ ] footer
    [ ] unit testing with JUnit
    [ ] documentation with swagger
    [ ] .tsx in .jsx out?
    [ ] better frontend with css? (can be used material ui or bootstrap; or can be skipped entirely)
    [ ] mobile version with Flutter (or React Native if it seems ez -i dont think it will tho-)

