<template>
  <nav id="nav">
    <router-link to="/">Home</router-link> |
    <router-link to="/board">게시판</router-link> |
    <span v-if="loggedIn">
      <router-link @click="logout" to="/logout">로그아웃</router-link> |
    </span>
    <span v-else>
      <router-link to="/login">로그인</router-link>
    </span>
    <span v-if="loggedIn">
      <router-link to="/write">글쓰기</router-link>
    </span>
  </nav>
  <router-view/>
</template>

<script setup>
import axios from 'axios';
import {inject  } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();

const loggedIn = inject('globalVariable');

const logout = () =>{
  axios.post("/api/logout")
        .then(() => {
          // 로그아웃 성공 시 로그인 상태 변경
          loggedIn.value = false;
          router.push('/'); // 로그아웃 성공 시 홈 페이지로 이동
        })
        .catch(error => {
          console.error(error);
        });
}

const checkSession = () => {
  axios.get('/api/check-login')
    .then(response => {
      loggedIn.value = response.data;
    })
    .catch(error => {
      console.error(error);
    });
};

checkSession();
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #42b983;
}
</style>