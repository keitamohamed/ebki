const CheckoutPost = (post, loading) => {
    return loading === true ? <h1>Loading...</h1> : (
        post.post.map((car) => {
            return (
                <tr
                    key={car.vin}
                    id={car.vin}
                >
                    <td>{car.checkout.checkoutID}</td>
                    <td>{car.year}</td>
                    <td>{car.brand}</td>
                    <td>{car.model}</td>
                    <td className={'carStatus' === 'pass' ? 'static_bg_green' : 'static_bg_red'}>
                        Pass
                    </td>
                </tr>
            )
        })
    )
}

export default CheckoutPost
