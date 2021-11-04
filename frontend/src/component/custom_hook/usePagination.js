import {useEffect, useState} from "react";

const usePagination = (car) => {
    const [paginationValue, setPaginationValue] = useState({
        page: 1,
        row: 7,
        querySet: car
    })

    const setPaginationData = query => {
      setPaginationData({
          ...paginationValue,
          querySet: query
      })
    }

    const pagination = () => {
      const start = (paginationValue.page - 1) * paginationValue.row;
      const end = start + paginationValue.row;
      const data = paginationValue.querySet.slice(start, end);
      const pages = Math.ceil(paginationValue.querySet.length / paginationValue.row)

        return {querySet: data, pages}
    }

    const setPagination = () => {
        setPaginationValue({
            ...paginationValue,
            querySet: car,
        })
    }

    useEffect(() => {
        setPagination()
    }, [])

    return {pagination}
}

export {
    usePagination
}
