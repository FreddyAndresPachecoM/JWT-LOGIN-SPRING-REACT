import * as urls from '../api/urls';

const request = (options) =>{
    const headers = new Headers(
        {
            'Content-Type':'application/json'
        }
    );

    if(localStorage.getItem("access_token")){
        headers.append('Authorization',`Bearer ${localStorage.getItem("access_token")}`);
    }

    const defaults = {
        headers:headers
    };

    options = Object.assign({},defaults,options);

    return fetch(options.url,options)
    .then(response=>
        response.json().then(json=>{
            console.log(response);
            if(!response.ok){
                return Promise.reject(json);
            }
            return json;
        }));

};


async function requestAsync (options) {
    const headers = new Headers(
        {
            'Content-Type':'application/json'
        }
    );

    if(localStorage.getItem("access_token")){
        headers.append('Authorization',`Bearer ${localStorage.getItem("access_token")}`);
    }

    const defaults = {
        headers:headers
    };

    options = Object.assign({},defaults,options);

    const response =await fetch(options.url,options);
    const responseJson = await response.json();

    return responseJson;

};


export const register = (registerRequest) =>{
    console.log(registerRequest);
    return request(
        {
            url:`${urls.URL_BASE}/auth/register`,
            method:"POST",
            body:JSON.stringify(registerRequest)
        }
    );
};

export const login = (loginRequest) =>{
    console.log(loginRequest);
    return request(
        {
            url:`${urls.URL_BASE}/auth/login`,
            method:"POST",
            body:JSON.stringify(loginRequest)
        }
    );
};

export const getCurrentUser = () =>{
    if(!localStorage.getItem("access_token")){
        // return Promise.reject("No existe el token");
    }

    return requestAsync(
        {
            url:`${urls.URL_BASE}/user/mysuser`,
            method:"GET",
         }) /*catch all rejection=*/
         .catch(error => {
             console.log("something bad happened somewhere, rollback!");
         });
};
