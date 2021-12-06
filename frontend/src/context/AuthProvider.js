import {useCookies} from 'react-cookie'

import {AuthContext} from "./Context";

const {Provider} = AuthContext

const AuthProvider = ({children}) => {

    const [cookie, setCookie, removeCookie] = useCookies(['access_token', 'refresh_token', 'id', 'read', 'write', 'role'])

    const setAuthenticate = ({access_token, username, read, write, role}) => {
        setCookie('access_token', access_token);
        setCookie('username', username)
        setCookie('read', read);
        setCookie('write', write);
        setCookie('role', role);
    }

    const setUserID = ({driverID}) => {
        setCookie('id', driverID);
    }

    return (
        <Provider value={{
            setAuthenticate,
            setUserID,
            cookie
        }}>
            {children}
        </Provider>
    )
}

export {
    AuthProvider
}