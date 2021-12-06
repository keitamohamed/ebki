import {AiOutlineCloudUpload} from "react-icons/all";

const FileUpload = () => {

    return (
        <div className="file-upload-container">
            <div className="content_file_preview"></div>
            <div className="drop-zone-content">
                <div className="drop_zone">
                    <AiOutlineCloudUpload/>
                    <br/>
                    <p>Drag & Drop image file here or click to upload</p>
                    <input type="file" name="file" className="drop_zone_input"
                           multiple={true}
                    />
                </div>
            </div>
        </div>
    )
}

export default FileUpload