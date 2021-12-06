import {Redirect, Route} from "react-router-dom"
import {AuthContext} from "../context/Context";
import {useContext} from "react";

const Router = ({component: Component, ...rest}) => {
    const authCtx = useContext(AuthContext)

    const isAuth = () => {
        return !!authCtx.cookie.access_token;
    }

    const isAdminProtected = () => {
        return !!authCtx.cookie.access_token && (authCtx.cookie.role === "ROLE_ADMIN");
    }

    return (
        rest.protected ? (
                <ProtectedRoute
                    path={rest.path}
                    isAuth={isAuth()}
                    adminProtected={isAdminProtected()}
                    component={Component}
                />
            )
            :
            <Route
                exact
                {...rest}
                render={() => {
                    return <Component/>
                }}
            />
    )
}

const ProtectedRoute = ({isAuth, component: Component, ...rest}) => {
    return (
        <Route
            {...rest}
            render={(props) => {
                return isAuth ? <Component/> : <Redirect to={{index: "/", state: {from: props.location}}}/>
            }}
        />
    )
}

export default Router
