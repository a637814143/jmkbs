import { createRouter, createWebHistory } from 'vue-router';
import LoginView from '../views/LoginView.vue';
import RegisterView from '../views/RegisterView.vue';
import UserDashboard from '../views/UserDashboard.vue';
import AdvertiserDashboard from '../views/AdvertiserDashboard.vue';
import AdminDashboard from '../views/AdminDashboard.vue';

const routes = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/login',
    name: 'login',
    component: LoginView
  },
  {
    path: '/register',
    name: 'register',
    component: RegisterView
  },
  {
    path: '/dashboard/user',
    name: 'dashboard-user',
    component: UserDashboard
  },
  {
    path: '/dashboard/advertiser',
    name: 'dashboard-advertiser',
    component: AdvertiserDashboard
  },
  {
    path: '/dashboard/admin',
    name: 'dashboard-admin',
    component: AdminDashboard
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;
