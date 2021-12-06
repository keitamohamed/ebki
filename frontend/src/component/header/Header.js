import {Link} from "react-router-dom";
import routPath from "../../route/RoutPath";
import {Logo} from "../app_logo/Logo";
import {AuthContext} from "../../context/Context";
import {useContext} from "react";

const Header = ({contextRight}) => {
    const authCtx = useContext(AuthContext)

    const logout = () => {
        
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
                            if ((link.name === 'Login' || link.name === 'Signup') && authCtx.cookie.access_token) {
                                return
                            }
                            if (link.protected && !authCtx.cookie.access_token) {
                                return
                            }
                            if (link.name === 'Home') {
                                return
                            }
                            return <Link key={index} to={link.path}>{link.name}</Link>
                        })
                    }
                </div>
            </div>
        </header>
    )
}

export {
    Header
}
