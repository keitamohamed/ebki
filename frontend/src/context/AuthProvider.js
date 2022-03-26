import {useHistory} from "react-router-dom";
import {useCookies} from 'react-cookie'

import {AuthContext} from "./Context";

const {Provider} = AuthContext

const AuthProvider = ({children}) => {
    const history = useHistory()
    const [cookie, setCookie, removeCookie] = useCookies(['access_token', 'refresh_token', 'id', 'name', 'read', 'write', 'role'])

    const setAuthenticate = ({access_token, username, read, write, role}) => {
        setCookie('access_token', access_token);
        setCookie('username', username)
        setCookie('read', read);
        setCookie('write', write);
        setCookie('role', role);
    }

    const setUserID = ({firstName, driverID}) => {
        setCookie('name', firstName)
        setCookie('id', driverID);
    }

    const logout = () => {
        const remove = ['access_token', 'id', 'username', 'refresh_token', 'id', 'name', 'read', 'write', 'role']
        remove.forEach(name => removeCookie(name))
        removeCookie('read')
        history.push("/")
    }

    return (
        <Provider value={{
            setAuthenticate,
            setUserID,
            cookie,
            logout
        }}>
            {children}
        </Provider>
    )
}

export {
    AuthProvider
}