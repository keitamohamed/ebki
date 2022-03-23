import {AiOutlineClose} from "react-icons/ai";
import {CarInputForm} from "../form/CarInputForm";
import {validateCarInput} from "../custom_hook/useInputValidation";
import {useCar} from "../custom_hook/useCar";
import {useStyleComponent} from "../style/ComponentStyle";

const CarModel = ({carClick}) => {

    const {handleChange, onUpdateSubmit, error, message} = useCar(carClick, validateCarInput)
    const {removeStyle} = useStyleComponent("car_model")

    const closeModel = () => {
        removeStyle()
    }

    return (
        <div className="car_model model">
            <div className="model_content">
                <div className="model_close_btn_container">
                    <AiOutlineClose className="close_model" onClick={closeModel}/>
                </div>
                <form action=""
                      className="form"
                      onSubmit={onUpdateSubmit}
                >
                    <CarInputForm
                        carClick={carClick}
                        title={'Update Car Information'}
                        handleChange={handleChange}
                        type={'Update Car'}
                        error={error}
                        message={message}
                    />
                </form>
            </div>
        </div>
    )
}

export {
    CarModel
}
