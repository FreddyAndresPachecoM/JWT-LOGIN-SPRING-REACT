import React,{useEffect} from 'react';

import './Login.css';
import LoginForm from './LoginForm';
import SocialLogin from './SocialLogin';

const Login = (props) =>{

    useEffect(() => {
        if(props.authenticated){
            console.log("AUTENTICAADDDDOOOOO LOGIN");
            props.history.push('/');
        }

    }, []);

    return (

        <div className="login-container">
            <div className="login-content">
                <h1 className="login-title"> Login al Autenticador</h1>
                <SocialLogin/>
                <div className="or-separator">
                    <span className="or-text">OR</span>
                </div>
                <LoginForm/>

            </div>

        </div>
    );
};
export default Login;