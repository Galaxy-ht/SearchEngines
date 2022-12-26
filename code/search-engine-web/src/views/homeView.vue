<template>
  <el-main >

    <el-row :gutter="16">
      <el-col :span="2"></el-col>
      <el-col :span="14">
        <el-card style="min-height: 60vh; text-align: center; align-items: center;">
          <div style="text-align: center;">
            <el-image
              style="display: inline-block; height: 70px;"
              src="https://s3-cn-south-1.qiniucs.com/test-g/images/logo.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=RJ-_huemj6awvksa6zlgyEK4fefrCwU3izst6JGE%2F20221225%2Fcn-south-1%2Fs3%2Faws4_request&X-Amz-Date=20221225T055814Z&X-Amz-Expires=604799&X-Amz-Signature=98aec815612444a2aadc3a834f3dcfa373f4438cfc7e3ea001c9765eaddd37f7&X-Amz-SignedHeaders=host"></el-image>
          </div>
          <el-autocomplete v-model="searchInput" hide-loading
                    style="width: 800px; height: 20px; font-size: 18px; margin: 20px;"
                    placeholder="搜索内容"
                           :fetch-suggestions="prefixSearch"
                           :trigger-on-focus="false"
                           @select="handleSelect"
          >
            <template #suffix>
              <el-button type="text" >
                <el-icon size="22">
                  <Microphone/>
                </el-icon>
              </el-button>
              <el-button type="text" @click="do_search(searchInput, 0, 10)">
                <el-icon size="22">
                  <search/>
                </el-icon>
              </el-button>
            </template>
          </el-autocomplete>
          <div style="width: 800px;">
            <span style="font-size: 18px;">历史记录: </span>
          <el-tag
              @click="do_search(searchTag, 0, 10)"
              v-for="(searchTag, index) in searchHistoryList" :key="index"
              style="width: 80px; height: 25px; overflow:hidden; text-overflow:ellipsis; white-space:nowrap; margin: 5px;"
          >{{searchTag}}</el-tag>
          </div>
          <el-empty description="无" v-if="!messageList.length"></el-empty>
          <ul v-if="messageList.length" style="margin-left: 5px;">
            <li style="margin-top: 5px;, width: 800px; min-height: 20px; max-height: 100px; text-align: left; overflow: hidden;
            text-overflow: ellipsis;
            display:-webkit-box;
            -webkit-box-orient:vertical;
            -webkit-line-clamp:4;
            "
                v-for="(message, index) in messageList" :key="index">
              <el-link @click="go_push(message.url)" style="font-size: 18px; font-weight: 600;" v-html="message.title">
              </el-link>
              <div v-html="message.content"></div>
            </li>
          </ul>
          <el-pagination
              v-if="messageList.length"
              v-model:currentPage="currentPage"
              v-model:page-size="pageSize"
              :page-sizes="[10, 20, 50, 100]"
              background
              :total="total"
              layout="total, sizes, prev, pager, next, jumper"
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              style="float: right; margin-top: 5px; margin-bottom: 5px;"
          />
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="min-height: 20vh" :body-style="{ padding: '0px' }">
          <template #header>
            <span style="font-size: 20px; font-weight: 600;">热榜</span>
          </template>
          <div style="height: 100%; width: 100%" class="top-10">
            <ul style="padding: 5px; margin-top: 3px; margin-bottom: 5px;">
              <li v-for="(hotKey, index) in hotBarList" :key="index">
                <el-link @click="do_search(hotKey,0,10)">{{hotKey}}</el-link>
              </li>
            </ul>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </el-main>
</template>

<script setup>
  import {Search, Microphone} from '@element-plus/icons-vue';
  import {ref} from "vue";
  import request from "@/utils/request";
  let searchInput = ref("");
  let messageList = ref([]);
  let currentPage = ref(1);
  let pageSize = ref(10);
  let total = ref(0);
  let hotBarList = ref([]);
  let searchHistoryList = ref([]);

  const do_search = (keyword, cp, ps) =>{
    getSearchHistory();
    searchInput.value = keyword;
    request.get("/search", {
      params:
          {
            keyword: keyword,
            page: cp,
            size: ps
          }
    }).
    then(res => {
      messageList.value = res.data;
      total.value = res.total - 10;
    }).catch(err => {
      console.log(err);
    })
  }

  const go_push = url => {
   window.open(url);
  }

  const prefixSearch = (prefix, cb) => {
    let results = [];
    if(prefix === '') cb(results);
    else{
      request.get("/search-suggest", {
        params:{
          prefix: prefix
        }
      }).then(res => {
        results = res;
        cb(results);
      }).catch(err => {
        console.log(err);
      })
    }
  }

  const getLeaderBar = () =>{
    request.get("/leader-bar", {})
        .then(res => {
          for(let i = 0; i < res.length; i ++) hotBarList.value.push(res[i].value);
          console.log(hotBarList.value);
        }).catch(err => {
          console.log(err);
    })
  }

  const getSearchHistory = () => {
    request.get("/search-history", {})
        .then(res => {
          searchHistoryList.value = res;
        }).catch(err => {
          console.log(err);
    })
  }

  const handleSelect = (item) => {
    do_search(item.value);
  }

  const handleSizeChange = size => {
    pageSize.value = size;
    do_search(searchInput.value, currentPage.value, pageSize.value);
  }

  const handleCurrentChange = Cpage => {
    currentPage.value = Cpage;
    do_search(searchInput.value, currentPage.value, pageSize.value);
  }
  getSearchHistory();
  getLeaderBar();
</script>

<style scoped>
  .top-10 {
    float: left;
    width: 100%;
    height: 100%;
  }
  .top-10 ul {
    counter-reset: section;
  }

  .top-10 li {
    float: left;
    width: 100%;
    border-bottom: 1px solid #b8c2cc;
    line-height: 46px;
    height: 46px;
    overflow: hidden;
    color: #525C66;
    font-size: 18px;
  }

  .top-10 li:before {
    counter-increment: section;
    content: counter(section);
    display: inline-block;
    padding: 0 12px;
    margin-right: 10px;
    height: 18px;
    line-height: 18px;
    background: #b8c2cc;
    color: #fff;
    border-radius: 3px;
    font-size: 14px
  }
  .top-10 li:nth-child(1):before {
    background: rgb(243,79,79);
  }

  .top-10 li:nth-child(2):before {
    background: rgb(254,148,25);
  }

  .top-10 li:nth-child(3):before {
    background: rgb(251,193,44);
  }
</style>