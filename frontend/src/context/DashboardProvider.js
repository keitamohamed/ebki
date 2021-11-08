import {DashboardContext} from "./Context";
import {useState} from "react";

const {Provider} = DashboardContext
const DashboardProvider = ({children}) => {
    const [show, setShow] = useState(null);
    const [model, setModel] = useState(null)

    const setAction = action => {
        setShow(action)
    }

    const displayModel = action => {
        setModel(action)
    }

    return (
        <Provider value={{
            setAction,
            displayModel,
            model,
            show
        }}>
            {children}
        </Provider>
    )
}

export {
    DashboardProvider
}
