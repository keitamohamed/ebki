const DriverInputForm = ({title, handleChange, onsubmit, error, message, type, className}) => {

    return (
        <form action="" className="form"
              onSubmit={onsubmit}
        >
            <div className="title_content">
                <h3>{title}</h3>
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
            <div className={`form-group ${className}`}>
                <input type="email"
                       id="email"
                       name="email"
                       className={error.email ? 'error' : ''}
                       placeholder={error.email ? error.email : `Email address`}
                       onChange={handleChange}
                />
            </div>
            <div className="form-group">
                <p className={'successful_message'}>{message !== 'undefined' ? message : ''}</p>
            </div>
            <div className="form-group btn-container">
                <input type="submit" className="submit" value={type}/>
            </div>
        </form>
    )

}

export {
    DriverInputForm
}
