import {useContext, useEffect} from "react";

import ProtHeader from "../header/ProtHeader";
import {Header} from "../header/Header"
import CarPostsDisplay from "../reusable/CarPostsDisplay";
import {useFetch} from "../custom_hook/useFetch";
import {BsSearch} from "react-icons/all";
import useSearch from "../custom_hook/useSearch";
import {AuthContext} from "../../context/Context";
import {isObjectUndefined} from "../util/Util";
import {useDriver} from "../custom_hook/useDriver";

const CarInventory = () => {
    const authCtx = useContext(AuthContext)
    const {driver} = useDriver(authCtx.cookie.username)
    const {car} = useFetch(authCtx.cookie.access_token)
    const {onEnterSearchCar, onKeyup, data} = useSearch()

    useEffect(() => {

    }, [car, data])

    return (
        <div className={`carInventory`}>
            {
                !isObjectUndefined(driver) ? <ProtHeader driver={driver}/> : <Header/>
            }
            <div className="car_inventory_layout">
                <div className="sub_nav">
                    <div className="content">
                        <div className="search_container">
                            <input
                                type="text"
                                className="search"
                                name="search"
                                placeholder={'Search by vin brand, model and year '}
                                onClick={onEnterSearchCar}
                                onKeyDown={onEnterSearchCar}
                                onKeyUp={onKeyup}
                            />
                            <BsSearch/>
                        </div>
                    </div>
                </div>
                <div className={`main`}>
                    {
                        car !== undefined ? (
                            data !== null ? (<CarPostsDisplay numPost={6} car={data}/>) : (
                                <CarPostsDisplay numPost={6} car={car.cars}/>)
                        ) : (<div className="no_car_display">
                            <h4>No car to display. Please check again later</h4>
                        </div>)
                    }
                </div>
            </div>
        </div>
    )
}

export default CarInventory
