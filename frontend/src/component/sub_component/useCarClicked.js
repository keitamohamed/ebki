import {isObjectUndefined, toggleButtonID} from "../util/Util";
import {ListButton} from "../reusable/ListButton";
import {useStyleComponent} from "../style/ComponentStyle";

const DisplayCarClicked = ({car, checkout}) => {
    const {applyStyle} = useStyleComponent("car_model")

    const onClick = event => {
        const clickButtonID = toggleButtonID(event)

        if (clickButtonID === "DELETE") {
            return
        }
        applyStyle()
    }

    return (
        <div>
            {
                !isObjectUndefined(car) ? (
                    <div className="car_card">
                        <div className="card_header">
                            <h3>Car Vin: <span>{car.vin}</span></h3>
                        </div>
                        <div className="card_body">
                            <h5>Brand: <span>{car.brand}</span></h5>
                            <h5>Model: <span>{car.model}</span></h5>
                            <h5>Body Type: <span>{car.bodyType}</span></h5>
                            <h5>Year: <span>{car.year}</span></h5>
                        </div>
                        {
                            checkout.checkoutID !== null ? (
                                <div className="checkout_detail">
                                    <h2 className="car_checkout">Checkout Detail</h2>
                                    <div className="card_header">
                                        <h3>Checkout ID: <span>{checkout.checkoutID}</span></h3>
                                    </div>
                                </div>
                            ) : (
                                ''
                            )
                        }
                        <ListButton onClick={onClick}/>
                    </div>
                ) : ''
            }
        </div>
    )
}

export default DisplayCarClicked
