import {useCar} from "../custom_hook/useCar";
import {validateCarInput} from "../custom_hook/useCarValidate";

const Car = () => {
    const {handleChange, onSubmit, error} = useCar(validateCarInput)

    return (
        <div className="car">
            <div className="container">
                <div className="context_new_car">
                    <form action=""
                          className="form"
                          onSubmit={onSubmit}
                    >
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
