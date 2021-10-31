const reFormatDate = (vale) => {
    const split = vale.split('-')
    return (`${split[2]}-${split[1]}-${split[0]}`)
}

export {
    reFormatDate
}
