import Home from "../component/page/Home";
import {Login} from "../component/page/Login";
import {Signup} from "../component/page/Signup";
import {Dashboard} from "../component/page/Dashboard";
import {Car} from "../component/page/Car";
import CarInventory from "../component/page/CarInventory";

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
        name: 'Cars',
        path: '/carlist',
        protected: false,
        component: CarInventory

    },
    {
        name: 'Car',
        path: '/car',
        protected: false,
        component: Car
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
