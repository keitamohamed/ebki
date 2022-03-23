import {ClipLoader} from "react-spinners";

const Loading = ({loading, color}) => {

    return (
        <div className="loading">
            <ClipLoader loading={loading} size={100} color={color}/>
        </div>
    )
}

export default Loading