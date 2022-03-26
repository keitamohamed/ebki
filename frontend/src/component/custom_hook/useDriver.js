import {useFetch} from "./useFetch";
import {useContext, useEffect, useState} from "react";
import {GET_REQUEST} from "../../client/apirequest/Request";
import {Path} from "../../client/apirequest/Path";
import {AuthContext} from "../../context/Context";

const useDriver = (driverID) => {
    const authCtx = useContext(AuthContext)
    const {driver, fetchDriver} = useFetch(authCtx.cookie.access_token)
    const [recentCheckout, setRecentCheckout] = useState({})
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


        const findRecentCheckout = async () => {
            const response = await GET_REQUEST(Path.FIND_RECENT_CHECKOUT, driverID, authCtx.cookie.access_token)
            setRecentCheckout(response.data)
        }

        checkout()
            .then(response => response)
        getDriver()
            .then(response => response)
        findRecentCheckout()
            .then(response => response)
    }, [])

    return {driver, address, checkout, recentCheckout, loaded}
}

export {
    useDriver
}
