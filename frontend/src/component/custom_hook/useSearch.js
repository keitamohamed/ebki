import {useContext, useState} from "react";
import {GET_REQUEST} from "../../client/apirequest/Request";
import {Path} from "../../client/apirequest/Path";
import {DashboardContext} from "../../context/Context";

const useSearch = () => {
    const dashCtx = useContext(DashboardContext)
    const [data, setData] = useState(null)

    const onEnter = async event => {
        if (event.key === 'Enter') {
            const {value} = event.target
            await fetchData(value)
        }
    }

    const reSetData = () => {
        setData(null)
    }

    const fetchData = async value => {
        const response = await GET_REQUEST(Path.FIND_DRIVER_BY_EMAIL, value, null)
        setData(response.data)
        dashCtx.setAction("SHOW_DRIVER")
    }

    return {onEnter, data, reSetData}
}

export default useSearch
