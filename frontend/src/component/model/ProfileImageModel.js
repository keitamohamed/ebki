import {useStyleComponent} from "../style/ComponentStyle";
import {AiOutlineClose} from "react-icons/ai";

const ProfileImageModel = () => {

    const {removeStyle} = useStyleComponent("image_model")

    const closeModel = () => {
        removeStyle()
    }

    return (
        <div className="image_model model">
            <div className="model_content">
                <div className="model_close_btn_container">
                    <AiOutlineClose className="close_model" onClick={closeModel}/>
                </div>
                <input type="file" className="file" multiple={true}/>
            </div>
        </div>
    )
}

export {
    ProfileImageModel
}