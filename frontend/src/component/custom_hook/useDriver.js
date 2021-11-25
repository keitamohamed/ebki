import {useFetch} from "./useFetch";
import {useEffect, useState} from "react";
import {GET_REQUEST} from "../../client/apirequest/Request";
import {Path} from "../../client/apirequest/Path";

const useDriver = (driverID) => {
    const {driver, fetchDriver} = useFetch()
    const [loaded, setLoaded] = useState({})
    // const [address, setAddress] = useState({})

    const address = () => {
        return driver.address
    }

    const checkout = () => {
        console.log(driver.checkout)
        return driver.checkout
    }

    useEffect(() => {
        const getDriver = async () => {
            await fetchDriver(driverID)
                .then(res => {
                    // setAddress(driver.address)
                    setLoaded(true)
                })
        }
        const checkout = async () => {
            const response = GET_REQUEST(Path.FIND_CHECK_OUT_BY_DRIVER_ID, driverID, null)
            console.log(response.data)
        }

        checkout()
        getDriver()
    }, [])

    return {driver, address, checkout, loaded}
}

export {
    useDriver
}
