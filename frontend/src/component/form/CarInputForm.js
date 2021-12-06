import FileUpload from "../reusable/FileUpload";

const CarInputForm = ({carClick, title, type, handleChange, error, message}) => {
    return (
        <>
            <div className="title_container">
                <h5>{title}</h5>
            </div>
            <div className="form-group">
                <input type="text"
                       id="brand"
                       name="brand"
                       className={error.brand ? 'error' : ''}
                       placeholder={error.brand ? error.brand : `Brand (Current ${carClick !== null ? carClick.brand : ''})`}
                       onChange={handleChange}
                />
            </div>
            <div className="form-group">
                <input type="text"
                       id="model"
                       name="model"
                       className={error.model ? 'error' : ''}
                       placeholder={error.model ? error.model : `Model (Current ${carClick !== null ? carClick.model : ''})`}
                       onChange={handleChange}
                />
            </div>
            <div className="form-group">
                <input type="text"
                       id="bodyType"
                       name="bodyType"
                       className={error.bodyType ? 'error' : ''}
                       placeholder={error.bodyType ? error.bodyType : `Body Style (Current ${carClick !== null ? carClick.bodyType : ''})`}
                       onChange={handleChange}
                />
            </div>
            <div className="form-group">
                <input type="number"
                       id="year"
                       name="year"
                       className={error.year ? 'error' : ''}
                       placeholder={error.year ? error.year : `Year (Current ${carClick !== null ? carClick.year : ''})`}
                       onChange={handleChange}
                />
            </div>
            <FileUpload/>
            <div className="form-group">
                <p className={'successful_message'}>{message.msg}</p>
            </div>
            <div className="form-group btn-container">
                <input type="submit" className="submit" value={type}/>
            </div>
        </>
    )
}

export {
    CarInputForm
}
