import {useCar} from "../custom_hook/useCar";
import {validateCarInput} from "../custom_hook/useInputValidation";
import ProtHeader from "../header/ProtHeader";
import CarTable from "../reusable/CarTable";
import {CarInputForm} from "../form/CarInputForm";
import {isObjectUndefined} from "../util/Util";
import {useContext} from "react";
import {AuthContext} from "../../context/Context";
import {useDriver} from "../custom_hook/useDriver";

const Car = () => {
    const authCtx = useContext(AuthContext)
    const {driver} = useDriver(authCtx.cookie.username)
    const {car, handleChange, onSubmit, error, message} = useCar(null, validateCarInput)

    return (
        <div className="car">
            <ProtHeader driver={driver}/>
            <div className="container">
                <div className="context_new_car">
                    <div className="car_container">
                        <div className="search_container">
                            <input
                                type="text"
                                className="search"
                                placeholder={'Search by vin, brand, year or ( brand model and year )'}
                            />
                        </div>
                        {
                            !isObjectUndefined(car.cars) ? (<CarTable car={car.cars}/>) : ""
                        }
                    </div>
                    <form action=""
                          className="form"
                          onSubmit={onSubmit}
                    >
                        <CarInputForm
                            carClick={null}
                            title={'New Car Details'}
                            handleChange={handleChange}
                            message={message}
                            error={error}/>
                    </form>
                </div>
            </div>
        </div>
    )
}

export {
    Car
}
