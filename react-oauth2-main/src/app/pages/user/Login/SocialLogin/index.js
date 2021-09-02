import React from 'react';
import fbLogo from '../../../../../img/fb-logo.png';
import googleLogo from '../../../../../img/google-logo.png';
import * as urls from '../../../../../api/urls';

const SocialLogin = () =>{

    return(
                <div className="social-login">
                <a className="btn btn-block social-btn google" href={`${urls.GOOGLE_AUTH_URL}&type=login`}>
                    <img src={googleLogo} alt="Google" /> Log in with Google</a>
                <a className="btn btn-block social-btn facebook" href={urls.FACEBOOK_AUTH_URL}>
                    <img src={fbLogo} alt="Facebook" /> Log in with Facebook</a>
                </div>
    );
};

export default SocialLogin;