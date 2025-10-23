<template>
  <div class="auth-wrapper">
    <section class="auth-banner">
      <div class="auth-banner-content">
        <h1>开启你的极目观影账号</h1>
        <p>完善注册信息后即可登录系统，根据不同角色体验专属的功能模块。</p>
        <p>平台支持观众、广告主及管理员登录，注册后可随时切换角色登陆。</p>
      </div>
    </section>
    <section class="auth-form-panel">
      <div class="auth-switcher">
        <h2>注册新账号</h2>
        <router-link to="/login">已有账号？去登录</router-link>
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
            placeholder="请输入字母、数字或下划线"
            minlength="3"
            maxlength="50"
            required
          />
        </div>
        <div class="auth-field">
          <label for="nickname">显示昵称</label>
          <input
            id="nickname"
            v-model.trim="form.nickname"
            name="nickname"
            type="text"
            placeholder="在平台展示的名称"
            maxlength="100"
            required
          />
        </div>
        <div class="auth-field">
          <label for="password">设置密码</label>
          <input
            id="password"
            v-model.trim="form.password"
            name="password"
            type="password"
            placeholder="至少 6 位字符"
            minlength="6"
            maxlength="64"
            required
          />
        </div>
        <div class="auth-field">
          <label for="confirmPassword">确认密码</label>
          <input
            id="confirmPassword"
            v-model.trim="form.confirmPassword"
            name="confirmPassword"
            type="password"
            placeholder="再次输入密码"
            minlength="6"
            maxlength="64"
            required
          />
        </div>
        <div class="auth-field">
          <label for="avatarUrl">头像地址（可选）</label>
          <input
            id="avatarUrl"
            v-model.trim="form.avatarUrl"
            name="avatarUrl"
            type="url"
            placeholder="https://example.com/avatar.png"
          />
        </div>
        <div class="auth-field">
          <label for="role">角色选择</label>
          <select id="role" v-model="form.role" name="role">
            <option value="USER">普通用户</option>
            <option value="ADVERTISER">广告主</option>
            <option value="ADMIN">管理员</option>
          </select>
        </div>
        <button class="auth-submit" type="submit" :disabled="isSubmitting">
          {{ isSubmitting ? '提交中...' : '创建账号' }}
        </button>
      </form>
      <p class="auth-helper">提交后即表示同意《极目观影用户协议》及《隐私政策》。</p>
    </section>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue';
import { useRouter } from 'vue-router';
import { registerUser } from '../services/api';

const router = useRouter();
const form = reactive({
  username: '',
  nickname: '',
  password: '',
  confirmPassword: '',
  avatarUrl: '',
  role: 'USER'
});

const isSubmitting = ref(false);
const errorMessage = ref('');
const successMessage = ref('');

async function handleSubmit() {
  if (isSubmitting.value) {
    return;
  }
  errorMessage.value = '';
  successMessage.value = '';
  if (form.password !== form.confirmPassword) {
    errorMessage.value = '两次输入的密码不一致';
    return;
  }
  isSubmitting.value = true;
  try {
    const payload = {
      username: form.username,
      nickname: form.nickname,
      password: form.password,
      avatarUrl: form.avatarUrl || undefined,
      role: form.role
    };
    await registerUser(payload);
    successMessage.value = '注册成功，3 秒后跳转到登录页';
    setTimeout(() => {
      router.push({ name: 'login' });
    }, 3000);
  } catch (error) {
    errorMessage.value = error.message || '注册失败，请稍后再试';
  } finally {
    isSubmitting.value = false;
  }
}
</script>
