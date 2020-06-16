<template>
  <div>
    <div class="page-body">
      <el-row type="flex">
        <el-col :span="4">
          <el-date-picker
            placeholder="开始时间"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            v-model="startAt"
            @change="loadData"
          />
        </el-col>
        <el-col :span="4" style="margin-left: 20px;">
          <el-date-picker
            placeholder="结束时间"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            v-model="endAt"
            @change="loadData"
          />
        </el-col>
        <el-col :span="8" :offset="6">
          <el-input placeholder="姓名/工号/身份证号/手机号" v-model="query">
            <el-button slot="append" type="primary" icon="el-icon-search" @click="loadData"
                       style="background: #1890FF;border-radius: 0 4px 4px 0;font-size: 16px;color: #FFFFFF;">搜索
            </el-button>
          </el-input>
        </el-col>
        <el-col :span="2" style="margin-left: 20px;">
          <el-button type="primary" icon="el-icon-search" @click="showCreate">新增
          </el-button>
        </el-col>
      </el-row>
      <el-row style="margin-top:20px">
        <el-table :data="histories" style="width: 100%;">
          <el-table-column label="姓名" prop="name"/>
          <el-table-column label="性别" prop="gender"/>
          <el-table-column label="身份证号" prop="certificateNum"/>
          <el-table-column label="手机号" prop="mobile"/>
          <el-table-column label="被访问人" prop="targetUserName"/>
          <el-table-column label="申请时间" prop="createTime"/>
          <el-table-column label="通行时间" >
            <template slot-scope="scope">
                <span v-if="scope.row.status=='1'">{{scope.row.startTime}}-{{scope.row.endTime}}</span>
                <span v-else></span>
              </template>
            </el-table-column>
          <el-table-column label="状态" prop="status">
            <template slot-scope="scope">
              <span v-if="scope.row.status=='1'">已通过</span>
              <span v-else-if="scope.row.status=='-1'">已拒绝</span>
              <span v-else>申请中</span>
            </template>
          </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button
                @click="onModify(scope.row)"
                type="text"
                size="small">
                修改
              </el-button>
              <el-button
                @click="onDelete(scope.row)"
                style="color:#E74B3B;"
                type="text"
                size="small">
                删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-row>
      <el-row>
        <el-col :span="8" :offset="8">
          <el-pagination
            background
            :hide-on-single-page="true"
            class="m-t-10"
            layout="prev, pager, next, jumper"
            :page-count="pageCount"
            @current-change="onPageChange">
          </el-pagination>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        startAt: '',
        endAt: '',
        query: '',
        page: 1,
        pageSize: 10,
        histories: [],
        pageCount: 0
      }
    },
    created() {
      this.loadData()
    },
    methods: {
      showCreate(){
        this.$router.push('visitor-apply')
      },
      onModify(user) {
        this.$router.push(`/admin/visit/edit?id=${user.id}`)
      },
      onDelete(user) {
        // this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        //   confirmButtonText: '确定',
        //   cancelButtonText: '取消',
        //   type: 'warning'
        // }).then(() => {
        //   this.$http.delete(`user_visit/${user.id}`).then(() => {
        //     this.loadData()
        //     this.$message({
        //       type: 'success',
        //       message: '删除成功!'
        //     })
        //   })
        // })
      },
      onPageChange(page) {
        this.page = page
        this.loadData()
      },
      loadData() {
        this.api.post('/account/guest/list', {
          params: {
            // startAt: this.startAt,
            // endAt: this.endAt,
            // query: this.query,
            pageNo: this.page,
            pageRow: this.pageSize
          }
        }).then(res => {
          this.histories = res.list
          this.pageCount = res.totalPage
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
