import {useState, useEffect, useContext} from "react";

import {GET_REQUEST} from "../../client/apirequest/Request";
import {Path} from "../../client/apirequest/Path";
import {CarContext} from "../../context/Context";

const useFetch = () => {
    const [driver, setDriver] = useState({})
    const [car, setCar] = useState({cars: []})
    const [carCheckout, setCarCheckout] = useState({checkoutList: []})
    const [carCheckIn, setCarCheckIn] = useState({checkInList: []})

    const fetchDriver = async driverID => {
        const response = await GET_REQUEST(Path.FIND_DRIVER_BY_ID, driverID, null)
        const {data} = response;
        setDriver(data)
    }

    useEffect(() => {
        const fetchDate = async () => {
            const result = await GET_REQUEST(Path.CAR_LIST, null, null)
            setCar({
                ...car,
                cars: result.data
            })
        }
        fetchDate().then(r => r)
    }, [])

    return {driver, car, carCheckout, carCheckIn, fetchDriver}

}

export {
    useFetch
}
