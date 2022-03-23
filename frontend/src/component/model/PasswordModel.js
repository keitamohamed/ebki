import {useState} from "react";

import {useStyleComponent} from "../style/ComponentStyle";
import {PasswordForm} from "../form/PasswordInputForm";
import {validatePasswords} from "../form/FormValidation";
import {AiOutlineClose} from "react-icons/ai";
import {REQUEST_MAPPING} from "../../client/apirequest/Request";
import {Path} from "../../client/apirequest/Path";


const PasswordModel = ({driverID, token}) => {
    const {removeStyle} = useStyleComponent('password_model')
    const [password, setPassword] = useState({
        password: ''
    })
    const [conformPassword, setConformPassword] = useState({
        conformPassword: ''
    })
    const [error, setError] = useState({})
    const [message, setMessage] = useState({})

    const handleAuthenticateChange = event => {
        setPassword({
            ...password,
            [event.target.name]: event.target.value
        })
    }

    const handleConformPasswordChange = event => {
        setConformPassword({
            ...conformPassword,
            [event.target.name]: event.target.value,
        })
    }

    const checkValidation = () => {
        setError(validatePasswords(password, conformPassword))
    }

    const doesPasswordMatch = () => {
        return (password.password === conformPassword.conformPassword);
    }

    const onSubmit = event => {
        event.preventDefault()
        checkValidation()
        const isPasswordValid = Object.values(error).every(x => x === '')
        if (isPasswordValid === false) {
            return
        }
        const isMatchPassword = doesPasswordMatch()
        if (isMatchPassword === false) {
            setMessage('Password and conform password does not match')
            return;
        }
        REQUEST_MAPPING('PUT', Path.UPDATE_DRIVER_PASSWORD, driverID, token, password)
            .then(response => setMessage(response.headers.message))
    }

    return (
        <div className="password_model model">
            <div className="model_content">
                <div className="model_close_btn_container">
                    <AiOutlineClose onClick={removeStyle}/>
                </div>
                <form action=""
                      className="form"
                      onSubmit={onSubmit}
                >
                    <PasswordForm
                        handleAuthenticateChange={handleAuthenticateChange}
                        handleConformPasswordChange={handleConformPasswordChange}
                        error={error}
                        message={message}
                    />
                    <div className="form-group btn-container">
                        <input type="submit" className="submit" value="Update"/>
                    </div>
                </form>
            </div>
        </div>
    )
}

export default PasswordModel