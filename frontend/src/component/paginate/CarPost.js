import {useContext} from "react";

import {CarContext} from "../../context/Context";
import UseClickCarID from "../custom_hook/useClickCarID";

const CarPost = (post, loading) => {
    const carCtx = useContext(CarContext)

    const setClickDataID = event => {
        carCtx.setCarVin(UseClickCarID(event))
    }

    return loading === true ? <h1>Loading...</h1> : (
        post.post.map((car) => {
            return (
                <tr
                    key={car.vin}
                    id={car.vin}
                    onClick={setClickDataID}
                >
                    <td>{car.year}</td>
                    <td>{car.brand}</td>
                    <td>{car.model}</td>
                    <td>{car.bodyType}</td>
                </tr>
            )
        })
    )

}

export default CarPost
