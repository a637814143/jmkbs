<template>
  <section class="dashboard">
    <h1>欢迎回来，{{ displayName }}</h1>
    <p>这是普通用户的观影工作台，后续可以在这里浏览影片、撰写影评和收藏心仪的电影。</p>
    <router-link to="/login">返回登录</router-link>
  </section>
</template>

<script setup>
import { computed } from 'vue';

const rawUser = localStorage.getItem('jmk_movie_user');
const user = rawUser ? JSON.parse(rawUser) : null;

const displayName = computed(() => user?.nickname || user?.username || '影迷');
</script>
