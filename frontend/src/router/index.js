import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import BoardList from '../views/BoardList.vue'
import DetailBoard from '../views/DetailBoard.vue'
import LoginAuth from '../views/LoginAuth.vue'
import BoardWrite from '../views/BoardWrite.vue'
import BoardUpdate from '../views/BoardUpdate.vue'

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  },
  {
    path: '/board',
    name: 'BoardList',
    component: BoardList
  },
  {
    path: '/detail/:boardId',
    name: 'DetailBoard',
    component: DetailBoard,
    props: true
  },
  {
    path: '/login',
    name: 'LoginAuth',
    component: LoginAuth
  },
  {
    path: '/write',
    name: 'BoardWrite',
    component: BoardWrite
  },
  {
    path: '/write/:boardId',
    name: 'BoardUpdate',
    component: BoardUpdate,
    props: true
  }
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router;
