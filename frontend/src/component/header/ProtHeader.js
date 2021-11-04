import {Link} from "react-router-dom";
import {Logo} from "../app_logo/Logo";
import {routPath} from "../../route/RoutPath";

const Header = () => {
    return (
        <header className="header pro">
            <div className="nave">
                <div className="hero_logo">
                    <strong>
                        <Link to="/">
                            <Logo/>
                        </Link>
                    </strong>
                </div>
                <div className={'context_links'}>
                    <li className={'dropdown_nav'}>
                        <a href="">Robert White &#9662;</a>
                        <ul className="dropdown">
                            <li>Account</li>
                            <li>Checkout</li>
                            <li>Checkin</li>
                            <li>Logout</li>
                        </ul>
                    </li>
                    {/*{*/}
                    {/*    routPath.map((link, index) => {*/}
                    {/*        return <Link key={index} to={link.path}>{link.name}</Link>*/}
                    {/*    })*/}
                    {/*}*/}
                </div>
            </div>
        </header>
    )
}

export default Header
