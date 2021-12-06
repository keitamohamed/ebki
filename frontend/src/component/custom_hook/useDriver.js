import {useFetch} from "./useFetch";
import {useContext, useEffect, useState} from "react";
import {GET_REQUEST} from "../../client/apirequest/Request";
import {Path} from "../../client/apirequest/Path";
import {AuthContext} from "../../context/Context";

const useDriver = (driverID) => {
    const authCtx = useContext(AuthContext)
    const {driver, fetchDriver} = useFetch(authCtx.cookie.access_token)
    const [loaded, setLoaded] = useState({})
    // const [address, setAddress] = useState({})

    const address = () => {
        return driver.address
    }

    const checkout = () => {
        return driver.checkout
    }

    useEffect(() => {
        const getDriver = async () => {
            await fetchDriver(authCtx.cookie.username, authCtx.cookie.access_token)
                .then(res => {
                    setLoaded(true)
                })
        }
        const checkout = async () => {
            await GET_REQUEST(Path.FIND_CHECK_OUT_BY_DRIVER_ID, driverID, authCtx.cookie.access_token);
        }

        checkout()
        getDriver()
    }, [])

    return {driver, address, checkout, loaded}
}

export {
    useDriver
}
