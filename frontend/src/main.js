import { createApp, ref } from 'vue';
import App from './App.vue';
import router from './router';

const app = createApp(App);

// 전역 변수 설정
const globalVariable = ref(false);
app.provide('globalVariable', globalVariable);

app
  .use(router)
  .mount('#app');