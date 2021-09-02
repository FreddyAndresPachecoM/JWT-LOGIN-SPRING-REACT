import React,{useEffect} from 'react';
import useQueryParams from '../../../../../hooks/useQueryParams';


function OAuth2RedirectHandler(props) {

    const getUrlParameter = (name) =>{
        console.log(name);
        name = name.replace(/[\[]/,'\\[').replace(/[\]]/,'\\]');
        console.log(name);
        var regex =new RegExp('[\\?&]'+name+'=([^&#]*)');
        var results = regex.exec(props.location.search);
        console.log("----------------------------------------------");
        console.log(results);
        return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
    };

    //const token = getUrlParameter('token');
    //const error = getUrlParameter('error');
    const{token,error} = useQueryParams();

    useEffect(() => {
        console.log(token);

        if(token){
            localStorage.setItem("access_token",token);
            console.log("AUTENTICAADDDDOOOOO token gmail "+token);
            props.history.push('/profile');
        }
        else{
            props.history.push('/login');
        }
 console.log(error);
    }, []);

    return (
        <div>
            
        </div>
    );
}

export default OAuth2RedirectHandler;