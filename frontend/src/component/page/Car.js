import {useCar} from "../custom_hook/useCar";
import {validateCarInput} from "../custom_hook/useCarValidate";
import ProtHeader from "../header/ProtHeader";
import CarTable from "../reusable/CarTable";

const Car = () => {
    const {car, handleChange, onSubmit, error, message} = useCar(validateCarInput)

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
                        <CarTable car={car.cars} />
                    </div>
                    <form action=""
                          className="form"
                          onSubmit={onSubmit}
                    >
                        <div className="title_container">
                            <h5>New Car Details</h5>
                        </div>
                        <div className="form-group">
                            <input type="text"
                                   id="brand"
                                   name="brand"
                                   className={error.brand ? 'error' : ''}
                                   placeholder={error.brand ? error.brand : 'Brand'}
                                   onChange={handleChange}
                            />
                        </div>
                        <div className="form-group">
                            <input type="text"
                                   id="model"
                                   name="model"
                                   className={error.model ? 'error' : ''}
                                   placeholder={error.model ? error.model : 'Model'}
                                   onChange={handleChange}
                            />
                        </div>
                        <div className="form-group">
                            <input type="text"
                                   id="bodyType"
                                   name="bodyType"
                                   className={error.bodyType ? 'error' : ''}
                                   placeholder={error.bodyType ? error.bodyType : 'Body type'}
                                   onChange={handleChange}
                            />
                        </div>
                        <div className="form-group">
                            <input type="number"
                                   id="year"
                                   name="year"
                                   className={error.year ? 'error' : ''}
                                   placeholder={error.year ? error.year : 'Year'}
                                   onChange={handleChange}
                            />
                        </div>
                        <div className="form-group">
                            <p className={'successful_message'}>{message.msg}</p>
                        </div>
                        <div className="form-group btn-container">
                            <input type="submit" className="submit" value="Add Car" />
                        </div>
                    </form>
                </div>
            </div>
        </div>
    )
}

export {
    Car
}
