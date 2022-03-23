const DisplayCarSearcher = ({car}) => {

    console.log("Car search", car)
    return (
        <div>
            <div className="car_card">
                {
                    car.map(car => {
                        return (
                            <div>
                                <div className="card_header">
                                    <h3>Car Vin: <span>{car.vin}</span></h3>
                                </div>
                                <div className="card_body">
                                    <h5>Brand: <span>{car.brand}</span></h5>
                                    <h5>Model: <span>{car.model}</span></h5>
                                    <h5>Body Type: <span>{car.bodyType}</span></h5>
                                    <h5>Year: <span>{car.year}</span></h5>
                                </div>
                            </div>
                        )
                    })
                }
            </div>
        </div>
    )
}

export default DisplayCarSearcher