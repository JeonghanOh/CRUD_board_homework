<template>
  <div>
    <form @submit="submitLoginForm">
      <input type="text" v-model="username" placeholder="Username">
      <input type="password" v-model="password" placeholder="Password">
      <button type="submit">Login</button>
    </form>
  </div>
</template>

<script setup>
import axios from 'axios';
import {ref, inject } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();

const loggedIn = inject('globalVariable');

// 폼 입력값 초기화를 위해 ref로 선언
const username = ref('');
const password = ref('');

const submitLoginForm = (event) => {
  event.preventDefault(); // 폼의 기본 동작인 페이지 새로고침 방지

  // 로그인 요청 보내기
  axios.post('/api/login', {
    username: username.value,
    password: password.value
  })
    .then(response => {
      // 로그인 성공
      console.log(response.data);
      console.log(router);
      loggedIn.value = true;
      router.push('/'); // 로그인 성공 시 홈 페이지로 이동
    })
    .catch(error => {
      // 로그인 실패
      console.error(error);
    });
};
</script>
