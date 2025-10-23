<template>
  <section class="dashboard">
    <h1>管理员控制台</h1>
    <p>您好，{{ displayName }}。从这里可以进入用户审核、影评举报处理与广告投放监控模块。</p>
    <router-link to="/login">返回登录</router-link>
  </section>
</template>

<script setup>
import { computed } from 'vue';

const rawUser = localStorage.getItem('jmk_movie_user');
const user = rawUser ? JSON.parse(rawUser) : null;

const displayName = computed(() => user?.nickname || user?.username || '管理员');
</script>
