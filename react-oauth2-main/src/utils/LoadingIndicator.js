import React from 'react';

function LoadingIndicator(props) {
    return (
        (props.loading) &&  
       ( <div className="loading-indicator" style={{display:"block",textAlign:'center',marginTop:'30px'}}>
            Loading...
            
        </div>)
    );
}

export default LoadingIndicator;