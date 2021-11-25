import {useEffect} from "react";
import ProtHeader from "../header/ProtHeader";
import {usePaginate} from "../paginate/usePaginate";
import {useDriver} from "../custom_hook/useDriver";
import CheckoutTable from "../reusable/CheckoutTable";


const Profile = () => {
    const {driver, address, checkout, loaded} = useDriver(825125692)
    const {} = usePaginate()

    const birthDay = () => {
        return new Date(driver.dob).toLocaleString('en-us', {month: 'long', day: 'numeric', year: 'numeric'})
    }

    useEffect(() => {

    }, [])

    return (
        <div className={"profile"}>
            <ProtHeader/>
            <div className="profile_layout">
                <div className="content_left">
                    <div className="content_img_container">
                        <div className="content_profile">
                            <img src="../../image/profile/profile.jpg" alt="profile"/>
                        </div>
                    </div>
                    <div className="content">
                        <div className="content_info">
                            <h5>ID: <span>{driver.driverID}</span></h5>
                            <h3>Name: <span>{`${driver.firstName} ${driver.lastName}`}</span></h3>
                            <h5>Date of Birth: <span>{birthDay()}</span></h5>
                        </div>
                        <div className="content_address">
                            <div className="title_container">
                                <h5>Address</h5>
                            </div>
                            {
                                loaded === true ? (
                                    address().map((add, index) => {
                                        return (
                                            <div
                                                className="address"
                                                key={index}
                                            >
                                                <p>{add.street}</p>
                                                <p>{`${add.city} ${add.state}, ${add.zipcode}`}</p>
                                            </div>
                                        )
                                    })
                                ) : ''
                            }
                        </div>
                    </div>
                </div>
                <div className="content_right">
                    <div className="content_top"></div>
                    <div className="content_bottom">
                        {
                            console.log(checkout())
                        }
                        {/*<CheckoutTable />*/}
                    </div>
                </div>
            </div>
        </div>
    )
}

export {
    Profile
}
