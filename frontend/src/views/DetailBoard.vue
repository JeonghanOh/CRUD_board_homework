<template>
  <div>
    <h1>BoardDetail</h1>
    <p>{{ boardId }}</p>
    <p>제목</p>
    <p v-if="responseData">{{ responseData.boardTitle }}</p>
    <p>내용</p>
    <p v-if="responseData">{{ responseData.boardContent }}</p>
    <div v-if="responseData && responseData.userId === user_id">
      <button type="button" @click="deleteDetail">Delete</button>
      <button type="button" @click="updateDetail">Update</button>
    </div>
    <button type="button" @click="blockDetail">게시글 차단</button>
  </div>
</template>

<script setup>
import axios from 'axios'
import { ref, onMounted, defineProps } from 'vue'
import { useRouter } from 'vue-router';

const props = defineProps({
  boardId: {
    type: String,
    required: true
  }
})

const router = useRouter();

const boardId = ref(props.boardId)
const responseData = ref(null)
const user_id = ref(null)
const boardFilePath = ref(null)

const sendDataToSpring = () => {
  axios
    .get(`/api/detail/${boardId.value}`)
    .then(response => {
      responseData.value = response.data
      boardFilePath.value = response.data.boardFilePath;
      console.log(response.data)
    })
    .catch(error => {
      console.error(error)
    })
}

const loginStatus = () => {
  axios
    .post(`/api/loginstatus`)
    .then(response => {
      user_id.value = response.data
      console.log(response.data)
    })
    .catch(error => {
      console.error(error)
    })
}

const deleteDetail = () => {
  axios
    .get(`/api/delete/${boardId.value}`)
    .then(response => {
      console.log(response.data)
    })
    .catch(error => {
      console.error(error)
    })
}

const updateDetail = () => {
  axios
    .get(`/api/update/${boardId.value}`)
    .then(response => {
      console.log(response.data)
      // assuming you have access to router instance
      router.push(`/write/${boardId.value}`)
    })
    .catch(error => {
      console.error(error)
    })
}

const blockDetail = () => {
  axios
    .get(`/api/toblock/${boardId.value}`)
    .then(response => {
      console.log(response.data)
      // assuming you have access to router instance
      router.push(`/board`)
    })
    .catch(error => {
      console.error(error)
    })
}

onMounted(() => {
  console.log(boardId.value) // boardId 값 확인
  sendDataToSpring()
  loginStatus()
})
</script>
