import {useGetData} from "./useGetData";

const useDashboard = () => {
    const {car, carCheckout, carCheckIn} = useGetData()

    return {car, carCheckout, carCheckIn}
}

export {
    useDashboard
}
