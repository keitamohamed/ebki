import Home from "../component/page/Home";
import {Login} from "../component/page/Login";
import {Signup} from "../component/page/Signup";
import {Dashboard} from "../component/page/Dashboard";
import {Car} from "../component/page/Car";
import CarInventory from "../component/page/CarInventory";
import {Profile} from "../component/page/Profile";

const routPath = [
    {
        name: "Home",
        path: "/",
        protected: false,
        component: Home
    },
    {
        name: "Profile",
        path: "/profile",
        protected: true,
        component: Profile
    },
    {
        name: "Dashboard",
        path: "/dashboard",
        protected: true,
        adminProtected: true,
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
        protected: true,
        adminProtected: true,
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

export default routPath
