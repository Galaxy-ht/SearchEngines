<template>
  <el-main >
    <el-row :gutter="16">
      <el-col :span="2"></el-col>
      <el-col :span="14">
        <el-card style="min-height: 60vh; text-align: center;">
          <el-input v-model="searchInput"
                    style="width: 800px; height: 50px; font-size: 18px; margin: 20px;"
                    placeholder="搜索内容">
            <template #suffix>
              <el-button type="text" >
                <el-icon size="22">
                  <Microphone/>
                </el-icon>
              </el-button>
              <el-button type="text" @click="do_search">
                <el-icon size="22">
                  <search/>
                </el-icon>
              </el-button>
            </template>
          </el-input>
          <div style="width: 800px; margin-left: auto; margin-right: auto;">
            <el-alert style="margin: 0 0;" v-if="searchInput" title="查询用时:0.00001ms" type="success"></el-alert>
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
              <el-link @click="go_push(message.url)" style="font-size: 18px; font-weight: 600;">{{message.title}}</el-link>
              <div>{{message.content}}</div>
            </li>
          </ul>
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

  const do_search = () =>{
    request.get("/search", {
      params:
          {
            title: searchInput.value
          }
    }).
    then(res => {
      messageList.value = res;
    }).catch(err => {
      console.log(err);
    })
  }

  const go_push = url => {
   window.open(url);
  }
</script>

<style scoped>

</style>