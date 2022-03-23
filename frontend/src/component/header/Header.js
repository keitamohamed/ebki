import {useContext} from "react";

import {Link, useHistory} from "react-router-dom";
import routPath from "../../route/RoutPath";
import {Logo} from "../app_logo/Logo";
import {AuthContext} from "../../context/Context";


const Header = ({contextRight}) => {
    const history = useHistory()
    const authCtx = useContext(AuthContext)

    const navigateTo = event => {
        history.push(event.target.id)
    }

    return (
        <header className="header">
            <div className="nave">
                <div className="hero_logo">
                    <strong>
                        <Link to="/">
                            <Logo/>
                        </Link>
                    </strong>
                </div>
                <div className={contextRight ? contextRight : 'context_links'}>
                    {
                        routPath.map((link, index) => {
                            if (link.protected === false && !authCtx.cookie.access_token && link.name !== 'Home') {
                                return <Link className={"add-padding-top"} key={index} to={link.path}>{link.name}</Link>
                            } else {
                                if (link.name === 'Cars' && authCtx.cookie.access_token) {
                                    return <Link className={"add-padding-top"} key={index}
                                                 to={link.path}>{link.name}</Link>
                                }
                            }
                        })
                    }
                    {
                        authCtx.cookie.access_token ? (
                            <div className={'context_links'}>
                                <li className={'dropdown_nav'}>
                                    <a className="user_name">
                                        {authCtx.cookie.name} &#9662;
                                    </a>
                                    <ul className="dropdown">
                                        {
                                            routPath.map((link, index) => {
                                                if (link.protected === false) {
                                                    return
                                                }
                                                if (link.adminProtected === true) {
                                                    return <li key={index} id={link.path}
                                                               onClick={navigateTo}>{link.name}</li>
                                                }
                                                if (link.protected) {
                                                    return <li key={index} id={link.path}
                                                               onClick={navigateTo}>{link.name}</li>;
                                                }
                                            })
                                        }
                                        <li onClick={authCtx.logout}>Logout</li>
                                    </ul>
                                </li>
                            </div>
                        ) : ''
                    }
                </div>
            </div>
        </header>
    )
}

export {
    Header
}
