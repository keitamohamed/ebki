import {useContext} from "react";
import {CarContext, DashboardContext} from "../../context/Context";
import {usePaginate, usePost} from "../paginate/usePaginate";
import {CarPosts} from "../paginate/CarPosts";
import {UsePagination} from "../paginate/usePagination";


const CarPostsDisplay = ({car, numPost}) => {
    const carCtx = useContext(CarContext)
    const dashCtx = useContext(DashboardContext)
    const {post, currentPage, postPerPage, loading, setCurrentPage} = usePaginate(car, numPost)
    const {currentPosts} = usePost(currentPage, postPerPage, car)

    const paginate = (pageNumber) => setCurrentPage(pageNumber)

    return (
        car.length > 0 ? (
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
