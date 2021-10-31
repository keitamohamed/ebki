import Home from "../component/page/Home";
import {Login} from "../component/page/Login";
import {Signup} from "../component/page/Signup";
import {Dashboard} from "../component/page/Dashboard";

const routPath = [
    {
        name: "Home",
        path: "/",
        protected: false,
        component: Home
    },
    {
        name: "Dashboard",
        path: "/dashboard",
        protected: false,
        component: Dashboard
    },
    {
        name: "Login",
        path: "/login",
        protected: false,
        component: Login
    },
    {
        name: "Signup",
        path: "/signup",
        protected: false,
        component: Signup
    }
]

export {
    routPath
}
