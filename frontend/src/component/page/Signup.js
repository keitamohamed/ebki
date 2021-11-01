import {Header} from "../Header";
import {useDriver} from "../form/useDriver";
import {validateDriver, validateAddress, validateAuthenticate, validateConformPassword} from "../form/FormValidation";

const Signup = () => {
    const {handleChange, addressHandleChange, handleAuthenticateChange,
        handleConformPasswordChange, onSubmit, error, errorA, errorC, errorD}
        = useDriver(validateDriver, validateAddress, validateAuthenticate, validateConformPassword)

    return (
        <div className="signup">
            <Header contextRight={'context_active_false'}/>
            <div className="container">
                <form action=""
                      className="form"
                      onSubmit={onSubmit}
                >
                    <div className="context_left">
                        <div className="title_title">
                            <h3>General Information</h3>
                        </div>
                        <div className="form-group">
                            <input type="text"
                                   id="firstName"
                                   name="firstName"
                                   className={error.firstName ? 'error' : ''}
                                   placeholder={error.firstName ? error.firstName : 'First Name'}
                                   onChange={handleChange}
                            />
                        </div>
                        <div className="form-group">
                            <input type="text"
                                   id="lastName"
                                   name="lastName"
                                   className={error.lastName ? 'error' : ''}
                                   placeholder={error.lastName ? error.lastName : `Last Name`}
                                   onChange={handleChange}
                            />
                        </div>
                        <div className="form-group">
                            <div className="form-group-row">
                                <div className="form-group">
                                    <input type="radio"
                                           id="male"
                                           value={"Male"}
                                           name="gender"
                                           onChange={handleChange}
                                    />
                                    <label htmlFor="male" className="radio-label">Male</label>
                                </div>
                                <div className="form-group">
                                    <input type="radio"
                                           id="female"
                                           value={"Female"}
                                           name="gender"
                                           onChange={handleChange}
                                    />
                                    <label htmlFor="female" className="radio-label">Female</label>
                                </div>
                            </div>
                            {error.gender && <p className='error'>{error.gender}</p>}
                        </div>
                        <div className="form-group">
                            <input type="date"
                                   id="dob"
                                   name="dob"
                                   onChange={handleChange}
                            />
                            {error.dob && <p className='error'>{error.dob}</p>}
                        </div>

                        <div className="form-group">
                            <input type="text"
                                   id="phoneNum"
                                   name="phoneNum"
                                   className={error.phoneNum ? 'error' : ''}
                                   placeholder={error.phoneNum ? error.phoneNum : `Phone Number`}
                                   onChange={handleChange}
                            />
                        </div>
                        <div className="form-group">
                            <input type="email"
                                   id="email"
                                   name="email"
                                   className={error.email ? 'error' : ''}
                                   placeholder={error.email ? error.email : `Email address`}
                                   onChange={handleChange}
                            />
                        </div>
                    </div>
                    <div className="context_right">
                        <div className="title_title">
                            <h3>Address Detail</h3>
                        </div>
                        <div className="form-group">
                            <input type="text"
                                   id="street"
                                   name="street"
                                   className={errorA.street ? 'error' : ''}
                                   placeholder={errorA.street ? errorA.street : `Street + Nr ${''}`}
                                   onChange={addressHandleChange}
                            />
                        </div>
                        <div className="form-group">
                            <input type="text"
                                   id="city"
                                   name="city"
                                   className={errorA.city ? 'error' : ''}
                                   placeholder={errorA.city ? errorA.city : `City`}
                                   onChange={addressHandleChange}
                            />
                        </div>
                        <div className="form-group">
                            <input type="text"
                                   id="state"
                                   name="state"
                                   className={errorA.state ? 'error' : ''}
                                   placeholder={errorA.state ? errorA.state : `State`}
                                   onChange={addressHandleChange}
                            />
                        </div>
                        <div className="form-group">
                            <input type="number"
                                   id="zipcode"
                                   name="zipcode"
                                   className={errorA.zipcode ? 'error' : ''}
                                   placeholder={errorA.zipcode ? errorA.zipcode : `Zip Code`}
                                   onChange={addressHandleChange}
                            />
                        </div>

                        <div className="title_title add_margin">
                            <h3>Authentication Detail</h3>
                        </div>

                        <div className="form-group">
                            <input type="text"
                                   id="username"
                                   name="username"
                                   className={errorC.username ? 'error' : ''}
                                   placeholder={errorC.username ? errorC.username : `username`}
                                   onChange={handleAuthenticateChange}
                            />
                        </div>
                        <div className="form-group">
                            <input type="password"
                                   id="password"
                                   name="password"
                                   className={errorC.password ? 'error' : ''}
                                   placeholder={errorC.password ? errorC.password : `Password`}
                                   onChange={handleAuthenticateChange}
                            />
                        </div>

                        <div className="form-group">
                            <input type="password"
                                   id="conform"
                                   name="conform"
                                   className={errorD.conform ? 'error' : ''}
                                   placeholder={errorD.conform ? errorD.conform : `Conform password`}
                                   onChange={handleConformPasswordChange}
                            />
                        </div>
                        <div className="form-group btn-container">
                            <input type="submit" className="submit" value="Submit" />
                        </div>
                    </div>
                </form>
            </div>
        </div>
    )
}

export {
    Signup
}
