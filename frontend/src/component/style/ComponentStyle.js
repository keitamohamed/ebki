import {getElement} from "../util/Util";

const useStyleComponent = (className) => {

    const applyStyle = () => {
        const element = getElement(className);
        const modelContent = element.querySelector(".model_content")

        element.style.zIndex = "10"
        element.style.opacity = "1"

        modelContent.style.opacity = "1"
        modelContent.style.transform = "scale(1)"
        modelContent.style.transition = "opacity 250ms 500ms ease, transform 350ms 500ms ease"
    }

    const removeStyle = () => {
        const element = getElement(className);
        const modelContent = element.querySelector(".model_content")

        modelContent.style.opacity = "0"
        modelContent.style.transform = "scale(0.2)"
        modelContent.style.transition = "opacity 250ms 250ms ease, transform 300ms 250ms ease"

        element.style.zIndex = "-2"
        element.style.opacity = "0"
    }

    return {applyStyle, removeStyle}
}

export {
    useStyleComponent
}
