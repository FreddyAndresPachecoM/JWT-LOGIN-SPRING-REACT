export const URL_BASE = "http://localhost:8080";
export const URL_BASE_CLIENT = 'http://localhost:3000/oauth2/redirect';

export const OAUTH2_REDIRECT_URI = `${URL_BASE_CLIENT}`;

export const GOOGLE_AUTH_URL =`${URL_BASE}/oauth2/authorize/google?redirect_uri=${OAUTH2_REDIRECT_URI}`;
export const FACEBOOK_AUTH_URL="";