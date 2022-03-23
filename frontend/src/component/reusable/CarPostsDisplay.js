import {usePaginate, usePost} from "../paginate/usePaginate";
import {CarPosts} from "../paginate/CarPosts";
import {UsePagination} from "../paginate/usePagination";
import Loading from "../reusable/Loading"

const CarPostsDisplay = ({car, numPost}) => {
    const {post, currentPage, postPerPage, loading, setCurrentPage} = usePaginate(car, numPost)
    const {currentPosts} = usePost(currentPage, postPerPage, car)

    const paginate = (pageNumber) => setCurrentPage(pageNumber)

    return (
        car.length > 0 && loading === false ? (
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
            <Loading loading={loading} color={'#36D7B7'}/>
        </div>)
    )

}

export default CarPostsDisplay
