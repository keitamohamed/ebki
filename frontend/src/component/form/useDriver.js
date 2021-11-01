import {useState} from "react";
import {Path} from "../../client/apirequest/Path";
import {POST_REQUEST} from "../../client/apirequest/Request";
import {validateAuthenticate, validateConformPassword} from "./FormValidation";


const useDriver = (validateDriver, validateAddress,
                   validateAuthenticate, validateConformPassword) => {

    const [driver, setDriver] = useState({
        firstName: '',
        lastName: '',
        email: '',
        phoneNum: '',
        gender: '',
        dob: '',
        address: []
    })

    const [address, setAddress] = useState({
        street: '',
        city: '',
        state: '',
        zipcode: 0
    })

    const [authenticate, setAuthenticate] = useState({
        username: '',
        password: ''
    })

    const [conformPassword, setConformPassword] = useState({conform: ''})

    const [error, setError] = useState({})
    const [errorA, setErrorA] = useState({})
    const [errorC, setErrorC] = useState({})
    const [errorD, setErrorD] = useState({})


    const handleChange = event => {
        const {name, value} = event.target;
        setDriver({
            ...driver,
            [name]: value
        });
    };

    const addressHandleChange = event => {
        setAddress({
            ...address,
            [event.target.name] : event.target.value
        })
    }

    const handleAuthenticateChange = event => {
        const {name, value} = event.target
        setAuthenticate({
            ...authenticate,
            [name]: value
        })
    }

    const handleConformPasswordChange = event => {
        const {name, value} = event.target
        setConformPassword({
            ...conformPassword,
            [name]: value
        })
    }

    const onSubmit = async event => {
        event.preventDefault();

        setError(validateDriver(driver))
        setErrorA(validateAddress(address))
        setErrorC(validateAuthenticate(authenticate))
        setErrorD(validateConformPassword(conformPassword))

        const isDriverValid = Object.values(error).every(x => x === '')
        const isAddressValid = Object.values(errorA).every(x => x === '' || x !== 0)
        const isAuthValid = Object.values(errorC).every(x => x === '')
        const isConformPass = Object.values(errorD).every(x => x === '')

        if (!isDriverValid || !isAddressValid || isAuthValid || isConformPass) {
            return
        }

        driver.address.push(address)
        // const response = await POST_REQUEST(Path.ADD_NEW_DRIVER, null, driver)
        // console.log(response.status)
    }

    return {handleChange, addressHandleChange, handleAuthenticateChange,
        handleConformPasswordChange, onSubmit, driver, error, errorA, errorC, errorD}
}

export {
    useDriver
}
