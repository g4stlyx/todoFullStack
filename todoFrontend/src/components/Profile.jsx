import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { getUserByUsernameApi, updateUserApi } from './api/UserApiService'; // Replace with actual API service
import { ErrorMessage, Field, Form, Formik } from 'formik';
import { useAuth } from './security/AuthContext';

//TODO: password will be decoded before be shown to the user
//TODO: there will be a 2nd password field, after checking they are same request will be sent
//TODO: if successful, show a message that saying the ps was successfully done

function Profile() {
    const authContext = useAuth();
    const username = authContext.username
    const isAdmin = authContext.isAdmin
    const navigate = useNavigate();

    const [password, setPassword] = useState('')

    useEffect(() => {
        retrieveUser();
    }, []);

    function retrieveUser() {
        getUserByUsernameApi(username)
            .then(response => {
                setPassword(response.data.password)
            })
            .catch(error => console.log(error));
    }

    function onSubmit(values) {
        setPassword(values.password)
        const updatedUser = {username, password: values.password, isAdmin}
        updateUserApi(username, updatedUser)
            .then(response => {
                navigate('/profile');
            })
            .catch(error => console.log(error));
    }

    function validate(values) {
        let errors = {};
        if (!values.password) {
            errors.password = 'Password is required';
        }
        return errors;
    }

    return (
        <div className="container">
            <h1>User Profile</h1>
            <h3>Since users have only 3 fields, profile page seems unnecessary now lol</h3>
            <div>
                <Formik
                    initialValues={{password}}
                    enableReinitialize={true}
                    onSubmit={onSubmit}
                    validate={validate}
                    validateOnChange={false}
                    validateOnBlur={false}
                >
                    {props => (
                        <Form>
                            <ErrorMessage name="password" component="div" className="alert alert-warning" />
                            <fieldset className="form-group">
                                <label>Password</label>
                                <Field type="password" className="form-control" name="password" /> 
                            </fieldset>
                            <button className="btn btn-success mt-3" type="submit">Save</button>
                        </Form>
                    )}
                </Formik>
            </div>
        </div>
    );
}

export default Profile;
