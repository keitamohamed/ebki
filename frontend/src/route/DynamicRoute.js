import {Switch} from "react-router-dom";

import routPath from "./RoutPath";
import Router from "./Navigate"

const DynamicRoute = () => {
    return (
        <Switch>
            {
                routPath.map((route, index) => {
                    return (
                        <Router
                            key={index}
                            exact
                            path={route.path}
                            component={route.component}
                            protected={route.protected}
                        />
                    )
                })
            }
        </Switch>
    )
}

export {
    DynamicRoute
}

