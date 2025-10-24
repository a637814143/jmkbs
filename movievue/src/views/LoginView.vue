<template>
  <div class="auth-wrapper">
    <section class="auth-banner">
      <div class="auth-banner-content">
        <h1>极目观影中心</h1>
        <p>登陆后即可根据角色进入不同的运营控制台，管理影片、广告投放或平台审核。</p>
        <p>还没有账号？立即注册成为观众或广告主，体验沉浸式的观影社区。</p>
      </div>
    </section>
    <section class="auth-form-panel">
      <div class="auth-switcher">
        <h2>账号登录</h2>
        <router-link to="/register">前往注册</router-link>
      </div>
      <form class="auth-form" @submit.prevent="handleSubmit">
        <div v-if="errorMessage" class="auth-error">{{ errorMessage }}</div>
        <div v-if="successMessage" class="auth-success">{{ successMessage }}</div>
        <div class="auth-field">
          <label for="username">用户名</label>
          <input
            id="username"
            v-model.trim="form.username"
            name="username"
            type="text"
            placeholder="请输入注册时设置的用户名"
            autocomplete="username"
            required
          />
        </div>
        <div class="auth-field">
          <label for="password">密码</label>
          <input
            id="password"
            v-model.trim="form.password"
            name="password"
            type="password"
            placeholder="请输入密码"
            autocomplete="current-password"
            required
          />
        </div>
        <button class="auth-submit" type="submit" :disabled="isSubmitting">
          {{ isSubmitting ? '登录中...' : '立即登录' }}
        </button>
      </form>
      <p class="auth-helper">忘记密码请联系管理员重置。</p>
    </section>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue';
import { useRouter } from 'vue-router';
import { loginUser } from '../services/api';

const router = useRouter();
const form = reactive({
  username: '',
  password: ''
});
const isSubmitting = ref(false);
const errorMessage = ref('');
const successMessage = ref('');

const roleRouteMap = {
  USER: '/dashboard/user',
  ADVERTISER: '/dashboard/advertiser',
  ADMIN: '/dashboard/admin'
};

async function handleSubmit() {
  if (isSubmitting.value) {
    return;
  }
  errorMessage.value = '';
  successMessage.value = '';
  isSubmitting.value = true;
  try {
    const payload = {
      username: form.username,
      password: form.password
    };
    const user = await loginUser(payload);
    successMessage.value = '登录成功，正在跳转...';
    localStorage.setItem('jmk_movie_user', JSON.stringify(user));
    const targetRoute = roleRouteMap[user.role] || '/dashboard/user';
    setTimeout(() => {
      router.push(targetRoute);
    }, 600);
  } catch (error) {
    errorMessage.value = error.message || '登录失败，请稍后再试';
  } finally {
    isSubmitting.value = false;
  }
}
</script>
