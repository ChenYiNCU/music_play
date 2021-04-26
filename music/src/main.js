import Vue from 'vue'
import App from './App.vue'
import router from './router'
import axios from 'axios'
import './assets/css/globalcss.css'


Vue.config.productionTip = false
Vue.prototype.$http=axios

new Vue({
  router:router,
  render: h => h(App),
}).$mount('#app')
