import {useEffect, useState} from "react";
import {GET_REQUEST} from "../../client/apirequest/Request";
import {Path} from "../../client/apirequest/Path";

const useCheckout = (token) => {
    const [checkoutList, setCheckoutList] = useState({cars: []})

    useEffect(() => {
        const fetchData = async () => {
            await GET_REQUEST(Path.CHECKOUT_LIST, null, token)
                .then(response => {
                    const {data} = response
                    setCheckoutList({
                        ...checkoutList,
                        cars: data
                    })
                })
        }
        fetchData()
            .then(r => r)
            .catch(error => error)
    }, [])

    return {checkoutList}
}

export {
    useCheckout
}
