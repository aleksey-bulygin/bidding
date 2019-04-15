import Vue from 'vue'
import VueRouter from 'vue-router'

import Auth from 'pages/Auth.vue'
import Profile from 'pages/Profile.vue'
import Bidding from 'pages/Bidding.vue'
import CustomUsers from 'pages/CustomUsers.vue'
import CustomBidding from 'pages/CustomBidding.vue'
import Playing from 'pages/Playing.vue';

Vue.use(VueRouter)

const routes = [
    {path: '/auth', component: Auth },
    {path: '/admin', component: CustomUsers},
    {path: '/admin/setting', component: CustomBidding},
    {path: '/profile', component: Profile},
    {path: '/bidding', component: Bidding},
    {path: '/bidding/choose', component: Playing},
]

export default new VueRouter({
    mode: 'history',
    routes
})