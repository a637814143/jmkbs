<template>
  <section class="dashboard">
    <h1>广告主工作台</h1>
    <p>欢迎，{{ displayName }}。这里将展示广告账户余额、投放计划和创意审核进度。</p>
    <router-link to="/login">切换账号</router-link>
  </section>
</template>

<script setup>
import { computed } from 'vue';

const rawUser = localStorage.getItem('jmk_movie_user');
const user = rawUser ? JSON.parse(rawUser) : null;

const displayName = computed(() => user?.nickname || user?.username || '广告主');
</script>
