import {AiOutlineClose} from "react-icons/ai";
import {useDriver} from "../custom_hook/useDriver";
import {validateDriver} from "../form/FormValidation";
import {DriverInputForm} from "../reusable/DriverInputForm";
import {getElement} from "../util/Util";

const DriverModel = ({data}) => {
    const {handleChange, onSubmitOnUpdate, error} = useDriver(data, validateDriver)

    const closeModel = () => {
        const element = getElement("driver_model");
        element.style.zIndex = "-2"
        element.style.opacity = "0"
    }
    return (
        <div className="driver_model model">
            <div className="model_content">
                <div className="model_close_btn_container">
                    <AiOutlineClose onClick={closeModel} />
                </div>
                <DriverInputForm
                    title={"Driver Update Form"}
                    handleChange={handleChange}
                    onsubmit={onSubmitOnUpdate}
                    error={error}
                    type={"Update"}
                    className={'display_none'}
                />
            </div>
        </div>
    )
}

export {
    DriverModel
}
