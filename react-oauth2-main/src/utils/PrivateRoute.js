import React,{useState,useEffect} from 'react';
import { Route,Redirect } from 'react-router-dom';

const PrivateRoute = ({component:Component,authenticated,currentUser,...rest}) => {

    console.log(authenticated);
    const [userData, setUserData] = useState({});

    useEffect(() => {
        currentUser.then(responseJson=>{
            setUserData(responseJson);
        });

    }, []);

    return(
        <Route
        {...rest}
        render={props=> authenticated?(
            <Component authenticated={authenticated} currentUser={userData}/>
        ):
        (
            <Redirect
            to={{
                pathname:'/login',
            }}
            />
        )
    }
        />
    )

};

export default PrivateRoute;