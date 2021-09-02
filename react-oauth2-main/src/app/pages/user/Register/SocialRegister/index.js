import React from 'react';
import * as urls from '../../../../../api/urls';
import fbLogo from '../../../../../img/fb-logo.png';
import googleLogo from '../../../../../img/google-logo.png';
function SocialRegister(props) {
    return (
        <div>
            <div className="social-signup">
                <a className="btn btn-block social-btn google" href={`${urls.GOOGLE_AUTH_URL}&type=register`}>
                    <img src={googleLogo} alt="Google" /> Sign up with Google</a>
                <a className="btn btn-block social-btn facebook" href={urls.FACEBOOK_AUTH_URL}>
                    <img src={fbLogo} alt="Facebook" /> Sign up with Facebook</a>
            </div>
        </div>
    );
}

export default SocialRegister;