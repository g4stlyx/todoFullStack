import { useNavigate, useParams } from "react-router-dom"
import { getTodoApi, updateTodoApi, createTodoApi } from "./api/TodoApiService"
import { useAuth } from "./security/AuthContext"
import { useEffect, useState } from "react"
import { ErrorMessage, Field, Form, Formik } from "formik"
import moment from "moment"

export default function Todo(){
    const authContext = useAuth()
    const navigate = useNavigate()

    const {id} = useParams()
    const username = authContext.username

    const [description, setDescription] = useState('')
    const [targetDate, setTargetDate] = useState('')
    const [errorMessage, setErrorMessage] = useState('')


    useEffect(() => {
        if (id !== '-1') {
            retrieveTodos();
        }
    }, [id]);

    function retrieveTodos() {
        if (id !== -1) {
            getTodoApi(username, id)
                .then(response => {
                    setDescription(response.data.description);
                    setTargetDate(response.data.targetDate);
                    setErrorMessage('');
                })
                .catch(error => {
                    if (error.response && error.response.status === 403) {
                        navigate("/not-authorized")
                    } 
                    else if(error.response && error.response.status ===404){
                        navigate("/not-found")
                    }
                    else if(error.response && error.response.status===400){
                        navigate("/bad-request")
                    }else if(error.response && error.response.status===500){
                        navigate("server-error")
                    }
                    else {
                        setErrorMessage("An error occurred while updating the todo.");
                    }
                    console.error(error);
                });
        }
    }
    
    function onSubmit(values) {
        setDescription(values.description);
        setTargetDate(values.targetDate);
    
        const todo = {
            username,
            description: values.description,
            targetDate: values.targetDate,
            isDone: false
        };
    
        if (id === '-1') {
            createTodoApi(username, todo)
                .then(response => {
                    navigate("/todos");
                })
                .catch(err => {
                    console.log(err);
                });
        } else {
            todo.id = id;
            updateTodoApi(username, id, todo)
                .then(response => {
                    navigate("/todos");
                })
                .catch(error => {
                    if (error.response && error.response.status === 403) {
                        navigate("/not-authorized")
                    } 
                    else if(error.response && error.response.status ===404){
                        navigate("/not-found")
                    }
                    else if(error.response && error.response.status===400){
                        navigate("/bad-request")
                    }else if(error.response && error.response.status===500){
                        navigate("server-error")
                    }
                    else {
                        setErrorMessage("An error occurred while updating the todo.");
                    }
                    console.error(error);
                });
        }
    }

    function validate(values){
        let errors= {}
        if(values.description.length<5 || values.description == null){
            errors.description= "Description must be at least 5 characters long"
        }
        if(values.targetDate == null || values.targetDate === "" || !moment(values.targetDate).isValid()){
            errors.targetDate = "Target date must be specified"
        }
        return errors
    }

    return(
        <div className="container">
            <h1>Enter Todo Details</h1>
            {errorMessage && <div className="alert alert-danger">{errorMessage}</div>}
            <div>
                <Formik initialValues={{description,targetDate}} enableReinitialize={true} onSubmit= {onSubmit} validate={validate} validateOnChange={false} validateOnBlur={false}>
                    {
                        (props) => (
                            <Form>
                                <ErrorMessage name="description" component="div" className="alert alert-warning"/>
                                <ErrorMessage name="targetDate" component="div" className="alert alert-warning"/>
                                <fieldset className="form-group">
                                    <label>Description</label>
                                    <Field type="text" className="form-control" name="description"></Field>
                                    <label>Target Date</label>
                                    <Field type="date" className="form-control" name="targetDate"></Field>
                                </fieldset>
                                <div>
                                    <button className="btn btn-success m-5" type="submit">Save</button>
                                </div>
                            </Form>
                        )
                    }
                </Formik>
            </div>
        </div>
    )
}