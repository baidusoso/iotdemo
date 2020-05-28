<template>
  <el-menu class="titlebar" mode="horizontal">
    <span class="title">工业物联网大数据平台</span>
    <el-dropdown class="avatar-container" trigger="click">
      <div class="avatar-wrapper">
        <span class="title">{{$store.getters.nickname}}</span>
        <i class="el-icon-caret-bottom"></i>
      </div>
      <el-dropdown-menu class="user-dropdown" slot="dropdown">
        <router-link class="inlineBlock" to="/">
          <el-dropdown-item>
            <span @click="logout" style="display:block;">修改密码</span>
          </el-dropdown-item>
        </router-link>
        <el-dropdown-item divided>
          <span @click="logout" style="display:block;">登出</span>
        </el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </el-menu>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  methods: {
    logout() {
      this.$store.dispatch('LogOut').then(() => {
        location.reload() // 为了重新实例化vue-router对象 避免bug
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.titlebar {
  height: 50px;
  line-height: 50px;
  border-radius: 0px !important;
  background-color: #334154;
  position: relative;
  top: 0px;
  .title{
    color:white;
    margin-left: 20px;
    font-size: 20px;
  }
  .avatar-container {
    height: 50px;
    display: inline-block;
    position: absolute;
    right: 35px;
    .avatar-wrapper {
      cursor: pointer;
      margin-top:0;
      position: relative;
      .el-icon-caret-bottom {
        position: absolute;
        right: -20px;
        top: 18px;
        font-size: 20px;
        color: white;
      }
    }
  }
}
</style>
