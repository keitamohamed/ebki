import {imageUrl} from "../../client/apirequest/Path";

const CarouselImage = ({carClick}) => {

    return (
        <div className="content">
            <div className="image_slide">
                <img src={imageUrl(carClick.vin, true)} alt={carClick.model}/>
                <div className="img_caption">
                    <p>{`Detail: ${carClick.year} ${carClick.brand} ${carClick.model}`}</p>
                </div>
            </div>
        </div>
    )
}

export {
    CarouselImage
}