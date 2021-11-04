import {useState} from "react";
import {CarContext} from "./Context";

const CarProvider = ({children}) => {
    const [clickCarVin, setClickCarVin] = useState({})
    const [clickCar, setClickCar] = useState({})

    const setCarVin = vin => {
        setClickCarVin({vin})
    }

    const getClickCarVin = () => {
      return clickCarVin.vin
    }

    const setClickCarValue = car => {
      setClickCar({
          car
      })
    }

    const getClickCar = () => {
        return clickCar.car
    }


    return (
        <CarContext.Provider value={{
            clickCarVin,
            clickCar,
            getClickCarVin,
            getClickCar,
            setCarVin,
            setClickCarValue
        }}>
            {children}
        </CarContext.Provider>
    )

}

export {
    CarProvider
}
