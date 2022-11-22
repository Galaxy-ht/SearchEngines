<template>
  <el-main style="height: 100vh; text-align: center;">
    <el-input v-model="searchInput"
              style="width: 600px; height: 60px; font-size: 18px; margin: 20px;"
              placeholder="搜索内容">
      <template #suffix>
        <el-button type="text" @click="do_search">
          <el-icon size="22">
            <Microphone/>
          </el-icon>
        </el-button>
        <el-button type="text">
          <el-icon size="22">
            <search/>
          </el-icon>
        </el-button>
      </template>
    </el-input>
  </el-main>
</template>

<script setup>
  import {Search, Microphone} from '@element-plus/icons-vue';
  import {ref} from "vue";
  import request from "@/utils/request";
  let searchInput = ref("");

  const do_search = () =>{
    request.post("", {}, {}).
    then(res => {
      console.log(res);
    }).catch(err => {
      console.log(err);
    })
  }
</script>

<style scoped>

</style>