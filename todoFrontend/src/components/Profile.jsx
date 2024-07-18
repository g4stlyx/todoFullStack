import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { getUserByUsernameApi, updateUserApi } from "./api/UserApiService";
import { ErrorMessage, Field, Form, Formik } from "formik";
import { useAuth } from "./security/AuthContext";

function Profile() {
  const authContext = useAuth();
  const username = authContext.username;
  const isAdmin = authContext.isAdmin;
  const navigate = useNavigate();
  const [message, setMessage] = useState("");

  useEffect(() => {
    retrieveUser();
  }, []);

  function retrieveUser() {
    getUserByUsernameApi(username)
      .then((response) => {
      })
      .catch((error) => console.log(error));
  }

  function onSubmit(values, { setSubmitting, resetForm }) {
    if (values.password === values.password2) {
      const updatedUser = { username, password: values.password, isAdmin };
      updateUserApi(username, updatedUser)
        .then((response) => {
          setMessage("Password updated successfully.");
          resetForm();
          setTimeout(() => {
            setMessage("");
            navigate("/profile");
          }, 2000);
        })
        .catch((error) => {
          console.log(error);
          setMessage("Failed to update password.");
        })
        .finally(() => {
          setSubmitting(false);
        });
    }
  }

  function validate(values) {
    let errors = {};
    if (!values.password || !values.password2) {
      errors.password = "Password is required!";
    }
    if (values.password !== values.password2) {
      errors.password = "Passwords should match!";
    }
    return errors;
  }

  return (
    <div className="container">
      <h1>Profile</h1>
      <h4>
        Since users have only 3 parameters -only 1 changeable-, profile page seems unnecessary now lol
      </h4>
      <div style={{ display: 'flex', justifyContent: 'center' }}>
        <Formik
          initialValues={{ password: "", password2: "" }}
          enableReinitialize={true}
          onSubmit={onSubmit}
          validate={validate}
          validateOnChange={false}
          validateOnBlur={false}
        >
          {(props) => (
            <Form>
              <ErrorMessage
                name="password"
                component="div"
                className="alert alert-warning"
              />
              {message && <div className="alert alert-info">{message}</div>}
              <fieldset className="form-group" style={{ textAlign: 'center' }}>
                <label>Password</label>
                <Field
                  type="password"
                  className="form-control"
                  name="password"
                  style={{ width: "300px", margin: "0 auto" }}
                />
              </fieldset>
              <fieldset className="form-group" style={{ textAlign: 'center' }}>
                <label>Confirm Password</label>
                <Field
                  type="password"
                  className="form-control"
                  name="password2"
                  style={{ width: "300px", margin: "0 auto" }}
                />
              </fieldset>
              <div style={{ textAlign: 'center' }}>
                <button
                  className="btn btn-success mt-3"
                  type="submit"
                  disabled={props.isSubmitting}
                >
                  Save
                </button>
              </div>
            </Form>
          )}
        </Formik>
      </div>
    </div>
  );
}

export default Profile;