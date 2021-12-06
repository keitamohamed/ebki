import {useHistory} from "react-router-dom";

import {Header} from "../header/Header";
import {useFetch} from "../custom_hook/useFetch";
import Slider from "../reusable/Slider";
import {Carousel} from "../reusable/Carousel";
import {useContext, useEffect, useState} from "react";
import {AuthContext} from "../../context/Context";
import {GET_REQUEST} from "../../client/apirequest/Request";
import {isObjectUndefined} from "../util/Util"

const Home = () => {
    const authCtx = useContext(AuthContext)
    const [url] = useState("http://localhost:8080/ebik/car/download-image")
    const {fetchDriver} = useFetch()
    const history = useHistory()
    const [post, setPost] = useState([
        {
            brand: "Chevrolt",
            model: "Camaro SS 1SS",
            year: 2018,
            url: 'chevrolt.png'
        },
        {
            brand: "Hyundai",
            model: "Sonata SEL Plus",
            year: 2020,
            url: 'hyundai_red.png'
        },
        {
            brand: "Audi",
            model: "Q3 Premium Plus 45",
            year: 2021,
            url: 'audi_q33.png'
        }
    ])

    const downloadImage = async () => {
        return await GET_REQUEST(`${url}/115015964`, null, authCtx.cookie.access_token);
    }

    const navigateToCarList = () => {
        history.push("/carlist")
    }

    const {car: {cars}} = useFetch()

    useEffect(() => {
        const loadData = async () => {
            const response = await fetchDriver(authCtx.cookie.username, authCtx.cookie.access_token)
            if (!isObjectUndefined(response)) {
                authCtx.setUserID(response)
            }
        }
        loadData()
    }, [])

    return (
        <div className="home">
            <Header/>
            <Slider/>
            <di className="image_container">
                <img src={`${url}/115015964`}/>
            </di>
            <Carousel post={post}/>
            <div className="main">
                <div className="content_container">
                    <div className="content">
                        <div className="content_info">
                            <h2>We will do the math. You do the driving.</h2>
                            <p>We are good with number so you do not have to be</p>
                            <div className="btn_container">
                                <li onClick={navigateToCarList}>
                                    Search Inventory
                                </li>
                            </div>
                        </div>
                        <div className="image_container">
                            <img src={'/image/car/ford_mustang.png'} alt="target"/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Home
