import {Header} from "../header/Header";
import {useState} from "react";
import {POST_REQUEST} from "../../client/apirequest/Request";
import {Path} from "../../client/apirequest/Path";

const Login = () => {

    const [login, setLogin] = useState({
        username: '',
        password: ''
    });
    const [loginError, setLoginError] = useState({
        username: '',
        password: ''
    })

    const onChange = event => {
        setLogin({
            ...login,
            [event.target.name]: event.target.value
        })
    }

    const isInputValid = () => {
        if (login.username && login.password) {
            return true
        }
        if (!login.username) {
            setLoginError({
                ...loginError,
                username: 'Username is require'
            })
        }
        if (!login.password) {
            setLoginError({
                ...loginError,
                password: 'Password is require'
            })
        }
        return false
    }

    const onSubmit = event => {
        event.preventDefault()
        const isValid = isInputValid()
        if (!isValid) {
            return
        }
        POST_REQUEST('POST', Path.LOGIN, null, login)
            .then(response => {
                {
                    console.log(response)
                }
            })
    }

    return (
        <div className="login">
            <Header contextRight={'context_active_false'}/>
            <div className="content_container">
                <form action=""
                      className="form"
                      onSubmit={onSubmit}
                >
                    <div className="form-group">
                        <input type="text"
                               name="username"
                               placeholder={loginError.username ? loginError.username : 'Enter user name'}
                               onChange={onChange}
                               className={loginError.username ? 'error light' : ''}
                        />
                    </div>
                    <div className="form-group">
                        <input type="password"
                               name="password"
                               placeholder={loginError.password ? loginError.password : 'Enter password'}
                               onChange={onChange}
                               className={loginError.password ? 'error light' : ''}
                        />
                    </div>
                    <div className="form-group btn-container">
                        <input type="submit" className="submit" value="Submit"/>
                    </div>
                </form>
            </div>
        </div>
    )
}

export {
    Login
}
