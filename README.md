# Todo App Full Stack Project 

## Techonologies
    - [x] React (+React Router, Bootstrap, Axios, Formik...) 
    - [x] Java Spring Boot(+Spring JWT Security, H2 Database...)
    - [x] MySQL, AWS
    - [ ] JUnit
    - [ ] Flutter/React Native

## How to Run/Use the App
    * to run the api, open 01-Backend folder as a Java Maven project:
        * run `mvn spring-boot:run` in terminal or
        * simply run and compile the java app from your IDE
    
    * to run the frontend part, open 02-Frontend folder:
        * run `npm i || npm start` in terminal

    * to run the mobile part (if exists), open 03-Mobile folder:
        * run your emulator from Android Studio or connect your own phone.
        * if you choose to connect your own phone, activate the "developer mode" and allow "USB debugging".
        * run `flutter pub get || flutter run` in terminal

## Existing -hardcoded- Users
    * g4stly:pwd
    * user2:pwd


## What's next?
    [ ] backend optimization, todos should be fetched faster
    [x] database connection with mysql and aws
    [ ] managing users, backend and frontend
        [ ] normal users will be able to manage their profiles, some attributes
        [ ] admins will be able to CRUD users
    [ ] sign up
    [ ] unit testing with JUnit
    [ ] mobile version with Flutter (or React Native if it seems ez -i dont think it will tho-)
