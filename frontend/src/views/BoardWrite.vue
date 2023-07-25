<template>
  <div>
    <h1>Write Post</h1>
    <form @submit="savePost">
      <div>
        <input type="text" v-model="boardTitle" placeholder="Title">
      </div>
      <div>
        <textarea rows="5" cols="50" v-model="boardContent" placeholder="Content"></textarea>
      </div>
      <div v-if="boardFilePath===null">
        <h1>Upload File</h1>
        <input type="file" @change="handleFileChange">
        <button type="button" @click="uploadFile">Upload</button>
      </div>
      <div v-if="boardFilePath !== null">
        {{ boardFilePath }}
        <button type="button" @click="removeFile">Remove</button>
      </div>
      <button type="submit">Save</button>
    </form>
  </div>
</template>

<script setup>
import axios from 'axios'
import { ref } from 'vue'
import { useRouter } from 'vue-router';

const router = useRouter();

const boardTitle = ref(null)
const boardContent = ref(null)
const selectedFile = ref(null)
const boardFilePath = ref(null)

const savePost = event => {
  event.preventDefault()

  axios
    .post('/api/write', {
      boardTitle: boardTitle.value,
      boardContent: boardContent.value,
      boardFilePath: boardFilePath.value
    })
    .then(response => {
      console.log(response.data)
      alert('Post saved successfully!')
      router.push('/board')
    })
    .catch(error => {
      console.error(error)
    })
}

const handleFileChange = event => {
  selectedFile.value = event.target.files[0]
}

const uploadFile = () => {
  if (selectedFile.value) {
    const formData = new FormData()
    formData.append('file', selectedFile.value)

    axios
      .post('/api/upload', formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      })
      .then(response => {
        console.log(response.data)
        boardFilePath.value = response.data
      })
      .catch(error => {
        console.error(error)
      })
  }
}

const removeFile = () => {
  boardFilePath.value = null
}
</script>
