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


    useEffect(()=>{
        retrieveTodos()
    }, [id])

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
                        setErrorMessage("You are not authorized to view this todo.");
                    } 
                    // else {
                    //     setErrorMessage("An error occurred while retrieving the todo.");
                    // }
                    console.error(error);
                });
        }
    }
    
    function onSubmit(values){
        setDescription(values.description)
        setTargetDate(values.targetDate)
        const todo = {
            id, username, description: values.description, targetDate: values.targetDate, isDone: false
        }
        if(id===-1){
            console.log(username);
            console.log(id);
            console.log(todo);
            createTodoApi(username, id, todo)
            .then(response => {
                console.log(response);
                navigate("/todos")
            })
            .catch(err=> console.log(err))
        }
        else{
            updateTodoApi(username, id, todo)
            .then(response => {
                navigate("/todos")
            })
            .catch(err => {
                if (err.response && err.response.status === 403) {
                    setErrorMessage("You are not authorized to update this todo.");
                } else {
                    setErrorMessage("An error occurred while updating the todo.");
                }
                console.error(err);
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
            {console.log(errorMessage)}
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