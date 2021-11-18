import {Path} from "../../client/apirequest/Path";
import {POST_REQUEST} from "../../client/apirequest/Request";

const CarPosts = ({post, loading}) => {

    const onCheckout = async event => {
        const {id} = event.target
        await POST_REQUEST('POST', `${Path.ADD_NEW_CHECKOUT}${825125692}/${id}`, null, {})
    }

    return loading === true ? <h1>Loading...</h1> : (
        post.map((car) => {
            return (
                <div className="card"
                     key={car.vin}
                >
                    <div className="image_container"></div>
                    <div className="car_info">
                        <h5>{`${car.year} ${car.brand} ${car.model}`}</h5>
                        <div className="btn_container">
                            <li className="btn">View Detail</li>
                            <li className="btn"
                                id={car.vin}
                                onClick={onCheckout}
                            >Check out</li>
                        </div>
                    </div>
                </div>
            )
        })
    )
}

export {
    CarPosts
}
