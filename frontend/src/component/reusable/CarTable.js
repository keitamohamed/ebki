import {useContext, useEffect} from "react";
import {CarContext, DashboardContext} from "../../context/Context";

import {GET_REQUEST} from "../../client/apirequest/Request";
import {Path} from "../../client/apirequest/Path";
import {usePaginate, usePost} from "../paginate/usePaginate";
import CarPost from "../paginate/CarPost";
import {UsePagination} from "../paginate/usePagination";


const CarTable = (cars, reSetData) => {
    const carCtx = useContext(CarContext)
    const dashCtx = useContext(DashboardContext)
    const {post, currentPage, postPerPage, loading, setCurrentPage} = usePaginate(cars.car, 10)
    const {currentPosts} = usePost(currentPage, postPerPage, cars.car)

    useEffect(() => {
        const fetchData = () => {
            GET_REQUEST(Path.FIND_CAR_BY_ID, carCtx.getClickCarVin(), null)
                .then(response => {
                    const {data} = response
                    carCtx.setClickCarValue(data)
                    dashCtx.setAction("SHOW_CAR")
                })
        }
        fetchData()

    }, [carCtx.clickCarVin])

    const paginate = (pageNumber) => setCurrentPage(pageNumber)

    return (
        <div className="table_container">
            {
                cars.car.length > 0 ? (
                    <table className="table mt_remove">
                        <thead>
                        <h5>List of available cars</h5>
                        </thead>
                        <tbody>
                        {
                            <CarPost post={currentPosts} loading={loading}/>
                        }
                        </tbody>
                        {
                            <UsePagination
                                postPerPage={postPerPage}
                                totalPosts={post.length}
                                paginate={paginate}
                            />
                        }
                    </table>
                ) : (
                    ''
                )
            }
        </div>
    )
}

export default CarTable
