import React from 'react';

import {Link,NavLink} from 'react-router-dom';
import './Header.css';

const Header = ({authenticated,onLogout}) =>{

    return(
        <header className="app-header">
            <div className="container">
                <div className="app-branding">
                    <Link to="/" className="app-title"> Authenticator </Link>
                </div>
                <div className="app-options">
                    <nav className="app-nav">

                        
                        {(authenticated)&&
                        (<ul>
                            <li>
                                <NavLink to="/profile">
                                    Profile
                                </NavLink>
                            </li>
                            <li>
                                <a onClick={onLogout}>
                                    Logout
                                </a>
                            </li>
                        </ul>)}
                        
                        {(!authenticated)&&
                        (
                        <ul>
                            <li>
                                <NavLink to="/login">
                                    Login
                                </NavLink>
                            </li>
                            <li>
                                <NavLink to="/register">
                                    Register
                                </NavLink>
                            </li>
                        </ul>)}

                    </nav>

                </div>

            </div>

        </header>

    );
};
export default Header;