import {useFetch} from "./useFetch";
import {useContext} from "react";
import {AuthContext} from "../../context/Context";

const useDashboard = () => {
    const authCtx = useContext(AuthContext)
    const {car, carCheckout, carCheckIn} = useFetch(authCtx.cookie.access_token)
    return {car, carCheckout, carCheckIn}
}

export {
    useDashboard
}
