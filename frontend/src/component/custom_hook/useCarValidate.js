
const validateCarInput = value => {

    let error = {}

    error.brand = value.brand.trim() ? '' : 'Brand is required'
    error.model = value.model.trim() ? '' : 'DriverModel is required'
    error.bodyType = value.bodyType.trim() ? '' : 'Body type is required'
    error.year = value.year !== 0 ? '' : 'Year is required'

    return error
}

export {
    validateCarInput
}
