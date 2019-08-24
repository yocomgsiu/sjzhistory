// import Vue from 'vue'
// import ElementUI from 'element-ui'
const Vue = require('vue')
const ElementUI = require('element-ui')
import App from './App.vue'
// import 'element-ui/lib/theme-chalk/index.css'
import './utils/filter_utils.js'
import router from './router'
// import './styles/element-variables.scss'

Vue.use(ElementUI)
Vue.config.productionTip = false
window.bus = new Vue();
new Vue({
  el: '#app',
  router,
  template: '<App/>',
  components: {App}
})
