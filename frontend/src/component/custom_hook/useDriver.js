import {useEffect, useState} from "react";
import {POST_REQUEST, PUT_REQUEST} from "../../client/apirequest/Request";
import {Path,MappingType} from "../../client/apirequest/Path";
import {isObjectEmpty} from "../util/Util";


const useDriver = (data, validateDriver, validateAddress,
                   validateAuthenticate, validateConformPassword) => {

    const initDriverUpdate = data => {
        return {
            driverID: data.driverID,
            firstName: data.firstName,
            lastName: data.lastName,
            email: data.email,
            phoneNum: data.phoneNum,
            gender: data.gender,
            dob: data.dob
        }
    }

    const initDriver = (data) => {
        return isObjectEmpty(data) === false ? initDriverUpdate(data) : {
            firstName: '',
            lastName: '',
            email: '',
            phoneNum: '',
            gender: '',
            dob: '',
            address: []
        }
    }

    const [driver, setDriver] = useState(initDriver(data))
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

        if (!isDriverValid || !isAddressValid || !isAuthValid || !isConformPass) {
            return
        }

        driver.address.push(address)
        await POST_REQUEST(MappingType.POST_MAPPING, Path.ADD_NEW_DRIVER, null, driver)
    }

    const onSubmitOnUpdate = async event => {
        event.preventDefault()

        setError(validateDriver(driver))
        const isDriverValid = Object.values(error).every(x => x === '')
        if (!isDriverValid) {
            return
        }
        await POST_REQUEST(MappingType.PUT_MAPPING, `${Path.UPDATE_DRIVER_INFO}${driver.driverID}`, null, driver)
    }

    useEffect(() => {
        if (data !== null) {
            setDriver(initDriver(data))
        }
    }, [data])

    return {handleChange, addressHandleChange, handleAuthenticateChange,
        handleConformPasswordChange, onSubmit, onSubmitOnUpdate, driver, error, errorA, errorC, errorD}
}

export {
    useDriver
}
