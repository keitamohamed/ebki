import {useFetch} from "./useFetch";

const useDashboard = () => {
    const {car, carCheckout, carCheckIn} = useFetch()

    return {car, carCheckout, carCheckIn}
}

export {
    useDashboard
}
