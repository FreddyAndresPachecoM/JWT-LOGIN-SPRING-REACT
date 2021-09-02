import {useMemo} from 'react';
import { useLocation } from 'react-router-dom';
import queryString from "query-string";

const  useQueryParams = () =>{
    const location = useLocation();

    const params = useMemo(() => {
        const queryParams = queryString.parse(location.search);
        return queryParams;
    },[location.search]);

    return params;

    
}

export default useQueryParams;