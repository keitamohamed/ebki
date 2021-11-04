const UsePagination = ({postPerPage, totalPosts, paginate}) => {
    const pageNumbers = []
    for (let i = 1; i <= Math.ceil(totalPosts / postPerPage); i++) {
        pageNumbers.push(i)
    }

    return (
        <div className="pagination_wrapper">
            {
                pageNumbers.map(pageNumber =>(
                    <li
                        className="page-item btn"
                        key={pageNumber}
                        onClick={() => paginate(pageNumber)}
                    >
                        {pageNumber}
                    </li>
                ))
            }
        </div>
    )
}

export {
    UsePagination
}
