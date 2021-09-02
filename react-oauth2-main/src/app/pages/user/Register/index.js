import React,{useEffect} from 'react';
import './Register.css';
import RegisterForm from './RegisterForm';
import SocialRegister from './SocialRegister';
import {Link} from 'react-router-dom';

const Register = (props) =>{

    useEffect(() => {
        if(props.authenticated){
            props.history.push('/login')
        }

    }, []);


    return(
        <div className="signup-container">
            <div className="signup-content">
                <h1 className="signup-title"> Singup with Authenticator</h1>
                <SocialRegister/>
                <div className="or-separator">
                    <span className="or-text">OR</span>
                </div>
                <RegisterForm/>
                <span className="login-link">Already hava an account? <Link to="/login"> Login!</Link> </span>

            </div>
        </div>
    );
};

export default Register;