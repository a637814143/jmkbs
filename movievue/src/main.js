import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import './styles/auth.css';
import './styles/dashboard.css';

createApp(App).use(router).mount('#app');
