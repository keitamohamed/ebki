const Carousel = ({post}) => {

    return (
        <div className="carousel">
            <div className="content_container">
                    {
                        post.map((car, index) => {
                            return (
                                <div className="card"
                                     key={index}
                                >
                                    <div className="image_container">
                                        <img src={ `/image/car/${car.url}`} alt={car.model}/>
                                    </div>
                                    <div className="content">
                                        <h5>{car.year}</h5>
                                        <p>{`${car.brand} ${car.model}`}</p>
                                    </div>
                                </div>
                            )
                        })
                    }
            </div>
        </div>
    )
}

export {
    Carousel
}
