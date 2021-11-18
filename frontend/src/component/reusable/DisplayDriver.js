import {useContext, useEffect, useState} from "react";
import {isArrayEmpty} from "../util/Util";
import {ListButton} from "./ListButton";
import {DashboardContext} from "../../context/Context";
import {toggleButtonID} from "../util/Util";
import {useStyleComponent} from "../style/ComponentStyle";

const DisplayDriver = ({driver}) => {
    useContext(DashboardContext);
    const [checkout, setCheckout] = useState(driver.checkout)
    const {applyStyle} = useStyleComponent('driver_model')

    const onClick = event => {
        const id = toggleButtonID(event)
        if (id === "DELETE") {
            return
        }
        applyStyle()
    }

    useEffect(() => {
        setCheckout(driver.checkout)
    }, [])

    return (
        <div className="driver">
            {
                <div className="driver_context">
                    <h5 className="title">Driver Detail</h5>
                    <div className="context">
                        <p>Driver ID: <span>{driver.driverID}</span></p>
                        <p>Full name: <span>{`${driver.firstName} ${driver.lastName}`}</span></p>
                        <p>Gender: <span>{driver.gender}</span></p>
                        <p>Email: <span>{driver.email}</span></p>
                        <p>Phone Number: <span>{driver.phoneNum}</span></p>
                    </div>
                    <h5 className="title">Address</h5>
                    {
                        driver.address.map((add, index) => {
                            return (
                                <div className="context address" key={index}>
                                    <br/>
                                    <p>Street: <span>{add.street}</span></p>
                                    <p>City: <span>{`${add.city}`}</span></p>
                                    <p>State: <span>{add.state}</span></p>
                                    <p>Zipcode: <span>{add.zipcode}</span></p>
                                </div>
                            )
                        })
                    }
                </div>
            }
            {
                isArrayEmpty(driver.checkout) ? (
                    <div className="checkout_context">
                        <h5 className="title">Checkout Detail</h5>
                        {
                            <div
                                className="context"
                            >
                                {
                                    checkout.map((checkout, index) => {
                                        return (
                                            <li key={index}>{checkout.checkoutID}</li>
                                        )
                                    })
                                }
                            </div>
                        }
                    </div>
                ) : ''
            }
            <ListButton onClick={onClick} />
        </div>
    )
}

export default DisplayDriver
