import {useEffect, useState} from "react";

import {GET_REQUEST} from "../../client/apirequest/Request";
import {Path} from "../../client/apirequest/Path";

const useFetch = (token) => {
    const [driver, setDriver] = useState({})
    const [car, setCar] = useState({cars: []})
    const [carCheckout, setCarCheckout] = useState({checkoutList: []})
    const [carCheckIn, setCarCheckIn] = useState({checkInList: []})

    const fetchDriver = async (driverID, token) => {
        const response = await GET_REQUEST(Path.FIND_DRIVER_BY_USERNAME, driverID, token)
        const {data} = response;
        setDriver(data)
        return data
    }

    const reSetData = () => {
        setDriver({});
        setCarCheckout({checkoutList: []});
        setCarCheckIn({checkInList: []})
    }

    useEffect(() => {
        const fetchDate = async () => {
            const result = await GET_REQUEST(Path.CAR_LIST, null, token)
            setCar({
                ...car,
                cars: result.data
            })
        }
        fetchDate().then(r => r)
    }, [])

    return {driver, car, carCheckout, carCheckIn, fetchDriver, reSetData}

}

export {
    useFetch
}
