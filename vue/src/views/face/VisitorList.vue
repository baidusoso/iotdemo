<template>
  <div class="app-container">
    <div class="main">
      <el-row type="flex" justify="space-between">
          <el-col :span="3">
              <el-input v-model="listQuery.name" placeholder="请输入姓名进行搜索"></el-input>
          </el-col>
          <el-col :span="3">
              <el-input v-model="listQuery.mobile" placeholder="请输入手机号进行搜索"></el-input>
          </el-col>
          <el-col :span="3">
              <el-input v-model="listQuery.certificateNum" placeholder="请输入身份证号进行搜索"></el-input>
          </el-col>
          <el-col style="display:flex;justify-content:flex-end" :span="8">
              <el-button @click="handleFilter" style="border:1px #1890FF solid;" icon="el-icon-search">搜索</el-button>
              <el-button type="primary" icon="plus"  @click="showCreate">新增</el-button>
              <!-- <el-button type="primary" icon="el-icon-upload2" @click="urlPush(`/`)">批量导入</el-button> -->
              <el-button type="danger" icon="plus"  @click="showCreate">批量删除</el-button>
          </el-col>
      </el-row>
    <el-table :data="list" v-loading.body="listLoading" element-loading-text="拼命加载中" border highlight-current-row style="margin-top: 30px;">
      <el-table-column align="center" type="selection" ></el-table-column>
      <el-table-column
          align="center"
          prop="facePic"
          label="照片">
           <template slot-scope="scope">
               <img width="100px" :src="`${imgHost}${scope.row.facePic}?s=${Math.random()}` || require('@/assets/image/avatar.jpg')" alt="">
          </template>
      </el-table-column>
      <el-table-column align="center" label="姓名" prop="name" ></el-table-column>
      <el-table-column align="center" label="性别" prop="gender" width="80">
        <template slot-scope="scope">
           <span v-if="scope.row.gender=='1'">男</span>
           <span v-else-if="scope.row.gender=='2'">女</span>
           <span v-else></span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="身份证号" prop="certificateNum" ></el-table-column>
      <el-table-column align="center" label="手机" prop="mobile"></el-table-column>
      <el-table-column align="center" label="最后修改时间" prop="updateTime">
        <template slot-scope="scope">
          {{scope.row.updateTime| dateYMDHMSFormat}}
        </template>
      </el-table-column>
      <el-table-column align="center" label="操作" width="200">
        <template slot-scope="scope">
          <el-button type="primary" icon="edit" @click="showUpdate(scope.$index)">修改</el-button>
          <el-button type="danger" icon="delete" @click="removeUser(scope.$index)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="listQuery.pageNum"
      :page-size="listQuery.pageRow" :total="totalCount" :page-sizes="[10, 20, 50, 100]" layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
    </div>
  </div>
</template>
<script>
  import {mapGetters} from 'vuex'
  import ScrollBar from '@/components/ScrollBar'
  export default {
    components: {ScrollBar},
    data() {
      return {
        totalCount: 0, //分页组件--数据总条数
        list: [], //表格的数据
        listLoading: false, //数据加载等待动画
        listQuery: {
          pageNum: 1, //页码
          pageRow: 10, //每页条数
          name:"",
          mobile:"",
          certificateNum:"",
          usergroup:"外来访客",
        },
        dialogStatus: 'create',
        dialogFormVisible: false,
        roleDialogVisible: false,
        textMap: {
          update: '编辑',
          create: '新建用户',
          role:'选择角色'
        },
        tempUser: {
          username: '',
          password: '',
          nickname: '',
          orgCode: '',
          userId: '',
          phone:''
        },
        imgHost:"http://10.69.212.11:3000/face/user/",
      }
    },
    created() {
      this.getList();
    },
    methods: {
      getList() {
        //查询列表
        this.listLoading = true;
        this.api({
          url: "/account/list",
          method: "post",
          data: this.listQuery
        }).then(data => {
          this.listLoading = false;
          this.list = data.list;
          this.totalCount = data.totalCount;
        }).catch(()=>{
          this.listLoading = false;
        })
      },
      handleSizeChange(val) {
        //改变每页数量
        this.listQuery.pageRow = parseInt(val);
        this.handleFilter();
      },
      handleCurrentChange(val) {
        //改变页码
        this.listQuery.pageNum = parseInt(val);
        this.getList();
      },
      handleFilter() {
        //查询事件
        this.listQuery.pageNum = 1;
        this.getList()
      },
      getIndex($index) {
        //表格序号
        return (this.listQuery.pageNum - 1) * this.listQuery.pageRow + $index + 1
      },
      showCreate() {
        //TODO
        this.$router.push({name: 'edit-visitor'})
      },
      showUpdate($index) {
        let account = this.list[$index];
        this.$router.push({name: 'edit-visitor',query: { accountId: account.id }})
      },
      removeVisitor($index) {
        let _vue = this;
        this.$confirm('确定删除此用户?', '提示', {
          confirmButtonText: '确定',
          showCancelButton: false,
          type: 'warning'
        }).then(() => {
          let user = _vue.list[$index];
          user.deleteStatus = '0';
          _vue.api({
            url: "/system/user/deleteUser",
            method: "post",
            data: user
          }).then(() => {
            _vue.getList()
          }).catch(() => {
            _vue.$message.error("删除失败")
          })
        })
      },
    }
  }
</script>
<style>
  .tree {
    position: fixed;
    width: 300px;
    height: 100%;
    overflow-y:auto;
    overflow-x: scroll;
    border-right: 1px solid #EEEEEE;
    padding-top: 20px;
  }
  .el-tree {
    min-width:100%;
    font-size:14px;
    display: inline-block;;
  }
  .main{
    padding: 20px;
  }
</style>
