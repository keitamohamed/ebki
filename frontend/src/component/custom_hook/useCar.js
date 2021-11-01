import {useState} from "react";
import {Path} from "../../client/apirequest/Path";
import {POST_REQUEST} from "../../client/apirequest/Request";

const useCar = (validateCarInput) => {
    const [car, setCar] = useState({
        brand: '',
        model: '',
        bodyType: '',
        year: 0,
    })
    const [error, setError] = useState({})

    const handleChange = event => {
        const {name, value} = event.target
        setCar({
            ...car,
            [name]: value,
        })
    }

    const onSubmit = async event => {
        event.preventDefault();
        setError(validateCarInput(car))

        const isCarValid = Object.values(error).every(x => x === '')
        if (!isCarValid) {
            return
        }
        const  response = await POST_REQUEST(Path.ADD_NEW_CAR, null, car)
        console.log(response)
    }


    return {handleChange, onSubmit, error}
}

export {
    useCar
}
