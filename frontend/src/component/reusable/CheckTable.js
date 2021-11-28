const DisplayCheckTable = ({checkout, title}) => {


    return (
        <div className="check-table">
            <h5>{title}</h5>
            <div className="tbody">
                {
                    checkout !== undefined ? (
                        checkout.map((check, index) => {
                            return <li key={index}>
                                {check.checkoutID}
                            </li>
                        })
                    ) : ''
                }
            </div>
        </div>
    )
}

export {DisplayCheckTable}