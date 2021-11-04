const CheckoutTable = ({checkoutCar: {cars}}) => {
    return (
        <div className="table_container">
            {
                cars.length > 0 ? (
                    <table className="table mt_remove">
                        <thead>
                        <h5>List of cars checkout</h5>
                        </thead>
                        <tbody>
                        {
                            cars.map((car) => {
                                return (
                                    <tr
                                        key={car.vin}
                                        id={car.vin}
                                    >
                                        <td>{car.checkout.checkoutID}</td>
                                        <td>{car.year}</td>
                                        <td>{car.brand}</td>
                                        <td>{car.model}</td>
                                        <td className={'carStatus' === 'pass' ? 'static_bg_green' : 'static_bg_red'}>
                                            Pass
                                        </td>
                                    </tr>
                                )
                            })
                        }
                        </tbody>
                    </table>
                ) : (
                    <p></p>
                )
            }
        </div>
    )
}

export default CheckoutTable
