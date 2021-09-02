import logo from './logo.svg';
import React, {useState,useEffect} from 'react';
import './App.css';
import Header from './app/layouts/Header';
import {Route,Switch} from 'react-router-dom';
import Home from './app/pages/Home';
import Login from './app/pages/user/Login';
import Register from './app/pages/user/Register';
import Profile from './app/pages/user/Profile';
import OAuth2RedirectHandler from './app/pages/user/oauth2/OAuth2RedirectHandler';
import PrivateRoute from './utils/PrivateRoute';
import { getCurrentUser } from './utils/APIUtils';
import LoadingIndicator from './utils/LoadingIndicator';

function App(props) {


const initialState = {
  authenticated:false,
  currentUser:undefined,
  loading:false
}
const [state,setState] = useState(initialState);


/*useEffect( () => {
  
  console.log("ANTES");
  getCurrentUserInfo();
  console.log("DESPUES");

}, []);*/


useEffect( () => {
  
  const fetchCurrentUser = async () =>{
    const datosUser = getCurrentUser();
   
      
        setState(
          {
            currentUser:datosUser,
            authenticated:true,
            loading:false
          }
        );
        console.log(datosUser);
      

      

    
  }
  fetchCurrentUser();

}, []);


const logout =()=>{
  localStorage.removeItem("access_token");
  setState(
    {
      currentUser:null,
      authenticated:false,
      loading:false
    }
  );
};

const{currentUser,authenticated,loading} = state;


  return (
    
    <React.Fragment>

    
      <LoadingIndicator loading={loading}/>
      <div className="app">
        <div className="app-top-box">
          <Header authenticated={authenticated} onLogout={logout}/>
        </div>
        <div className="app-body">
        <Switch>
          <Route exact path ="/" authenticated={authenticated} component={Home}></Route>
          <Route path="/login" authenticated={authenticated} component={Login}></Route>
          <Route path="/register" authenticated={authenticated} component={Register}></Route>
          <PrivateRoute path="/profile" authenticated={authenticated} currentUser={currentUser} component={Profile}></PrivateRoute>
          <Route path="/oauth2/redirect" component={OAuth2RedirectHandler}></Route>
          <Route path="/notfound"></Route>
        </Switch>

        </div>
      </div>
    </React.Fragment>
  );
}

export default App;
