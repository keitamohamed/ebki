import {getElement, getElements} from "../util/Util";
import {useState} from "react";
import {POST_REQUEST_FILE} from "../../client/apirequest/Request";
import {Path} from "../../client/apirequest/Path";

const useFile = () => {
    const drag = ["dragleave", "dragend"]
    const [files, setFiles] = useState({file: []})
    const [fileExist] = useState({url: []})

    const saveFile = (inputElement) => {
        if (inputElement.files.length) {
            for (let i = 0; i < inputElement.files.length; i++) {
                const newFile = inputElement.files[i]
                const {file} = files
                file.push(newFile)
            }
            previewFile()
        }
    }

    const previewFile = () => {
        const imageContainer = getElement("content_file_preview");
        if (files.file.length > 0) {
            getElement("content_file_preview").style.visibility = "visible"
        }

        files.file.map((file) => {
            const fileReader = new FileReader()
            fileReader.readAsDataURL(file)
            const div = document.createElement("div")
            div.classList.add("content_image");
            const img = document.createElement("img")
            img.classList.add("imageFile")
            const {url} = fileExist
            let isFileExist = false;
            fileReader.onload = () => {
                url.map(img => {
                    if (fileReader.result === img) {
                        isFileExist = true
                    }
                })
                if (!isFileExist) {
                    url.push(fileReader.result)
                    img.src = `${fileReader.result}`
                    div.append(img)
                    imageContainer.append(div)
                }
            }
        })
    }

    const onClickGetSelectedFiles = () => {

    }

    const dropZone = () => {
        const elements = getElements("drop_zone_input")
        elements.forEach(inputElement => {
            const dropZoneElement = inputElement.closest('.drop_zone');
            const pElement = getElement("drop_zone p")

            dropZoneElement.addEventListener('click', (event) => {
                inputElement.click()
            })

            dropZoneElement.addEventListener('change', (event) => {
                saveFile(inputElement)
            })

            dropZoneElement.addEventListener("dragover", (event) => {
                console.log('Dropping drop zone')
                event.preventDefault()
                dropZoneElement.classList.add("drop_zone__over")
                pElement.innerHTML = `Release to Upload File's`
            })

            drag.forEach((type) => {
                dropZoneElement.addEventListener(type, e => {
                    dropZoneElement.classList.remove('drop_zone__over')
                    if (type === 'dragleave') {
                        pElement.innerHTML = `Drag & drop file here or click to upload`
                    }
                })
            })

            dropZoneElement.addEventListener('drop', event => {
                event.preventDefault()
                dropZoneElement.classList.remove('drop_zone__over')
                pElement.innerHTML = `Drag & drop file here or click to upload`
                saveFile(event.dataTransfer)
            })
        })
    }

    const uploadFile = (token, id, isCarImage, doUpdateFiles) => {
        if (id === undefined) {
            getElement(".file_send_error").style.display = 'block'
            return
        }
        if (files.file.length === 0) {
            return;
        }
        files.file.map(async file => {
            const formData = new FormData()
            formData.append('file', file)
            if (isCarImage && doUpdateFiles === false) {
                await POST_REQUEST_FILE('POST', `${Path.CAR_SAVE_IMAGE}${id}`, token, formData)
                    .then(res => console.log(res))
                    .catch(err => console.log(err))
                setFiles({file: []})
            } else if (!isCarImage && doUpdateFiles === false) {
                console.log("Add driver image")
            } else if (isCarImage && doUpdateFiles === true) {
                if (files.file.length > 0) {
                    await POST_REQUEST_FILE('POST', `${Path.CAR_SAVE_IMAGE}${id}`, token, formData)
                }
            }

        })
        files.file.forEach((value, index) => {
            files.file.slice(index, index)
        })
    }

    return {dropZone, uploadFile}
}

export default useFile