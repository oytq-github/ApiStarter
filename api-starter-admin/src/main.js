import Vue from 'vue'

import 'normalize.css/normalize.css' // A modern alternative to CSS resets

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import locale from 'element-ui/lib/locale/lang/zh-CN' // lang i18n

import '@/styles/index.scss' // global css

import App from './App'
import store from './store'
import router from './router'

import '@/icons' // icon
import '@/permission' // permission control

Vue.use(ElementUI, {
  locale
})

Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})

Vue.prototype.$formatDate = (num, fomatter) => {
  const date = new Date(num)
  const y = date.getFullYear()
  const m = ('00' + (date.getMonth() + 1)).slice(-2)
  const d = ('00' + date.getDate()).slice(-2)
  if (fomatter === 'yyyy-MM-dd') {
    return `${y}-${m}-${d}`
  } else if (fomatter === 'yyyy-MM-dd HH:mm:ss' || !fomatter) {
    const h = ('00' + date.getHours()).slice(-2)
    const min = ('00' + date.getMinutes()).slice(-2)
    const s = ('00' + date.getSeconds()).slice(-2)
    return `${y}-${m}-${d} ${h}:${min}:${s}`
  }
}

Vue.prototype.$pickerOptions = {
  shortcuts: [{
    text: '今天',
    onClick(picker) {
      const end = new Date()
      const start = new Date(new Date(new Date().toLocaleDateString()).getTime())
      start.setTime(start.getTime())
      picker.$emit('pick', [start, end])
    }
  }, {
    text: '最近七天',
    onClick(picker) {
      const end = new Date()
      const start = new Date(new Date(new Date().toLocaleDateString()).getTime())
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
      picker.$emit('pick', [start, end])
    }
  }, {
    text: '最近三十天',
    onClick(picker) {
      const end = new Date()
      const start = new Date(new Date(new Date().toLocaleDateString()).getTime())
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
      picker.$emit('pick', [start, end])
    }
  }, {
    text: '最近九十天',
    onClick(picker) {
      const end = new Date()
      const start = new Date(new Date(new Date().toLocaleDateString()).getTime())
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
      picker.$emit('pick', [start, end])
    }
  }]
}

Vue.prototype.$hasButtons = (buttons, button) => {
  let result = false
  if (buttons && buttons.length > 0) {
    buttons.forEach(item => {
      if (button === item) {
        result = true
      }
    })
  }
  return result
}
