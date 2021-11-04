import {useContext, useEffect, useState} from "react";

import {useHistory} from 'react-router-dom'
import Header from "../header/ProtHeader";
import {useDashboard} from "../custom_hook/useDashboard";
import {useCheckout} from "../custom_hook/useCheckout";
import CarTable from "../reusable/CarTable";
import CheckoutTable from "../reusable/CheckoutTable";
import DisplayCarDetail from "../sub_component/CarDetail";

import {CarContext} from "../../context/Context";
import {BsSearch, IoAdd} from "react-icons/all";


const Dashboard = () => {
    const history = useHistory();
    const [carClick, setCarClick] = useState({})
    const [checkout, setCheckout] = useState({checkoutID: null})
    const carCtx = useContext(CarContext)

    const {car} = useDashboard()
    const {checkoutList} = useCheckout()

    const carVin = () => {
        setCarClick(carCtx.getClickCar())
    }

    const navigate = (event) => {
        let url = event.target.id
        if (!url) {
            url = event.target.parentNode.id
        }
        if (!url) {
            url = event.target.parentNode.parentNode.id
        }
        history.push(url)
    }

    const setCheckoutCar = () => {
        if (carCtx.getClickCar()) {
            const car = carCtx.getClickCar()
            const {checkout} = car;
            if (checkout) {
                setCheckout({
                    ...checkout,
                    checkoutID: checkout.checkoutID
                })
            } else {
                setCheckout({
                    ...checkout,
                    checkoutID: null,
                })
            }
        }
    }

    useEffect(() => {
        carVin()
        setCheckoutCar()
    }, [carCtx.clickCarVin, carCtx.clickCar, checkout.id])

    return (
        <div className="dashboard">
            <div className="dash_context_layout">
                <Header/>
                <div className="dash_main">
                    <div className="context_sub_nav">
                        <div className="btn_container">
                            <li
                                className="btn li_btn"
                                id={'/car'}
                                onClick={navigate}>Car <IoAdd />
                            </li>
                            <li
                                id={'/checkout'}
                                className="btn li_btn"
                                onClick={navigate}
                            >Checkout <IoAdd/>
                            </li>
                            <li
                                id={'/checkin'}
                                className="btn li_btn"
                                onClick={navigate}
                            >CheckIn <IoAdd />
                            </li>
                        </div>
                        <div className="search_container">
                            <input
                                type="text"
                                className="search"
                                placeholder={'Search....'}
                            />
                            <BsSearch/>
                        </div>
                    </div>
                    <CheckoutTable checkoutCar={checkoutList}/>
                    <CarTable car={car.cars}/>
                </div>
                <div className="checklist">
                    {
                        carClick ? (
                            <DisplayCarDetail car={carClick} checkout={checkout} />
                        ) : (
                            ''
                        )
                    }
                </div>
            </div>
        </div>
    )
}

export {
    Dashboard
}
