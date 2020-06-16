<template>
  <div>
    <div class="page-body">
      <el-form style="width:50%;margin:0 auto;" label-width="100px">
        <el-form-item label="姓名">
          <span>{{this.guest ? this.guest.name : ''}}</span>
        </el-form-item>
        <el-form-item label="人脸照片">
          <div>
            <img v-if="imageUrl" :src="imageUrl" class="avatar" />
          </div>
        </el-form-item>
        <el-form-item label="身份证号">
          <span>{{this.guest ? this.guest.certificateNum : ''}}</span>
        </el-form-item>
        <el-form-item label="性别">
          <span>{{this.guest ? this.guest.gender : ''}}</span>
        </el-form-item>
        <el-form-item label="手机号">
          <span>{{this.guest ? this.guest.mobile : ''}}</span>
        </el-form-item>
        <el-form-item label="被访人员">
          <span>{{this.guest ? this.guest.targetUserName : ''}}</span>
        </el-form-item>
        <el-form-item label="通行时间">
          <span>{{this.getPassTime()}}</span>
        </el-form-item>
        <el-form-item label="历史访问记录">
          <el-button @click="history = true" type="text">
            查看
          </el-button>
        </el-form-item>
        <el-form-item v-if="reviewing" istyle="width:100%;">
          <el-button style="width:150px;" @click="onReview(false)">
            拒绝
          </el-button>
          <el-button style="width:150px;margin-left:10%;" type="primary" @click="onReview(true)">
            同意
          </el-button>
        </el-form-item>
      </el-form>
    </div>
    <visitor-visit-history v-if="history" :visible="history" :id="id" :user-id="userId" @close="history = false" />
  </div>
</template>

<script>
  import VisitorVisitHistory from './VisitorVisitHistory'

  export default {
    components: {
      VisitorVisitHistory,
    },
    data() {
      return {
        id: null,
        guest: null,
        imageUrl: null,
        reviewing: true,
        history: false,
        userId: null,
      }
    },
    mounted() {
      const id = this.$route.query.id
      if (!id) return this.$router.go(-1)
      this.id = id
      this.loadData()
    },
    methods: {
      loadData() {
        this.api.get(`/account/guest/${this.id}`).then((res) => {
          console.log(res)
          this.guest = res
          // this.imageUrl = this.guest.facePic + `?s=${Math.random()}`
          this.reviewing = this.guest.status == '0'
          this.userId = this.guest.userId
        })
      },
      getPassTime() {
        if (!this.guest) return ''
        if (this.guest.status == '1')
          return `${this.guest.startTime} 至 ${this.guest.endTime}`
        return this.guest.status == '0' ? "申请中" : "已拒绝"
      },
      onReview(result) {
        const formData = new FormData()
        formData.append("approve", result)
        
        this.api.post(`/account/guest/review/${this.id}`, formData).then(() => {
          this.loadData()
          this.$message({
            type: 'success',
            message: result ? '已同意!' : '已拒绝!'
          })
        })
      },
    },
  }
</script>

<style>
  .avatar {
    width: 210px;
    display: block;
  }

  .el-form-item__content {
    display: flex;
  }
</style>
