import React,{useState} from 'react';
import { login } from '../../../../../utils/APIUtils';
import Alert from 'react-s-alert';

const LoginForm = (props) =>{

    const [state, setState] = useState({
        email:"",
        password:""
    });
    const handleInputChange=({target})=>{
       
        const{value,name} = target;

        console.log(value);
        setState(
            {
                ...state,
                [name]:value
            }
        );
    };

    const handleSubmit =(event) =>{
        event.preventDefault();
        const loginRequest = Object.assign({},state);
        login(loginRequest)
        .then(response=>{
            console.log("AUTENTICAADDDDOOOOO TOKEN "+response.accessToken);
            localStorage.setItem("access_token",response.accessToken);
            props.history.push('/');
        }).catch(error=>{
            Alert.error((error && error.message) || 'Lo sentimos, algo salió mal');
        })
        ;

    }

    const{email,password} = state;

    return(
        <form onSubmit={handleSubmit}>
            <div className="form-item">
                <input 
                type="email" 
                name="email" 
                id="email" 
                className="form-control"
                placeholder= "Ingrese su email"
                value={email}
                onChange={handleInputChange}
                required
                />

            </div>
            <div className="form-item">
                <input 
                type="password" 
                name="password" 
                id="password" 
                className="form-control"
                placeholder= "Ingrese su password"
                value={password}
                onChange={handleInputChange}
                required
                />

            </div>

            <div className="form-item">
                <button type="submit" className="btn btn-block btn-primary"> Login</button>
            </div>
        </form>
    );
};

export default LoginForm;