import {useState} from "react";
import {POST_REQUEST} from "../../client/apirequest/Request";
import {Path, MappingType} from "../../client/apirequest/Path";

const CarCard = ({data}) => {
    const [car, setCar] = useState(data)

    const onCheckout = async event => {
        await POST_REQUEST(MappingType.POST_MAPPING,`${Path.ADD_NEW_CHECKOUT}${825125692}/${car.vin}`, null, {})
    }


    return (
        <div className="card">
            <div className="card_header">
                <h3>{`Brand: ${car.brand}`}</h3>
            </div>
            <div className="card_body">
                <h5>{`Model: ${car.model}`}</h5>
                <h5>{`Body Type: ${car.bodyType}`}</h5>
                <h5>{`Year: ${car.year}`}</h5>
            </div>
            <div className="card_action">
                <input type="button" className="btn" value='View' />
                <input type="button"
                       className="btn"
                       value='Checkout'
                       onClick={onCheckout}
                />
            </div>
        </div>
    )
}

export {
    CarCard
}
