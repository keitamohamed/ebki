import {AddressInputFormat} from "../form/AddressInputForm";
import {useStyleComponent} from "../style/ComponentStyle";
import {AiOutlineClose} from "react-icons/ai";
import {useAddress} from "../custom_hook/useAddress";
import {useEffect} from "react";
import {validateAddressInput} from "../custom_hook/useInputValidation";

const AddressModel = ({id, type, driverID, token}) => {
    const {removeStyle} = useStyleComponent('address_model');
    const {
        address,
        onChange,
        onSubmit,
        error,
        message
    } = useAddress(id, driverID, token, validateAddressInput, type)

    const onSubmitD = event => {
        event.preventDefault();
        onSubmit()
    }

    useEffect(() => {
    }, [id, message])
    return (
        <div className="address_model model">
            <div className="model_content">
                <div className="model_close_btn_container">
                    <AiOutlineClose onClick={removeStyle}/>
                </div>
                <form
                    action=""
                    className="form"
                    onSubmit={onSubmitD}
                >
                    <AddressInputFormat
                        onChange={onChange}
                        title={"New Address"}
                        address={address}
                        error={error}
                        message={message}/>
                    <div className="form-group btn-container">
                        <input type="submit" className="submit" value={type}/>
                    </div>
                </form>
            </div>
        </div>
    )
}

export {
    AddressModel
}