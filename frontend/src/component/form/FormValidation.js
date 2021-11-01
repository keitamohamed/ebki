
const validateDriver = driver => {

    let driverError = {}

    driverError.firstName = driver.firstName.trim() ? '' : 'First name is required'
    driverError.lastName = driver.lastName.trim() ? '' : 'Last name is required'
    driverError.phoneNum = driver.phoneNum.trim() ? '' : 'Phone number is required'
    driverError.gender = driver.gender.trim() ? '' : 'Gender is required'
    driverError.email = driver.email.trim() ? '' : 'Email is required'
    driverError.dob = driver.dob.trim() ? '' : 'Date of birth is required'

    return driverError
}

const validateAddress = address => {
    let addressError = {}

    addressError.street = address.street.trim() ? '' : 'Street is required'
    addressError.city = address.city.trim() ? '' : 'City is required'
    addressError.state = address.state.trim() ? '' : 'State is required'
    addressError.zipcode = address.zipcode !== 0 ? '' : 'City is required'

    return addressError
}

const validateAuthenticate = auth => {
    let authError = {}

    authError.username = auth.username.trim() ? '' : 'Username is required'
    authError.password = auth.password.trim() ? '' : 'Password is required'

    return authError
}

const validateConformPassword = conformPass => {

    let conformError = {}
    conformError.conform = conformPass.conform.trim() ? '' : 'Conform password is required'

    return conformError
}

export {
    validateDriver,
    validateAddress,
    validateAuthenticate,
    validateConformPassword
}
