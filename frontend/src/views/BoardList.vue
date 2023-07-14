<template>
  <div>
    <input type="text" v-model="searchKeyword" placeholder="검색어를 입력하세요" />
    <button @click="search">검색</button>

    <ul v-if="responseData">
      <li v-for="item in responseData.content" :key="item.boardId">
        <a @click="redirectToDetail(item.boardId)">{{ item.boardTitle }}</a>
      </li>
    </ul>

    <div v-if="responseData && responseData.totalPages > 1">
      <button v-for="pageNumber in responseData.totalPages" :key="pageNumber" @click="changePage(pageNumber)">
        {{ pageNumber }}
      </button>
    </div>
  </div>
</template>

<script setup>
import axios from 'axios'
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'

const responseData = ref(null)
const currentPage = ref(1)
const itemsPerPage = ref(10)
const searchKeyword = ref('')

const router = useRouter()

const fetchData = () => {
  const params = {
    page: currentPage.value - 1, // 스프링은 0부터 페이지를 계산하므로 1을 뺍니다.
    size: itemsPerPage.value,
    sort: 'board_id,desc' // 필요에 따라 정렬 기준을 변경하세요.
  }

  axios
    .get('/api/board', { params })
    .then(response => {
      responseData.value = response.data
    })
    .catch(error => {
      console.error(error)
    })
}

const redirectToDetail = boardId => {
  router.push({ name: 'DetailBoard', params: { boardId } })
}

const changePage = pageNumber => {
  currentPage.value = pageNumber
  fetchData()
}

const search = () => {
  const params = {
    query: searchKeyword.value,
    page: currentPage.value - 1, // 스프링은 0부터 페이지를 계산하므로 1을 뺍니다.
    size: itemsPerPage.value,
    sort: 'board_id,desc' // 필요에 따라 정렬 기준을 변경하세요.
  }
  axios
    .get('/api/searchresult', { params })
    .then(response => {
      responseData.value = response.data
    })
    .catch(error => {
      console.error(error)
    })
}

onMounted(() => {
  fetchData()
})
</script>