import {isObjectEmptyKeys} from "../util/Util";

const PasswordForm = ({handleAuthenticateChange, handleConformPasswordChange, error, message}) => {


    return (
        <>
            <div className="form-group">
                <input type="password"
                       id="password"
                       name="password"
                       className={error.password ? 'error' : ''}
                       placeholder={error.password ? error.password : `Password`}
                       onChange={handleAuthenticateChange}
                />
            </div>
            <div className="form-group">
                <input type="password"
                       id="conformPassword"
                       name="conformPassword"
                       className={error.conformPassword ? 'error' : ''}
                       placeholder={error.conformPassword ? error.conformPassword : `Conform password`}
                       onChange={handleConformPasswordChange}
                />
            </div>
            <div className="form-group">
                <p className={'successful_message'}>{isObjectEmptyKeys(message) ? '' : message}</p>
            </div>
        </>
    )
}

export {
    PasswordForm
}