import {useEffect} from "react";

import ProtHeader from "../header/ProtHeader";
import CarPostsDisplay from "../reusable/CarPostsDisplay";
import {useFetch} from "../custom_hook/useFetch";
import {BsSearch} from "react-icons/all";
import useSearch from "../custom_hook/useSearch";

const CarInventory = () => {
    const {car} = useFetch()
    const {onEnterSearchCar, onKeyup, data} = useSearch()

    useEffect(() => {
    }, [car, data])

    return (
        <div className="carInventory">
            <ProtHeader/>
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
                <div className="side_nav">
                    <div className="content_filter">
                        <h5 className={"filter_title"}>Filter Inventory By</h5>
                    </div>
                </div>
                <div className={`main`}>
                    {
                        data !== null ? (<CarPostsDisplay car={data} />) : (<CarPostsDisplay car={car.cars} />)
                    }
                </div>
            </div>
        </div>
    )
}

export default CarInventory
