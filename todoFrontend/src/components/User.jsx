import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { getUserByUsernameApi, updateUserApi, createUserApi } from "./api/UserApiService";
import { ErrorMessage, Field, Form, Formik } from "formik";

/*
TODO: /administrator/users/${username} buraya gelecek, 
TODO:     username==="-1" ise kullanıcı ekleme sayfası işlevi görecek.
TODO:     "username" değişkenli bir kullanıcı varsa kullanıcı güncelleme sayfası işlevi görecek, 
TODO:     yoksa hata verecek, 

TODO: profil sayfasının aksine username, password burada gözükecek (pwd gözükecekse decode edilmeli. gözükmeli mi? username değiştirilebilmeli mi?) ayrıca buradan adminlik verip alabilecek

TODO: /administrator url'i sadece adminlere açık olacak
*/

function User() {
  const {username} = useParams()
  const navigate = useNavigate();
  const [message, setMessage] = useState("");
  const [initialValues, setInitialValues] = useState({
    username: "",
    password: "",
    isAdmin: "false",
  });

  useEffect(() => {
    if (username !== "-1") {
      retrieveUser();
    }
  }, [username]);

  function retrieveUser() {
    getUserByUsernameApi(username)
      .then((response) => {
        const user = response.data;
        setInitialValues({
          username: user.username,
          password: "",
          isAdmin: user.isAdmin ? "true" : "false",
        });
      })
      .catch((error) => {
        console.log(error);
        setMessage("User not found.");
      });
  }

  function onSubmit(values, { setSubmitting, resetForm }) {
    const userPayload = {
      username: values.username,
      password: values.password,
      isAdmin: values.isAdmin === "true",
    };

    if (username === "-1") {
      createUserApi(userPayload)
        .then((response) => {
          setMessage("User created successfully.");
          resetForm();
          setTimeout(() => {
            setMessage("");
            navigate("/users");
          }, 2000);
        })
        .catch((error) => {
          console.log(error);
          setMessage("Failed to create user.");
        })
        .finally(() => {
          setSubmitting(false);
        });
    } else {
      updateUserApi(username, userPayload)
        .then((response) => {
          setMessage("User updated successfully.");
          resetForm();
          setTimeout(() => {
            setMessage("");
            navigate("/users");
          }, 2000);
        })
        .catch((error) => {
          console.log(error);
          setMessage("Failed to update user.");
        })
        .finally(() => {
          setSubmitting(false);
        });
    }
  }

  function validate(values) {
    let errors = {};
    if (!values.username) {
      errors.username = "Username is required!";
    }
    if (!values.password) {
      errors.password = "Password is required!";
    }
    return errors;
  }

  return (
    <div className="container">
      <h1>{username === "-1" ? "Create User" : "Edit User"}</h1>
      {message && <div className="alert alert-info">{message}</div>}
      <div style={{ display: 'flex', justifyContent: 'center' }}>
        <Formik
          initialValues={initialValues}
          enableReinitialize={true}
          onSubmit={onSubmit}
          validate={validate}
          validateOnChange={false}
          validateOnBlur={false}
        >
          {(props) => (
            <Form>
              <fieldset className="form-group" style={{ textAlign: 'center' }}>
                <label>Username</label>
                <Field
                  type="text"
                  className="form-control"
                  name="username"
                  disabled={username !== "-1"}
                  style={{ width: "300px", margin: "0 auto" }}
                />
                <ErrorMessage
                  name="username"
                  component="div"
                  className="alert alert-warning"
                />
              </fieldset>
              
              <fieldset className="form-group" style={{ textAlign: 'center' }}>
                <label>Password</label>
                <Field
                  type="password"
                  className="form-control"
                  name="password"
                  style={{ width: "300px", margin: "0 auto" }}
                />
                <ErrorMessage
                  name="password"
                  component="div"
                  className="alert alert-warning"
                />
              </fieldset>

              <fieldset className="form-group" style={{ textAlign: 'center' }}>
                <label>Admin? </label>
                <div>
                  <Field type="radio" name="isAdmin" value="true" />
                  <label htmlFor="isAdmin">Yes</label>
                </div>
                <div>
                  <Field type="radio" name="isAdmin" value="false" />
                  <label htmlFor="isAdmin">No</label>
                </div>
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

export default User;