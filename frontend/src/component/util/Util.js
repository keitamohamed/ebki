const getElement = className => {
    const element = document.querySelector(`.${className}`)
    return element ? element : null;
}

const getElements = className => {
    const elements = document.querySelectorAll(`.${className}`);
    return elements ? elements : null
}

const reFormatDate = (vale) => {
    const split = vale.split('-')
    return (`${split[2]}-${split[1]}-${split[0]}`)
}

const toggleButtonID = (event) => {
    let {id} = event.target
    if (!id) {
        id = event.target.parentNode.id
    }
    if (!id) {
        id = event.target.parentNode.parentNode.id
    }
    if (!id) {
        id = event.target.parentNode.parentNode.parentNode.id
    }
    return id
}

const getID = event => {
    if (event.target.id) {
        return event.target.id
    }
    if (event.target.parentNode.id) {
        return event.target.parentNode.id
    }
    return event.target.parentNode.parentElement.id
}

const isEmailValid = (email) => {
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)
}

const isObjectEmpty = (object) => {
    return typeof object === undefined || object === null || Object.keys(object).length === 0
}

const isObjectEmptyKeys = (object) => {
    if (object === undefined || null) {
        return true
    }
    return Object.keys(object).length === 0;
}

const isObjectUndefined = object => {
    return object === undefined || object === 'undefined'
}

const isArrayEmpty = (array) => {
    return array.length > 0;
}

const isNumeric = (num) => {
    return !isNaN(num)
}

export {
    getElement,
    getElements,
    reFormatDate,
    getID,
    isEmailValid,
    isObjectEmpty,
    isObjectEmptyKeys,
    isArrayEmpty,
    isObjectUndefined,
    toggleButtonID,
    isNumeric
}
