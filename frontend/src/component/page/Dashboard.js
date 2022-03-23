import {useContext, useEffect, useState} from "react";

import {useHistory} from 'react-router-dom'
import AuthorizeHeader from "../header/ProtHeader";
import {useDashboard} from "../custom_hook/useDashboard";
import {useCheckout} from "../custom_hook/useCheckout";
import CarTable from "../reusable/CarTable";
import CheckoutTable from "../reusable/CheckoutTable";
import DisplayCarClicked from "../sub_component/useCarClicked";
import DisplayDriver from "../reusable/DisplayDriver";
import DisplayCarSearcher from "../sub_component/useCarSearch";
import {AuthContext, CarContext, DashboardContext} from "../../context/Context";
import {AiOutlineUnorderedList, BsSearch, IoAdd} from "react-icons/all";
import {getID} from "../util/Util";
import useSearch from "../custom_hook/useSearch";
import {DriverModel} from "../model/DriverModel";
import {CarModel} from "../model/CarModel";
import {useDriver} from "../custom_hook/useDriver";


const Dashboard = () => {
    const authCtx = useContext(AuthContext);
    const history = useHistory();
    const carCtx = useContext(CarContext)
    const dashCtx = useContext(DashboardContext)
    const {car} = useDashboard()
    const {checkoutList} = useCheckout(authCtx.cookie.access_token)
    const {onEnter, data, setData} = useSearch()
    const {driver} = useDriver(authCtx.cookie.username)

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

    const reSetData = () => {
        console.log('Re-Setting')
        setData(null)
    }

    useEffect(() => {
        carVin()
        setCheckoutCar()
    }, [carCtx.clickCarVin, carCtx.clickCar, checkout.id])

    return (
        <div className="dashboard">
            <div className="dash_context_layout">
                <AuthorizeHeader driver={driver}/>
                <DriverModel data={data}/>
                <CarModel carClick={carClick}/>

                <div className="dash_main">
                    <div className="context_sub_nav">
                        <div className="btn_container">
                            <li
                                className="btn li_btn"
                                id={'/car'}
                                onClick={navigate}>Car <IoAdd/>
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
                            >CheckIn <IoAdd/>
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
                            <CarTable car={car.cars} reSetData={reSetData}/>
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
                            <DisplayDriver driver={data}/>
                            : dashCtx.show === "SHOW_CAR_SEARCH" && data !== null ?
                                <DisplayCarSearcher car={data}/>
                                : dashCtx.show === "SHOW_CAR" && carClick !== null ?
                                    <DisplayCarClicked car={carClick} checkout={checkout}/> : ''
                    }
                </div>
            </div>
        </div>
    )
}

export {
    Dashboard
}
