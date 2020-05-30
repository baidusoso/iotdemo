<template>
  <div class="app-container">
    <div id="org" style="width:20%;height100%;float:left;">
      <el-tree :data="rootOrg" ref="org" node-key="orgCode" :default-expanded-keys="[]"
        :highlight-current="true" :default-checked-keys="[]" :props="defaultProps" :check-strictly="true"
        :check-on-click-node="true" @check-change="handleCheckChange" @node-click="handleNodeClick">
      </el-tree>
    </div>
    <div id="person" style="width:79%;float:left;">
      <el-row type="flex" justify="space-between">
          <el-col :span="3">
              <el-input v-model="listQuery.name" placeholder="请输入姓名进行搜索"></el-input>
          </el-col>
          <el-col :span="3">
              <el-input v-model="listQuery.no" placeholder="请输入工号进行搜索"></el-input>
          </el-col>
          <el-col :span="3">
              <el-input v-model="listQuery.mobile" placeholder="请输入手机号进行搜索"></el-input>
          </el-col>
          <el-col :span="3">
              <el-input v-model="listQuery.certificateNum" placeholder="请输入身份证号进行搜索"></el-input>
          </el-col>
          <el-col style="display:flex;justify-content:flex-end" :span="8">
              <el-button @click="getList" style="border:1px #1890FF solid;" icon="el-icon-search">搜索</el-button>
              <el-button type="primary" icon="plus"  @click="sync">同步</el-button>
              <el-button type="primary" icon="el-icon-upload2" @click="urlPush(`/`)">批量导入</el-button>
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
      <el-table-column align="center" label="性别" prop="gender" >
        <template scope="scope">
           <span v-if="scope.row.gender=='1'">男</span>
           <span v-else-if="scope.row.gender=='2'">女</span>
           <span v-else></span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="工号" prop="no" ></el-table-column>
      <el-table-column align="center" label="身份证号" prop="certificateNum" ></el-table-column>
      <el-table-column align="center" label="手机" prop="mobile"></el-table-column>
      <el-table-column align="center" label="最后修改时间" prop="updateTime">
        <template slot-scope="scope">
          {{scope.row.updateTime| dateYMDHMSFormat}}
        </template>
      </el-table-column>
      <el-table-column align="center" label="操作" width="300">
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
  import {
    mapGetters
  } from 'vuex'

  export default {
    data() {
      return {
        totalCount: 0, //分页组件--数据总条数
        list: [], //表格的数据
        listLoading: false, //数据加载等待动画
        listQuery: {
          offset: 0, //页码
          pageRow: 10, //每页条数
          orgId:"",
          name:"",
          no:"",
          mobile:"",
          certificateNum:""
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
        rootOrg: [],
        defaultProps: {
          "children": "children",
          "label": "orgName"
        },
        editOrgCode: "",
        editOrgName: "",
        rolelist:[],
        imgHost:"http://10.69.212.11:3000/face/user/",
      }
    },
    created() {
      this.getList();
      this.getRootOrg();
    },
    watch:{
            rolelist(){
              this.$nextTick( ()=> {
                for (var i = 0; i < this.rolelist.length; i++) {
                  this.$refs.roletable.toggleRowSelection(this.rolelist[i],this.rolelist[i].userId!=null)
                }
              })
            },
    },
    computed: {
      ...mapGetters([
        'userId'
      ])
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
      getRootOrg() {
        var _vue = this;
        this.api({
          url: "/system/org/getRootOrg",
          method: "get"
        }).then(data => {
          var rootOrg = [];
          if (data != null) {
            rootOrg.push(data);
          }
          _vue.rootOrg = rootOrg;
        })
      },
      sync(){
        var _vue = this;
        this.api({
          url: "/account/sync",
          method: "get"
        }).then(data => {
          _vue.getList();
        })
      },
      handleSizeChange(val) {
        //改变每页数量
        this.listQuery.pageRow = parseInt(val);
        this.listQuery.offset=0;
        this.handleFilter();
      },
      handleCurrentChange(val) {
        //改变页码
        this.listQuery.pageNum = parseInt(val)
        this.listQuery.offset=this.listQuery.pageNum*this.listQuery.pageRow;
        this.getList();
      },
      handleFilter() {
        //查询事件
        this.listQuery.pageNum = 1
        this.listQuery.offset=(this.listQuery.pageNum-1)*this.listQuery.pageRow;
        this.getList()
      },
      getIndex($index) {
        //表格序号
        return (this.listQuery.pageNum - 1) * this.listQuery.pageRow + $index + 1
      },
      showCreate() {
        //显示新增对话框
        this.tempUser.username = "";
        this.tempUser.password = "";
        this.tempUser.nickname = "";
        this.tempUser.roleId = "";
        this.tempUser.userId = "";
        this.dialogStatus = "create"
        this.dialogFormVisible = true
      },
      showUserRole($index){
        this.listLoading=true;
        let user = this.list[$index];
        this.tempUser.userId = user.userId;
        this.api({
          url: "/system/user/getAllRolesByUser",
          method: "post",
          data: {
            userId:user.userId
          },
        }).then(data => {
          this.listLoading = false;
          this.rolelist = data.list;
          this.dialogStatus = "role";
          this.roleDialogVisible = true;
          // this.$nextTick( ()=> {
          //     for(let i=0; i < this.rolelist.length; i++) {
          //       this.$refs.roletable.toggleRowSelection(this.rolelist[i],this.rolelist[i].userId!=null)
          //     }
          // })
        }).catch(()=>{
          this.listLoading = false;
        })
      },
      showUpdate($index) {
        let user = this.list[$index];
        console.log(user);
        this.tempUser.username = user.username;
        this.tempUser.nickname = user.nickname;
        this.tempUser.orgCode = user.orgCode;
        this.tempUser.userId = user.userId;
        this.tempUser.status = '1';
        this.tempUser.password = '';
        this.tempUser.phone = user.phone;
        this.editOrgName=user.orgName;
        this.dialogStatus = "update";
        this.dialogFormVisible = true;
      },
      updateUserRole($index){
        var selection=this.$refs.roletable.store.states.selection;
        if(selection.length==0){
          this.$message.error("至少选择一个角色");
          return false;
        }
        var roleIds=[];
        for(var i=0;i<selection.length;i++){
          roleIds.push(selection[i].roleId);
        }
        var _vue=this;
        this.api({
          url: "/system/user/updateUserRole",
          method: "post",
          data: {
            userId:_vue.tempUser.userId,
            roleIds:roleIds
          }
        }).then(() => {
          _vue.roleDialogVisible = false
        })
      },
      createUser() {
        //添加新用户
        this.api({
          url: "/system/user/addUser",
          method: "post",
          data: this.tempUser
        }).then(() => {
          this.getList();
          this.dialogFormVisible = false
        })
      },
      updateUser() {
        //修改用户信息
        let _vue = this;
        this.api({
          url: "/system/user/updateUser",
          method: "post",
          data: this.tempUser
        }).then(() => {
          let msg = "修改成功";
          this.dialogFormVisible = false
          if (this.userId === this.tempUser.userId) {
            msg = '修改成功,部分信息重新登录后生效'
          }
          this.$message({
            message: msg,
            type: 'success',
            duration: 1 * 1000,
            onClose: () => {
              _vue.getList();
            }
          })
        })
      },
      removeUser($index) {
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
      handleCheckChange(item, node, self) {
        console.log("handleCheckChange:"+node)
        if (node == true) {
          console.log(item);
          this.editorgCode = item.orgCode;
          this.editOrgName = item.orgName;
          this.$refs.org.setCheckedKeys([item.orgCode]);
          this.tempUser.orgCode=this.editOrgCode;
        } else {
          if (this.editOrgCode == item.orgCode) {
            this.$refs.org.setCheckedKeys([item.orgCode]);
          }
        }
      },
      handleNodeClick(item, node, self) {
        console.log("handleNodeClick");
        this.editOrgCode = item.orgCode;
        this.editOrgName = item.orgName;
        this.$refs.org.setCheckedKeys([item.orgCode]);
        this.listQuery.pageNum=1;
        this.listQuery.offset=0;
        this.listQuery.orgId=item.orgCode;
        this.listQuery.name="";
        this.listQuery.no="";
        this.listQuery.mobile="";
        this.listQuery.certificateNum="";
        this.getList();
      },
    }
  }
</script>
<style>
  .customWidth {
    width: 500px;
  }
</style>
