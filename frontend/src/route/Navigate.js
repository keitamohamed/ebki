import {Redirect, Route} from "react-router-dom"

const Router = ({component: Component, ...rest}) => {

    return (
        rest.protected ? (
            <ProtectedRoute
                path={rest.path}
                isAuth={false}
                component={Component}
            />
        )
        :
        <Route
            exact
            {...rest}
            render={() => {
                return <Component />
            }}
        />
    )
}

const ProtectedRoute = ({isAuth, component: Component, ...rest}) => {
  return  (
        <Route
            {...rest}
            render={(props) => {
                return isAuth ? <Component /> : <Redirect to={{index: "/", state: {from: props.location}}} />
            }}
        />
    )
}

export {
    Router,
}
