import {useContext, useEffect, useState} from "react";
import {useHistory} from "react-router-dom"

import ProtHeader from "../header/ProtHeader";
import {usePaginate} from "../paginate/usePaginate";
import {useDriver} from "../custom_hook/useDriver";
import {AuthContext} from "../../context/Context";
import {DisplayCheckTable} from "../reusable/CheckTable";
import {CarouselImage} from "../reusable/ImageWithText";
import {DriverModel} from "../model/DriverModel";
import {AddressModel} from "../model/AddressModel";
import {ProfileImageModel} from "../model/ProfileImageModel";
import PasswordModel from "../model/PasswordModel";
import {useStyleComponent} from "../style/ComponentStyle";
import {AiFillCamera, AiFillDelete, AiFillEdit} from "react-icons/ai";
import {DELETE_REQUEST_MAPPING, GET_REQUEST} from "../../client/apirequest/Request";
import {imageUrl, Path} from "../../client/apirequest/Path"
import {isObjectEmptyKeys} from "../util/Util";
import useFile from "../custom_hook/useFile";

const Profile = () => {
    const history = useHistory()
    const authCtx = useContext(AuthContext)
    const {driver, address, checkout, recentCheckout, loaded} = useDriver(authCtx.cookie.id)
    const [clickAddressID, setClickAddressID] = useState({})
    const [clickCheckout, setClickCheckout] = useState({})
    const {} = usePaginate()
    const [type, setType] = useState({})
    const {applyStyle} = useStyleComponent()
    const {dropZone, uploadFile} = useFile()
    const [message, setMessage] = useState({})

    const birthDay = () => {
        return new Date(driver.dob).toLocaleString('en-us', {month: 'long', day: 'numeric', year: 'numeric'})
    }

    const showDriverModel = () => {
        applyStyle('driver_model')
    }

    const showAddressModel = async (id, type) => {
        setClickAddressID(id)
        setType(type)
        applyStyle('address_model')
    }

    const showPasswordModel = () => {
        applyStyle('password_model')
    }

    const onClickSetProfileImage = () => {
        applyStyle('image_model')
    }

    const deleteAddress = async value => {
        await DELETE_REQUEST_MAPPING(`${Path.DELETE_ADDRESS_BY_ID}/${value}`, authCtx.cookie.access_token)
            .then(response => console.log(response.headers))
        history.push('/profile')
    }

    const setClickCar = event => {
        GET_REQUEST(Path.FIND_CHECKOUT_BY_ID, event.target.id, authCtx.cookie.access_token)
            .then(response => setClickCheckout(response.data))
    }

    const checkInCar = async event => {
        await DELETE_REQUEST_MAPPING(`${Path.CHECK_IN_CAR}/${event.target.id}`, authCtx.cookie.access_token)
            .then(response => setMessage(response.headers.message))
    }

    useEffect(() => {

    }, [clickAddressID])

    return (
        <div className={"profile"}>
            <ProtHeader driver={driver}/>
            <div className="profile_layout">
                <DriverModel data={driver}/>
                <AddressModel
                    id={clickAddressID}
                    type={type}
                    driverID={driver.driverID}
                    token={authCtx.cookie.access_token}
                />
                <PasswordModel
                    token={authCtx.cookie.access_token}
                    driverID={driver.driverID}/>
                <ProfileImageModel/>
                <div className="content_left">
                    <div className="content_img_container">
                        <div className="content_profile">
                            <img src="../../image/profile/profile.jpg" alt="profile"/>
                            <AiFillCamera onClick={onClickSetProfileImage}/>
                        </div>
                        <div className="user_name_container">
                            <h2>{`${driver.firstName} ${driver.lastName}`}
                                <br/>
                                <p>{`ID: ${driver.driverID}`}</p>
                            </h2>
                        </div>
                        <div className="btn_container">
                            <li id={"EDIT"} onClick={showDriverModel}>Update Info</li>
                            <li id={'EDIT_AUTH'} onClick={showPasswordModel}>Change Password</li>
                            <li id={"NEW"} onClick={() => showAddressModel('Submit')}>New Address</li>
                        </div>
                    </div>
                    <div className="content">
                        <div className="content_info">
                            <h5>Phone: <span>{driver.phoneNum}</span></h5>
                            <h5>Email: <span>{driver.email}</span></h5>
                            <h5>Gender: <span>{driver.gender}</span></h5>
                            <h5>Date of Birth: <span>{birthDay()}</span></h5>
                        </div>
                        <div className="content_address">
                            {
                                loaded === true ? (
                                    address().map((add, index) => {
                                        return (
                                            <div className="content"
                                                 key={index}>
                                                <div
                                                    className="address"
                                                >
                                                    <p>{add.street}</p>
                                                    <p>{`${add.city} ${add.state}, ${add.zipcode}`}</p>
                                                </div>
                                                <div className="btn_content">
                                                    <AiFillEdit
                                                        onClick={() => showAddressModel(add.addressID, 'Update')}/>
                                                    <AiFillDelete onClick={() => deleteAddress(add.addressID)}/>
                                                </div>
                                            </div>
                                        )
                                    })
                                ) : ''
                            }
                        </div>
                    </div>
                </div>
                <div className="content_right">
                    <div className="title_container">
                        <h3>Recent Checkout</h3>
                    </div>
                    <div className="content_top">
                        <div className="content_recent_checkout">
                            <div className="car_image_container">
                                <img src={imageUrl(recentCheckout.vin, true)} alt="car"/>
                            </div>
                        </div>
                        <div className="recent_content">
                            <div className="content">
                                <div className="content_car">
                                    <h4>Vin: <span>{recentCheckout.vin}</span></h4>
                                    <h5>Year: <span>{recentCheckout.year}</span></h5>
                                    <h5>Brand: <span>{recentCheckout.brand}</span></h5>
                                    <h5>Model: <span>{recentCheckout.model}</span></h5>
                                    <h5>Body Type: <span>{recentCheckout.bodyType}</span></h5>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className="content_bottom">
                        <DisplayCheckTable
                            setClickCar={setClickCar}
                            checkInCar={checkInCar}
                            checkout={checkout()}
                            title={'Checkout History'}/>
                        {
                            !isObjectEmptyKeys(clickCheckout) ?
                                <CarouselImage carClick={clickCheckout}/> : ''
                        }
                    </div>
                    <div className="container">
                        <div className="content">
                            <p>{!isObjectEmptyKeys(message) ? message : ''}</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export {
    Profile
}
