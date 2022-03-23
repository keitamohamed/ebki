import {useContext} from "react";
import {Link, useHistory} from "react-router-dom";
import {Logo} from "../app_logo/Logo";
import {AuthContext} from "../../context/Context";
import routPath from "../../route/RoutPath";

const Header = ({driver}) => {
    const history = useHistory();
    const authCtx = useContext(AuthContext)

    const toCheckout = path => {
        history.push(path)
    }
    return (
        <header className="header pro">
            <div className="nave">
                <div className="hero_logo">
                    <strong>
                        <Link to="/">
                            <Logo color={"#FFF"}/>
                        </Link>
                    </strong>
                </div>
                <div className={'context_links'}>
                    <li className={'dropdown_nav'}>
                        <a className="user_name">
                            {driver.firstName} &#9662;
                        </a>
                        <ul className="dropdown">
                            {
                                routPath.map((link, index) => {
                                    return link.protected && !link.adminProtected ?
                                        <li key={index} onClick={() => toCheckout(link.path)}>{link.name}</li> : ''
                                })
                            }
                            {
                                routPath.map((link, index) => {
                                    return link.adminProtected && authCtx.cookie.role === 'ROLE_ADMIN' ?
                                        <li key={index} onClick={() => toCheckout(link.path)}>{link.name}</li> : ''
                                })
                            }
                            <li onClick={authCtx.logout}>Logout</li>
                        </ul>
                    </li>
                </div>
            </div>
        </header>
    )
}

export default Header
