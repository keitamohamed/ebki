import {AiOutlineClose} from "react-icons/ai";
import {useDriverRegistration} from "../custom_hook/useDriverRegistration";
import {validateDriver} from "../form/FormValidation";
import {DriverInputForm} from "../form/DriverInputForm";
import {useStyleComponent} from "../style/ComponentStyle";

const DriverModel = ({data}) => {
    const {handleChange, onSubmitOnUpdate, message, error} = useDriverRegistration(data, validateDriver)
    const {removeStyle} = useStyleComponent("driver_model")

    const closeModel = () => {
        removeStyle()
    }

    return (
        <div className="driver_model model">
            <div className="model_content">
                <div className="model_close_btn_container">
                    <AiOutlineClose onClick={closeModel}/>
                </div>
                <DriverInputForm
                    title={"Driver Update Form"}
                    handleChange={handleChange}
                    onsubmit={onSubmitOnUpdate}
                    message={message}
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
