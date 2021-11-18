import {useContext, useEffect, useState} from "react";

import {useHistory} from 'react-router-dom'
import Header from "../header/ProtHeader";
import {useDashboard} from "../custom_hook/useDashboard";
import {useCheckout} from "../custom_hook/useCheckout";
import CarTable from "../reusable/CarTable";
import CheckoutTable from "../reusable/CheckoutTable";
import DisplayCarClicked from "../sub_component/useCarClicked";
import DisplayDriver from "../reusable/DisplayDriver";

import {CarContext} from "../../context/Context";
import {BsSearch, IoAdd, AiOutlineUnorderedList} from "react-icons/all";
import {getID} from "../util/Util";
import useSearch from "../custom_hook/useSearch";

import {DashboardContext} from "../../context/Context";
import {DriverModel} from "../model/DriverModel";
import {CarModel} from "../model/CarModel";


const Dashboard = () => {
    const history = useHistory();
    const carCtx = useContext(CarContext)
    const dashCtx = useContext(DashboardContext)
    const {car} = useDashboard()
    const {checkoutList} = useCheckout()
    const {onEnter, data} = useSearch()

    const [carClick, setCarClick] = useState(null)
    const [checkout, setCheckout] = useState({checkoutID: null})
    const [show, setShow] = useState('Car')

    const carVin = () => {
        const car = carCtx.getClickCar()
        if (car !== undefined) {
            setCarClick(car[0])
        }
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
            const {checkout} = car[0];
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

    const onClick = (event) => {
        setShow(getID(event))
    }

    useEffect(() => {
        carVin()
        setCheckoutCar()
    }, [carCtx.clickCarVin, carCtx.clickCar, checkout.id])

    return (
        <div className="dashboard">
            <div className="dash_context_layout">
                <Header/>
                <DriverModel data={data} />
                <CarModel carClick={carClick} />

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
                        <div className="btn_show">
                            <li className="btn"
                                id={"Car"}
                                onClick={onClick}
                            >Car <AiOutlineUnorderedList/>
                            </li>
                            <li className="btn"
                                id={"checkout"}
                                onClick={onClick}
                            >
                                Checkout
                                <AiOutlineUnorderedList/></li>
                            <li className="btn"
                                id={"Checkin"}
                                onClick={onClick}
                            >
                                Checkin
                                <AiOutlineUnorderedList/></li>
                        </div>
                    </div>
                    {
                        show === 'Car' ? (
                            <CarTable car={car.cars}/>
                        ) : (
                            <CheckoutTable checkoutCar={checkoutList}/>
                        )
                    }
                </div>
                <div className="checklist">
                    <div className="search_container">
                        <input
                            type="text"
                            className="search"
                            name="search"
                            placeholder={'Search...by id, vin  email, brand, year '}
                            onClick={onEnter}
                            onKeyDown={onEnter}
                        />
                        <BsSearch/>
                    </div>
                    {
                        dashCtx.show === "SHOW_DRIVER" && data !== null ?
                            <DisplayDriver driver={data} />
                        : dashCtx.show === "SHOW_CAR" && carClick !== null?
                                <DisplayCarClicked car={carClick} checkout={checkout} /> : ''
                    }
                </div>
            </div>
        </div>
    )
}

export {
    Dashboard
}
