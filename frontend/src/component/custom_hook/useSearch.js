import {useContext, useState} from "react";
import {GET_REQUEST} from "../../client/apirequest/Request";
import {Path} from "../../client/apirequest/Path";
import {DashboardContext} from "../../context/Context";
import {isEmailValid, isNumeric} from "../util/Util";

const useSearch = () => {
    const dashCtx = useContext(DashboardContext)
    const [data, setData] = useState(null)

    const onEnter = async event => {
        if (event.key === 'Enter') {
            const {value} = event.target
            if (inputType(value)) {
                await fetchDriver(value)
            } else {
                await fetchCar(value)
            }
        }
    }

    const inputType = value => {
        return isEmailValid(value);
    }

    const onKeyup = event => {
        const {value} = event.target
        if (value.trim() === '') {
            reSetData()
        }
    }

    const reSetData = () => {
        setData(null)
    }

    const fetchDriver = async value => {
        const response = await GET_REQUEST(Path.FIND_DRIVER_BY_EMAIL, value, null)
        setData(response.data)
        dashCtx.setAction("SHOW_DRIVER")
    }

    const fetchCar = async value => {
        let response;
        if (isNumeric(value)) {
            response = await GET_REQUEST(Path.FIND_CAR_BY_YEAR, parseInt(value), null)
        } else {
            response = await GET_REQUEST(Path.FIND_CAR_BRAND, value, null)
        }
        if (isNumeric(value) && response.data.length === 0) {
            response = await GET_REQUEST(Path.FIND_CAR_BY_ID, parseInt(value), null)
        }
        setData(response.data)
        dashCtx.setAction("SHOW_CAR_SEARCH")
    }

    return {onEnter, onKeyup, data, setData, reSetData}
}

export default useSearch
