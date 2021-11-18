import {useFetch} from "../custom_hook/useFetch";
import {useContext, useEffect} from "react";
import {CarContext, DashboardContext} from "../../context/Context";
import {usePaginate, usePost} from "../paginate/usePaginate";
import {CarPosts} from "../paginate/CarPosts";
import {UsePagination} from "../paginate/usePagination";


const CarPostsDisplay = (cars) => {
    const carCtx = useContext(CarContext)
    const dashCtx = useContext(DashboardContext)
    const {post, currentPage, postPerPage, loading, setCurrentPage} = usePaginate(cars.car, 12)
    const {currentPosts} = usePost(currentPage, postPerPage, cars.car)

    const paginate = (pageNumber) => setCurrentPage(pageNumber)

    return (
        cars.car.length > 0 ? (
            <div className="content_paginate">
                <div className="paginate_data_wrapper">
                    <CarPosts post={currentPosts} loading={loading}/>
                </div>
                <UsePagination
                    postPerPage={postPerPage}
                    totalPosts={post.length}
                    paginate={paginate}/>
            </div>
        ) : (<div className={"no_data_found"}>
            <h5>No Data found</h5>
        </div>)
    )

}


export default CarPostsDisplay
