import {useCar} from "../custom_hook/useCar";
import {validateCarInput} from "../custom_hook/useCarValidate";
import ProtHeader from "../header/ProtHeader";
import CarTable from "../reusable/CarTable";
import {CarInputForm} from "../form/CarInputForm";
import {isObjectUndefined} from "../util/Util";
import useFile from "../custom_hook/useFile";

const Car = () => {
    const {dropZone, uploadFile} = useFile()
    const {car, handleChange, onSubmit, error, message} = useCar(null, validateCarInput)

    return (
        <div className="car">
            <ProtHeader/>
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
