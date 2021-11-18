import {useState, useEffect} from "react";
import {getElements} from "../util/Util";

const useCarousel = (className, waitTime) => {
    const [slideIndex, setSlideIndex] = useState(1)
    const [timeout] = useState(waitTime)

    const nextSlides = () => {
        const slides = getElements(className)
        if (slideIndex === slides.length) {
            setSlideIndex(1)
            return
        }
        setSlideIndex((slideIndex + 1))
    }

    const previousSlides = () => {
        const slides = getElements(className)
        if (slideIndex === 1) {
            setSlideIndex((slides.length))
            return
        }
        setSlideIndex((slideIndex - 1))
    }

    const currentSlide = event => {
        setSlideIndex(parseInt(event.target.id))
    }

    const showSlides = () => {

        const slides = getElements(className)
        const dots = getElements("dot")

        for (let i = 0; i < slides.length; i++) {
            slides[i].style.display = "none"
        }
        for (let i = 0; i < dots.length; i++) {
            dots[i].className = dots[i].className.replace(" active", "")
        }
        slides[(slideIndex - 1) >= slides.length ? 0 : (slideIndex - 1)].style.display = "block";
        dots[(slideIndex - 1) >= slides.length ? 0 : (slideIndex - 1)].className += " active";
    }

    const autoShowSlides = () => {
        const slides = getElements(className)
        setSlideIndex(slideIndex + 1)
        if (slideIndex >= slides.length) {
            setSlideIndex(1)
        }
    }

    useEffect(() => {

    }, [slideIndex])


    return {
        className,
        slideIndex,
        timeout,
        previousSlides,
        nextSlides,
        currentSlide,
        autoShowSlides,
        showSlides,
    }

}

export {
    useCarousel
}
