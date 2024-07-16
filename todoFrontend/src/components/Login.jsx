import "../styles/todoApp.css"
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import {useAuth} from "./security/AuthContext";

export default function Login(){

    const [username, setUsername] = useState("g4stly");
    const [password, setPassword] = useState("");
    const [showErrorMessage, setShowErrorMessage] = useState(false);

    const navigate = useNavigate();

    const authContext = useAuth()

    function handleUsernameChange(event){
        setUsername(event.target.value);
    }

    function handlePasswordChange(event){
        setPassword(event.target.value);
    }

    async function handleSubmit(){
        if(await authContext.login(username,password)){
            navigate(`/welcome/${username}`);
        }
        else{
            setShowErrorMessage(true);
        }
    }

    return(
        <div className="Login">
            <h1>Please login to continue.</h1>
            {showErrorMessage && <div className="errorMessage">Authentication Failed! Please check your credentials.</div>}
            <div className="loginForm">
                <div>
                    <label htmlFor="username">Username:</label>
                    <input type="text" name="username" value={username} onChange={handleUsernameChange} />
                </div>
                <div>
                    <label htmlFor="password">Password:</label>
                    <input type="password" name="password" value={password} onChange={handlePasswordChange} />
                </div>
                <div>
                    <button className="button loginButton" name="login" onClick={handleSubmit}>Login</button>
                </div>
            </div>
        </div>
    )

}

