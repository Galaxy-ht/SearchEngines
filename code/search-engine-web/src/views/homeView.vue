<template>
  <el-main >
    <el-row :gutter="16">
      <el-col :span="2"></el-col>
      <el-col :span="14">
        <el-card style="min-height: 60vh; text-align: center; align-items: center;">
          <el-autocomplete v-model="searchInput"
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
              <el-button type="text" @click="do_search(searchInput, currentPage, pageSize)">
                <el-icon size="22">
                  <search/>
                </el-icon>
              </el-button>
            </template>
          </el-autocomplete>
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
        <el-card>
          <template #header>
            <span style="font-size: 20px; font-weight: 600;">热榜</span>
          </template>
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
  let currentPage = ref(0);
  let pageSize = ref(10);
  let total = ref(0);

  const do_search = (keyword, cp, ps) =>{
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
</script>

<style scoped>

</style>