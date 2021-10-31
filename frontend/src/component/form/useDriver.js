import {useState} from "react";
import {Path} from "../../client/apirequest/Path";
import {POST_REQUEST} from "../../client/apirequest/Request";
import {reFormatDate} from "../util/Util";


const useDriver = (validateDriver, validateAddress) => {

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

    const [error, setError] = useState({
    })
    const [errorA, setErrorA] = useState({})

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

    const onSubmit = async event => {
        event.preventDefault();

        setError(validateDriver(driver))
        setErrorA(validateAddress(address))

        const isDriverValid = Object.values(error).every(x => x === '')
        const isAddressValid = Object.values(errorA).every(x => x === '' || x !== 0)
        if (!isDriverValid || !isAddressValid) {
            return
        }
 
        driver.address.push(address)
        const response = await POST_REQUEST(Path.ADD_NEW_DRIVER, null, driver)
    }

    return {handleChange, addressHandleChange, onSubmit, driver, error, errorA}
}

export {
    useDriver
}
