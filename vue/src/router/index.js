import Vue from 'vue'
import Router from 'vue-router'
// in development env not use Lazy Loading,because Lazy Loading too many pages will cause webpack hot update too slow.so only in production use Lazy Loading
/* layout */
import Layout from '../views/layout/Layout'

const _import = require('./_import_' + process.env.NODE_ENV)
Vue.use(Router)
export const constantRouterMap = [
  {path: '/login', component: _import('login/index'), hidden: true},
  {path: '/404', component: _import('404'), hidden: true},
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    name: '首页',
    hidden: true,
    children: [{
      path: 'dashboard',
      component: _import('dashboard/index'),
      name: '首页',
    }]
  }
]
export default new Router({
  // mode: 'history', //后端支持可开
  scrollBehavior: () => ({y: 0}),
  routes: constantRouterMap
})
export const asyncRouterMap = [
  {
    path: '/dashboard',
    component: Layout,
    // redirect: 'dashboard',
    name: '',
    meta: {title: '', icon: 'tree'},
    children: [
      {
        path: '',
        name: '首页',
        component: _import('dashboard/index'),
        meta: {title: '首页', icon: 'example',showNavBar:false},
        menu: 'system'
      },
    ]
  },
  {
    path: '/system',
    component: Layout,
    redirect: '/system/role',
    name: '系统管理',
    meta: {title: '系统管理', icon: 'table'},
    children: [
      {
        path: 'role',
        name: '角色管理',
        component: _import('system/role'),
        meta: {title: '角色管理', icon: 'password',showNavBar:true},
        menu: 'system'
      },
      {
        path: 'rolepermission',
        name: '分配权限',
        component: _import('system/rolepermission'),
        meta: {title: '分配权限', icon: 'password',showNavBar:true},
        menu: 'system',
        hidden: true
      },
      {
        path: 'user',
        name: '用户管理',
        component: _import('system/user'),
        meta: {title: '用户管理', icon: 'user',showNavBar:true},
        menu: 'system'
      },
      {
        path: 'org',
        name: '部门管理',
        component: _import('system/org'),
        meta: {title: '部门管理', icon: 'user',showNavBar:true},
        menu: 'system'
      },
      {
        path: 'region',
        name: '区域管理',
        component: _import('system/region'),
        meta: {title: '区域管理', icon: 'user',showNavBar:true},
        menu: 'system'
      },
      {
        path: 'map',
        name: '地图管理',
        component: _import('system/user'),
        meta: {title: '地图管理', icon: 'user',showNavBar:true},
        menu: 'system'
      },
    ]
  },
  {
    path: '/recognization',
    component: Layout,
    redirect: '/system/account',
    name: '人脸识别',
    meta: {title: '人脸识别', icon: 'table'},
    children: [
      {
        path: 'account',
        name: '人员管理',
        component: _import('recognization/account'),
        meta: {title: '人员管理', icon: 'password',showNavBar:true},
        menu: 'recognization'
      },
      {
        path: 'acs',
        name: '门禁管理',
        component: _import('recognization/acs'),
        meta: {title: '门禁管理', icon: 'user',showNavBar:true},
        menu: 'recognization'
      },
      {
        path: 'auth',
        name: '权限管理',
        component: _import('system/user'),
        meta: {title: '权限管理', icon: 'user',showNavBar:true},
        menu: 'recognization'
      },
      {
        path: 'visitor',
        name: '来访人员管理',
        component: _import('system/user'),
        meta: {title: '来访人员管理', icon: 'user',showNavBar:true},
        menu: 'recognization'
      },
      {
        path: 'vistorhistory',
        name: '访问记录',
        component: _import('system/user'),
        meta: {title: '访问记录', icon: 'user',showNavBar:true},
        menu: 'recognization'
      },
    ]
  },
  {path: '*', redirect: '/404', hidden: true}
]
