const Path = {
    LOGIN: ('/login'),
    ADD_NEW_DRIVER: ('driver/add'),
    GET_DRIVER: ('driver/get_driver'),
    FIND_DRIVER_BY_ID: ('driver/find_by_id/'),
    FIND_DRIVER_BY_EMAIL: ("driver/email/"),
    FIND_DRIVER_BY_USERNAME: ("driver/find_by_username/"),
    FIND_CAR_BY_ID: ('car/vin/'),
    FIND_CAR_BRAND: ('car/brand/'),
    FIND_CAR_BY_YEAR: ('car/year/'),
    UPDATE_DRIVER_INFO: ('driver/update/'),
    DELETE_DRIVER: ('driver/delete/'),
    ADD_NEW_CAR: ('car/add'),
    CAR_LIST: ('car/carList'),
    CAR_SAVE_IMAGE: ('car/save-car-image/'),
    CAR_DOWNLOAD_IMAGE: ("car/download-image/"),
    UPDATE_CAR_INFO: ('car/update/'),
    CHECKOUT_LIST: ('checkout/checkout_list'),
    FIND_CHECK_OUT_BY_DRIVER_ID: ("checkout/find_checkout_by_driver_id/"),
    ADD_NEW_CHECKOUT: ('checkout/add/')
}

const MappingType = {
    POST_MAPPING: ('POST'),
    PUT_MAPPING: ('PUT'),
}

const imageUrl = (id, isCarImage) => {
    return isCarImage === true ? `http://localhost:8080/ebik/car/download-image/${id}` : ''
}

export {
    Path,
    MappingType,
    imageUrl
}
