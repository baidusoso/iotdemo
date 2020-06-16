<template>
  <el-dialog
    title="历史记录"
    :visible.sync="visible"
    width="60%"
    :before-close="onClose">
    <el-row class="m-t-10" style="height: 400px;">
      <el-scrollbar class="page-component__scroll">
        <el-table :data="data" style="width: 100%;">
          <el-table-column label="被访问人" prop="targetUserName"/>
          <el-table-column label="允许通行开始时间" prop="startTime"/>
          <el-table-column label="允许结束开始时间" prop="endTime"/>
        </el-table>
      </el-scrollbar>
    </el-row>
  </el-dialog>
</template>

<script>
  export default {
    props: {
      visible: Boolean,
      id: String,
      userId: String
    },
    data() {
      return {
        data: []
      }
    },
    mounted() {
      this.loadData()
    },
    methods: {
      onClose() {
        this.$emit('close')
      },
      loadData() {
        this.api.get(`/account/guest/${this.userId}/history`).then(res => {
          this.data = res
        })
      }
    }
  }
</script>

<style>
  thead th {
    background: #F5F5F5 !important;
    border-radius: 4px 4px 0 0;
    font-size: 14px;
    color: #333333;
    line-height: 22px;
  }
</style>
