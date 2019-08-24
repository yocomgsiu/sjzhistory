
const Vue = require('vue')
const Router = require('vue-router')
const Login = () => import('@/components/Login')
const RecordArticle = () => import('@/components/RecordArticle')
import TimelineHistory from '@/components/TimelineHistory'

Vue.use(Router)

export default new Router({
  routes: [{
      path: '/',
      name: '',
      component: TimelineHistory,
      hidden: true
    }, {
      path: '/timelineHistory',
      name: '',
      component: TimelineHistory,
      hidden: false
    }, {
      path: '/login',
      name: '',
      component: Login,
      hidden: true
    }, {
      path: '/recordArticle',
      name: '撰写文章',
      component: RecordArticle,
      hidden: true
    },
    {
      path: '/timelineHistory',
      name: '时间轴',
      component: TimelineHistory,
      meta: {
        keepAlive: true
      }
    }

  ]
})
