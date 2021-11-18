import {useEffect, useState} from "react";
import {MappingType, Path} from "../../client/apirequest/Path";
import {POST_REQUEST} from "../../client/apirequest/Request";
import {useFetch} from "./useFetch";

const useCar = (carClick, validateCarInput) => {
    const {car} = useFetch()

    const initCarUpdate = (carClick) => {
        return {
            brand: carClick.brand,
            model: carClick.model,
            bodyType: carClick.bodyType,
            year: carClick.year
        }
    }

    const initCar = () => {
      return carClick !== null && carClick !== undefined ? initCarUpdate(carClick) : {
          brand: '',
          model: '',
          bodyType: '',
          year: 0,
      }
    }
    const [newCar, setNewCar] = useState(initCar)

    const [message, setMessage] = useState({msg: ''})
    const [error, setError] = useState({})

    const handleChange = event => {
        const {name, value} = event.target
        setNewCar({
            ...newCar,
            [name]: value,
        })
    }

    const onSubmit = async event => {
        event.preventDefault();

        if (!isInputValidate()) {
            return
        }
        const response = await POST_REQUEST(MappingType.POST_MAPPING, Path.ADD_NEW_CAR, null, newCar)
        if (response.status === 200) {
            setMessage({
                ...message,
                msg: `Successfully added ${newCar.year} ${newCar.brand} ${newCar.model}`
            })
        }
    }

    const onUpdateSubmit = async event => {
        event.preventDefault()
        if (!isInputValidate()) {
            return
        }
        await POST_REQUEST(MappingType.PUT_MAPPING, `${Path.UPDATE_CAR_INFO}${carClick.vin}`, null, newCar)
    }

    const isInputValidate = () => {
        setError(validateCarInput(newCar))
        return Object.values(error).every(x => x === '');
    }

    useEffect(() => {
        if (carClick !== null) {
            setNewCar(initCar())
        }
    }, [carClick])

    return {car, handleChange, onSubmit, onUpdateSubmit, error, message}
}

export {
    useCar
}
