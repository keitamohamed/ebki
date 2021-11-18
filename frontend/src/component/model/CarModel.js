import {AiOutlineClose} from "react-icons/ai";

import {getElement} from "../util/Util";
import {CarInputForm} from "../form/CarInputForm";
import {validateCarInput} from "../custom_hook/useCarValidate";
import {useCar} from "../custom_hook/useCar";
import {useStyleComponent} from "../style/ComponentStyle";

const CarModel = ({carClick}) => {

    const {handleChange, onUpdateSubmit, error, message} = useCar(carClick, validateCarInput)
    const {removeStyle} = useStyleComponent("car_model")

    const closeModel = () => {
        // const element = getElement("car_model");
        // element.style.zIndex = "-2"
        // element.style.opacity = "0"
        removeStyle()
    }

    return (
        <div className="car_model model" >
            <div className="model_content">
                <div className="model_close_btn_container">
                    <AiOutlineClose onClick={closeModel} />
                    <form action=""
                          className="form"
                          onSubmit={onUpdateSubmit}
                    >
                        <CarInputForm
                            title={'Update Car Information'}
                            handleChange={handleChange}
                            type={'Update Car'}
                            error={error}
                            message={message}
                        />
                    </form>
                </div>
            </div>
        </div>
    )
}

export {
    CarModel
}
