<template>
  <div>
    <h1>Write Post</h1>
    <form @submit="savePost">
      <input type="text" v-model="boardTitle" placeholder="Title">
      <textarea v-model="boardContent" placeholder="Content"></textarea>
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
import { ref, onMounted, defineProps } from 'vue'
import { useRouter } from 'vue-router';
const router = useRouter();

const boardTitle = ref('')
const boardContent = ref('')
const selectedFile = ref(null)
const boardFilePath = ref('')
const boardEntity = ref(null)
const filePath = ref('')

const props = defineProps({
  boardId: {
    type: String,
    required: true
  }
})

const representPost = () => {
  axios
    .get(`/api/detail/${props.boardId}`)
    .then(response => {
      console.log(response.data)
      boardEntity.value = response.data
      boardTitle.value = boardEntity.value.boardTitle
      boardContent.value = boardEntity.value.boardContent
      boardFilePath.value = boardEntity.value.boardFilePath
      filePath.value = boardFilePath.value
    })
    .catch(error => {
      console.error(error)
    })
}

const savePost = event => {
  event.preventDefault()

  axios
    .post(`/api/write/${props.boardId}`, {
      boardTitle: boardTitle.value,
      boardContent: boardContent.value,
      boardFilePath: boardFilePath.value
    })
    .then(response => {
      console.log(response.data)
      alert('Post updated successfully!')
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

onMounted(() => {
  representPost()
})
</script>
