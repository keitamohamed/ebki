import {Link} from "react-router-dom";
import {routPath} from "../../route/RoutPath";
import {Logo} from "../app_logo/Logo";

const Header = ({contextRight}) => {
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
