const Path = {
    LOGIN: ('driver/login'),
    ADD_NEW_DRIVER: ('driver/add'),
    GET_DRIVER: ('driver/get_driver'),
    FIND_DRIVER_BY_ID: ('driver/find_by_id/'),
    FIND_DRIVER_BY_EMAIL: ("driver/email/"),
    FIND_CAR_BY_ID: ('car/vin/'),
    FIND_CAR_BRAND: ('car/brand/'),
    UPDATE_DRIVER_INFO: ('driver/update/'),
    DELETE_DRIVER: ('driver/delete/'),
    ADD_NEW_CAR: ('car/add'),
    CAR_LIST: ('car/carList'),
    CHECKOUT_LIST:('checkout/checkout_list'),
    ADD_NEW_CHECKOUT: ('checkout/add/')
}

const MappingType = {
    POST_MAPPING: ('POST'),
    PUT_MAPPING: ('PUT'),
}

export {
    Path,
    MappingType
}
