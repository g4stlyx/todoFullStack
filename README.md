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
    [ ] managing users
        [ ] normal users will be able to manage their profiles, some attributes
        [ ] admins will be able to CRUD users
        [ ] "profile" and "users" pages
        [ ] another protection for "users", only admins will be able to see that page
        [ ] can i see other users' profiles using their ids or usernames in url? i shouldnt
    [ ] sign up
    [ ] validations
        [ ] field validations with spring in backend
        [ ] input validations in frontend (use <Formik validate={validate}>)
    [ ] unit testing with JUnit
    [ ] mobile version with Flutter (or React Native if it seems ez -i dont think it will tho-)
