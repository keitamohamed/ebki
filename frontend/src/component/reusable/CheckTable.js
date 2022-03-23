const DisplayCheckTable = ({title, checkout, setClickCar, checkInCar}) => {
    
    return (
        <div className="check-table">
            <h5>{title}</h5>
            <div className="tbody">
                {
                    checkout !== undefined ? (
                        checkout.map((check, index) => {
                            return <li
                                id={check.checkoutID}
                                key={index}>
                                <span
                                    id={check.checkoutID}
                                    className='click_checkout'
                                    onClick={setClickCar}
                                >
                                    {check.checkoutID}
                                </span>
                                <span
                                    id={check.checkoutID}
                                    className="check_in_car"
                                    onClick={checkInCar}>
                                    CheckIn
                                </span>
                            </li>
                        })
                    ) : ''
                }
            </div>
        </div>
    )
}

export {DisplayCheckTable}