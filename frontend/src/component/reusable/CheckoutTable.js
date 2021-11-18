import {usePaginate, usePost} from "../paginate/usePaginate";
import {UsePagination} from "../paginate/usePagination";
import CheckoutPost from "../paginate/CheckoutPost";

const CheckoutTable = ({checkoutCar: {cars}}) => {
    const {post, currentPage, postPerPage, loading,setCurrentPage} = usePaginate(cars, 10)
    const {currentPosts} = usePost(currentPage, postPerPage, cars)
    const paginate = (pageNumber) => setCurrentPage(pageNumber)

    return (
        <div className="table_container">
            {
                cars.length > 0 ? (
                    <table className="table mt_remove">
                        <thead>
                        <h5>List of cars checkout</h5>
                        </thead>
                        <tbody>
                        <CheckoutPost post={currentPosts} loading={loading}  />
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

export default CheckoutTable
