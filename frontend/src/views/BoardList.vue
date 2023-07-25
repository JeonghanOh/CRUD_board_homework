<template>
  <div>
    <div class="search">
      <input type="text" v-model="searchKeyword" placeholder="검색어를 입력하세요" />
      <button @click="search">검색</button>
    </div>
    <div class="board">
      <table v-if="responseData" class="center">
        <tr class="name">
          <th>번호</th>
          <th class="title">제목</th>
          <th class="writer">작성자</th>
          <th class="date">등록일</th>
        </tr>
        <tr v-for="item in responseData.content" :key="item.boardId">
          <td>{{ item.boardId }}</td>
          <td @click="redirectToDetail(item.boardId)">{{ item.boardTitle }}</td>
          <td>{{ item.userId }}</td>
          <td>{{ item.boardDate }}</td>
        </tr>
      </table>
    </div>
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

<style scoped>
.name th{
  border-top: 1px solid #42B983;
  border-bottom: 1px solid #42B983;
}

.center tr{
  margin-bottom: 5px;
}

table {
  border-collapse: collapse; /* 테이블 경계 병합 */
}

.center{
  margin: 0 auto;
}

.title{
  width: 400px;
}
.writer{
  width: 200px;
}

.date{
  width: 100px;
}

.search{
  margin-bottom: 30px;
}

.board{
  margin-bottom: 30px;
}
</style>