import slideOne from '../../image/slider/slide_1.jpg'
import slideTwo from "../../image/slider/slide_2.jpg"
import slideThree from "../../image/slider/slide_3.jpg"
import {useCarousel} from "../custom_hook/useCarousel";
import {useEffect} from "react";

const Slider = () => {
    const {
        slideIndex,
        showSlides,
        autoShowSlides,
        previousSlides,
        nextSlides,
        currentSlide} =
        useCarousel("slide", 5000)

    useEffect(() => {
        showSlides()
        const timer = setTimeout(() => {
            autoShowSlides()
        }, 5000)
        return () => clearTimeout(timer)
    }, [slideIndex])

    return (
        <div className="slider">
            <div className="slideshow_container">
                <div className="slide_container">
                    <div className="slide fade">
                        <img src={slideOne} alt="slide 1"/>
                    </div>

                    <div className="slide fade">
                        <img src={slideTwo} alt="slide 2"/>
                    </div>

                    <div className="slide fade">
                        <img src={slideThree} alt="slide 3"/>
                    </div>

                    <a className="prev" onClick={previousSlides}>&#10094;</a>
                    <a className="next" onClick={nextSlides}>&#10095;</a>
                </div>
            </div>

            <div className="dot_container">
                <span
                    id={1}
                    className="dot"
                    onClick={currentSlide}/>
                <span
                    id={2}
                    className="dot"
                    onClick={currentSlide}/>
                <span
                    id={3}
                    className="dot"
                    onClick={currentSlide}/>
            </div>
        </div>
    )
}

export default Slider
