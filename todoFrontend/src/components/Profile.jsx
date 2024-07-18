import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { getUserByIdApi, updateUserApi } from './api/UserApiService'; // Replace with actual API service
import { useAuth } from './security/AuthContext';
import { ErrorMessage, Field, Form, Formik } from 'formik';

function Profile() {
    const authContext = useAuth();
    const navigate = useNavigate();
    const username = authContext.username;

    const [user, setUser] = useState({ name: '', password: '' });

    useEffect(() => {
        retrieveUser();
    }, []);

    function retrieveUser() {
        getUserByIdApi(username)
            .then(response => {
                setUser(response.data);
            })
            .catch(error => console.log(error));
    }

    function onSubmit(values) {
        const updatedUser = { ...user, ...values };
        updateUserApi(username, updatedUser)
            .then(response => {
                navigate('/profile');
            })
            .catch(error => console.log(error));
    }

    function validate(values) {
        let errors = {};
        if (!values.username) {
            errors.username = 'Username is required';
        }
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
                    initialValues={user}
                    enableReinitialize={true}
                    onSubmit={onSubmit}
                    validate={validate}
                    validateOnChange={false}
                    validateOnBlur={false}
                >
                    {props => (
                        <Form>
                            <ErrorMessage name="username" component="div" className="alert alert-warning" />
                            <ErrorMessage name="password" component="div" className="alert alert-warning" />
                            <fieldset className="form-group">
                                <label>Username</label>
                                <Field type="text" className="form-control" name="username" />
                            </fieldset>
                            <fieldset className="form-group">
                                <label>Password</label>
                                {/* //TODO: Type may be password. */}
                                <Field type="text" className="form-control" name="password" /> 
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
