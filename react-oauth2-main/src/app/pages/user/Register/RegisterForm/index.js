import React,{useState} from 'react';
import {register} from '../../../../../utils/APIUtils';
import Alert from 'react-s-alert';
import {withRouter} from 'react-router';


function RegisterForm(props) {

    const [state, setState] = useState({
        name:"",
        email:"",
        password:"",
     
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

    const handleSubmit = (event) =>{
        event.preventDefault();
        const registerRequest = Object.assign({},state);

        register(registerRequest)
        .then(response=>{
            Alert.success("Usted se ha registrado exitosamente");
            console.log("USUARIO REGISTRADO EXITOSAMENTE");
            props.history.push("/login");
        })
        .catch(error=>{
            Alert.error((error && error.message) || 'Algo ha salido mal');
        })
    }

    const{name,email,password,passwordverify} = state;

    return (
        <div>
            <form onSubmit={handleSubmit}>

                <div className="form-item">
                    <input 
                    type="text" 
                    name="name" 
                    className="form-control" 
                    placeholder="Enter the name"
                    value={name}
                    onChange={handleInputChange}
                    />

                </div>
                <div className="form-item">
                    <input 
                    type="email" 
                    name="email" 
                    className="form-control" 
                    placeholder="Enter the email"
                    value={email}
                    onChange={handleInputChange}
                    />

                </div>

                <div className="form-item">
                    <input 
                    type="password" 
                    name="password" 
                    className="form-control" 
                    placeholder="Enter the password"
                    value={password}
                    onChange={handleInputChange}
                    />

                </div>
                <div className="form-item">
                    <input 
                    type="password" 
                    name="passwordverify" 
                    className="form-control" 
                    placeholder="Enter the password again"

                    />

                </div>
                <div className="form-item">
                    <button 
                    type="submit" 
                    name="password-verify" 
                    className="btn btn-block btn-primary" 
                    
                    >
                        Register
                    </button>

                </div>
            </form>
        </div>
    );
}

export default withRouter(RegisterForm);