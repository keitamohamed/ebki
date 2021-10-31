import {useEffect, useState} from "react";

import {Path} from "../../client/apirequest/Path";
import {GET_REQUEST} from "../../client/apirequest/Request";

const Dashboard = () => {
    const [car, setCar] = useState({list:[]})

    useEffect(async () => {
        async function fetchData() {
            const {data} = await GET_REQUEST(Path.CAR_LIST, null)
            console.log(data)
            setCar({
                ...car,
                list: data
            })
        }
        fetchData()
        console.log(car.list)
    }, [])

    return (
        <div className="dashboard">
            <div className="dash_context_layout">
                <div className="dash_main">
                    <table className="table">
                        <thead>
                        <tr>
                            <th>Date</th>
                            <th>Brand</th>
                            <th>Model</th>
                            <th>Year</th>
                            <th>Checklist</th>
                            <th>Status</th>
                        </tr>
                        </thead>
                        {
                            car.list.map((car, index) => {
                                return (
                                    <tr key={index}>
                                        <td>{car.data}</td>
                                        <td>{car.brand}</td>
                                        <td>{car.model}</td>
                                        <td>{car.year}</td>
                                        <td>{'Ebik/Daily'}</td>
                                        <td className={'carStatus' === 'pass' ? 'static_bg_green' : 'static_bg_red'}>
                                            Pass
                                        </td>
                                    </tr>
                                )
                            })
                        }
                    </table>
                </div>
                <div className="checklist">
                    If you're visiting this page, you're likely here
                    sentence
                </div>
            </div>
        </div>
    )
}

export {
    Dashboard
}
