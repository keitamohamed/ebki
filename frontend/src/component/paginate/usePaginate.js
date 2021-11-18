import {useEffect, useState} from "react";

const usePaginate = (post, numPostPerPage) => {

    const [posts, setPosts] = useState({})
    const [currentPage, setCurrentPage] = useState(1)
    const [postPerPage] = useState(numPostPerPage)
    const [loading, setLoading] = useState(true)

    useEffect(() => {
        setLoading(false)
    }, [posts, numPostPerPage])
    return {post, currentPage, postPerPage, loading, setCurrentPage}
}

const usePost = (currentPage, postPerPage, post) => {
    const indexOfLastPost = currentPage * postPerPage;
    const indexOfFirstPost = indexOfLastPost - postPerPage
    const currentPosts = post.slice(indexOfFirstPost, indexOfLastPost)

    return {currentPosts}
}

export {
    usePaginate,
    usePost
}
