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
    path: '/face',
    component: Layout,
    redirect: '/face/staff-list',
    name: '人脸识别',
    meta: {title: '人脸识别', icon: 'table'},
    children: [
      {
        path: 'staff-list',
        name: 'staff-list',
        component: _import('face/StaffList'),
        meta: {title: '人员管理', icon: 'user',showNavBar:true},
        menu: 'face'
      },
      {
        path: 'edit-staff',
        name: 'edit-staff',
        component: _import('face/EditStaff'),
        meta: {title: '修改人员信息', icon: 'user',showNavBar:true},
        menu: 'face',
        hidden:true
      },
      {
        path: 'visitor-list',
        name: 'visitor-list',
        component: _import('face/VisitorList'),
        meta: {title: '外来访客管理', icon: 'user',showNavBar:true},
        menu: 'face'
      },
      {
        path: 'edit-visitor',
        name: 'edit-visitor',
        component: _import('face/EditVisitor'),
        meta: {title: '修改外来访客信息', icon: 'user',showNavBar:true},
        menu: 'face',
        hidden:true
      },
      {
        path: 'gateway-list',
        name: '门禁管理',
        component: _import('face/GatewayList'),
        meta: {title: '门禁管理', icon: 'password',showNavBar:true},
        menu: 'face'
      },
      {
        path: 'policy-list',
        name: 'policy-list',
        component: _import('face/PolicyList'),
        meta: {title: '权限管理', icon: 'password',showNavBar:true},
        menu: 'face'
      },
      {
        path: 'new-policy',
        name: 'new-policy',
        component: _import('face/NewPolicy'),
        meta: {title: '分配权限', icon: 'password',showNavBar:true},
        menu: 'face',
        hidden:true
      },
      {
        path: 'visitor-apply-list',
        name: 'visitor-apply-list',
        component: _import('face/VisitorApplyList'),
        meta: {title: '访客申请记录', icon: 'user',showNavBar:true},
        menu: 'face'
      },
      {
        path: 'visitor-apply',
        name: 'visitor-apply',
        component: _import('face/VisitorApply'),
        meta: {title: '访客信息录入', icon: 'user',showNavBar:true},
        menu: 'face',
        hidden:true
      },
      {
        path: 'visitor-apply-review',
        name: 'visitor-apply-review',
        component: _import('face/VisitorApplyReview'),
        meta: {title: '访客信息', icon: 'user',showNavBar:true},
        menu: 'face',
        hidden:true
      },
      {
        path: 'vistorhistory',
        name: '访问记录',
        component: _import('system/user'),
        meta: {title: '访问记录', icon: 'password',showNavBar:true},
        menu: 'face'
      },
    ]
  },
  {path: '*', redirect: '/404', hidden: true}
]
