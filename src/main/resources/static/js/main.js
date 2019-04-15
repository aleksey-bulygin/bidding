import Vue from 'vue'
import App from 'pages/App.vue';

import '@babel/polyfill'
import 'api/resource';

import store from 'store/store';

import Vuetify from 'vuetify';
import 'vuetify/dist/vuetify.min.css';

import router from 'router/router';

import Vuelidate from 'vuelidate'

Vue.use(Vuetify)
Vue.use(Vuelidate)

new Vue({
    el: '#app',
    store,
    router,
    render: a => a(App)
});