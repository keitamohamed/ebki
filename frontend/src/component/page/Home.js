import {Header} from "../header/Header";
import {CarCard} from "../reusable/Card";
import {useGetData} from "../custom_hook/useGetData";

const Home = () => {
    const {car:{cars}} = useGetData()

    return (
        <div className="home">
            <Header />
            <div className="main">
                <div className="context_car">
                    {
                        cars.map((car, index) => {
                            return (
                                <CarCard
                                    key={index}
                                    data={car}
                                />
                            )
                        })
                    }
                </div>
            </div>
        </div>
    )
}

export default Home
