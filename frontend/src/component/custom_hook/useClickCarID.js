const UseClickCarID = (event) => {
    const getID = () => {
      return event.target.parentNode.id
    }
    return (getID())
}

export default UseClickCarID
