import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

import Layout from '@/layout'

export const constantRoutes = [{
  path: '/login',
  component: () => import('@/views/login/index'),
  hidden: true
},
{
  path: '/404',
  component: () => import('@/views/404'),
  hidden: true
},
{
  path: '/',
  component: Layout,
  redirect: '/dashboard',
  children: [{
    path: 'dashboard',
    name: 'Dashboard',
    component: () => import('@/views/dashboard/index'),
    meta: {
      title: '首页',
      icon: 'dashboard'
    }
  }]
}
]

export const asyncRoutes = [{
  path: '/system',
  component: Layout,
  alwaysShow: true,
  meta: {
    icon: 'example',
    title: '系统管理',
    roles: ['SYSTEM_MANAGER']
  },
  children: [{
    path: 'resource',
    name: 'Resource',
    component: () => import('@/views/system/resources/index'),
    meta: {
      title: '资源管理',
      roles: ['RESOURCE_MENU']
    }
  }, {
    path: 'role',
    name: 'Role',
    component: () => import('@/views/system/roles/index'),
    meta: {
      title: '角色管理',
      roles: ['ROLE_MENU']
    }
  }, {
    path: 'users',
    name: 'Users',
    component: () => import('@/views/system/users/index'),
    meta: {
      title: '用户管理',
      roles: ['USER_MENU']
    }
  }]
},

{
  path: '*',
  redirect: '/404',
  hidden: true
}
]

const createRouter = () => new Router({
  scrollBehavior: () => ({
    y: 0
  }),
  routes: constantRoutes
})

const router = createRouter()
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher
}

export default router
