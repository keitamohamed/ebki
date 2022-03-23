import {isObjectEmptyKeys} from "../util/Util";

const AddressInputFormat = ({title, address, onChange, error, message}) => {
    return (
        <>
            <div className="title_container">
                <h5>{title}</h5>
            </div>
            <div className="form-group">
                <input type="text"
                       id="street"
                       name="street"
                       className={error.street ? 'error' : ''}
                       placeholder={error.street ? error.street : `Street + Nr ${isObjectEmptyKeys(address) ? '' : `(Current: ${address.street})`}`}
                       onChange={onChange}
                />
            </div>
            <div className="form-group">
                <input type="text"
                       id="city"
                       name="city"
                       className={error.city ? 'error' : ''}
                       placeholder={error.city ? error.city : `City ${isObjectEmptyKeys(address) ? '' : `(Current: ${address.city})`}`}
                       onChange={onChange}
                />
            </div>
            <div className="form-group">
                <input type="text"
                       id="state"
                       name="state"
                       className={error.state ? 'error' : ''}
                       placeholder={error.state ? error.state : `State ${isObjectEmptyKeys(address) ? '' : `(Current: ${address.state})`}`}
                       onChange={onChange}
                />
            </div>
            <div className="form-group">
                <input type="number"
                       id="zipcode"
                       name="zipcode"
                       className={error.zipcode ? 'error' : ''}
                       placeholder={error.zipcode ? error.zipcode : `Zip Code ${isObjectEmptyKeys(address) ? '' : `(Current: ${address.zipcode})`}`}
                       onChange={onChange}
                />
            </div>
            <div className="form-group">
                <p className={'successful_message'}>{isObjectEmptyKeys(message) ? '' : message}</p>
            </div>
        </>
    )
}

export {
    AddressInputFormat
}