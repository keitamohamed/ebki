import {imageUrl, Path} from "../../client/apirequest/Path";

import {REQUEST_MAPPING} from "../../client/apirequest/Request";
import {useContext} from "react";
import {AuthContext} from "../../context/Context";

const CarPosts = ({post, loading}) => {
    const authCtx = useContext(AuthContext)
    const onCheckout = async event => {
        const {id} = event.target
        await REQUEST_MAPPING('POST', Path.ADD_NEW_CHECKOUT, `${authCtx.cookie.id}/${id}`, authCtx.cookie.access_token, {})
    }

    return loading === true ? <h1>Loading...</h1> : (
        post.map((car) => {
            return (
                <div className="card"
                     key={car.vin}
                >
                    <div className="image_container">
                        <div className="content_image">
                            <img src={imageUrl(car.vin, true)} alt={car.model}/>
                        </div>
                    </div>
                    <div className="car_info">
                        <h5>{`${car.year} ${car.brand} ${car.model}`}</h5>
                        <div className="btn_container">
                            <li className="btn">View Detail</li>
                            <li className="btn"
                                id={car.vin}
                                onClick={onCheckout}
                            >Check out
                            </li>
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
