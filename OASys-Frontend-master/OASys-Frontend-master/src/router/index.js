import Vue from "vue";
import store from "../store";
import VueRouter from "vue-router";
import Admin from "../layout/Admin";
import Index from "../views/Index";
import Login from "../views/Login";
import SignInSignOut from "../views/SignInSignOut";
import SignRecord from "../views/SignRecord";
import SignSetting from "../views/SignSetting";
import PersonalNetDisk from "../views/PersonalNetDisk";
import PublicNetDisk from "../views/PublicNetDisk";
import AskLeave from "../views/AskLeave";
import CheckLeave from "../views/CheckLeave";
import AddUser from "../views/AddUser";
import User from "../views/User";
import Goods from "../views/Goods";
import Flight from "../views/Flight";
import Flightsearch from "../views/FlightSearch";
import TrainTicket from "../views/TrainTicket";
import TrainTicketOrder from "../views/TrainTicketOrder";
import TrainTicketSearch from "../views/TrainTicketSearch";
import GuestIndex from "../views/GuestIndex";
import Guest from "../layout/Guest";


Vue.use(VueRouter);

const routes = [
	{
        path: "/guest",
        component: Guest,
        children: [
            {
                path: "",
                name: "guest-index",
                component: GuestIndex,
            }
        ]
    },
    {
        path: "/",
        component: Admin,
        children: [
            {
                path: "",
                name: "index",
                component: Index,
            },
            {
                path: "/sign",
                name: "sign",
                component: SignInSignOut,
            },
            {
                path: "/sign-record",
                name: "sign-record",
                component: SignRecord,
            },
            {
                path: "/sign-setting",
                name: "sign-setting",
                component: SignSetting,
            },
            {
                path: "/personal-net-disk",
                name: "personal-net-disk",
                component: PersonalNetDisk,
            },
            {
                path: "/public-net-disk",
                name: "public-net-disk",
                component: PublicNetDisk,
            },
            {
                path: "/ask-leave",
                name: "ask-leave",
                component: AskLeave,
            },
            {
                path: "/check-leave",
                name: "check-leave",
                component: CheckLeave,
            },
            {
                path: "/add-user",
                name: "add-user",
                component: AddUser,
            },
            {
                path: "/user",
                name: "user",
                component: User,
            },
            {
                path: "/goods",
                name: "goods",
                component: Goods,
            },
            {
                path: "/flight",
                name: "flight",
                component: Flight,
            },
            {
                path: "/flightsearch",
                name: "flightsearch",
                component: Flightsearch,
            },
            {
                path: "/trainTicket",
                name: "trainTicket",
                component: TrainTicket,
            },
            {
                path: "/trainTicketOrder",
                name: "trainTicketOrder",
                component: TrainTicketOrder,
            },
            {
                path: "/trainTicketSearch",
                name: "trainTicketSearch",
                component: TrainTicketSearch,
            },

        ]
    },
    {
        name: "login",
        path: "/login",
        component: Login
    },
    {
        path: "*",
        component: GuestIndex
    }
];

const router = new VueRouter({
    mode: "history",
    routes
});

router.beforeEach((to, from, next) => {
    if (to.name.startsWith("guest")){
        next();
    }
    if (store.state.auth || to.name == "login") {
        next();
    } else if (from.name == "login" && to.name == "index") {
        next();
    } else {
        router.push({name: "guest-index"});
    }
});
const originalPush = VueRouter.prototype.push;
VueRouter.prototype.push = function push(location) {
    return originalPush.call(this, location).catch(err => err);
};
export default router;
