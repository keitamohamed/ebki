import {useState, useEffect, useContext} from "react";

import {GET_REQUEST} from "../../client/apirequest/Request";
import {Path} from "../../client/apirequest/Path";
import {CarContext} from "../../context/Context";

const useGetData = () => {
    const [car, setCar] = useState({cars: []})
    const [carCheckout, setCarCheckout] = useState({checkoutList: []})
    const [carCheckIn, setCarCheckIn] = useState({checkInList: []})

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

    return {car, carCheckout, carCheckIn}

}

export {
    useGetData
}
