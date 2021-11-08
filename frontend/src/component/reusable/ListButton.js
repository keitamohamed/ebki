import {AiOutlineEdit} from "react-icons/ai";
import {RiDeleteBin6Fill} from "react-icons/ri";

const ListButton = ({onClick}) => {

    const callClick = event => {
      onClick(event)
    }

    return (
        <div className="btn_container">
            <li className="btn"
                id={"EDIT"}
                onClick={callClick}
            >
                <AiOutlineEdit/>
            </li>
            <li className="btn"
                id={"DELETE"}
                onClick={callClick}
            >
                <RiDeleteBin6Fill/>
            </li>
        </div>
    )
}

export {
    ListButton
}
