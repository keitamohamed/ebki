const validateCarInput = value => {

    let error = {}

    error.brand = value.brand.trim() ? '' : 'Brand is required'
    error.model = value.model.trim() ? '' : 'DriverModel is required'
    error.bodyType = value.bodyType.trim() ? '' : 'Body type is required'
    error.year = value.year !== 0 ? '' : 'Year is required'

    return error
}

const validateAddressInput = value => {
    let error = {}

    error.street = value.street.trim() ? '' : 'Street is required'
    error.city = value.city.trim() ? '' : 'City is required'
    error.state = value.state.trim() ? '' : 'State is required'
    error.zipcode = value.zipcode !== 0 ? '' : 'Zipcode is required'

    return error
}

const validatePasswords = ({password, conformPassword}) => {
    let error = {}

    error.password = password.trim() ? '' : 'Validate password is required'
    error.conformPassword = conformPassword ? '' : 'Conform password required'

    return error
}

export {
    validateCarInput,
    validateAddressInput,
    validatePasswords
}
