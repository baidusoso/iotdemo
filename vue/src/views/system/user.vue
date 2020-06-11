<template>
  <div class="app-container p-20">
    <div class="filter-container">
      <el-form>
        <el-form-item>
          <el-button type="primary" icon="plus"  @click="showCreate">添加</el-button>
          <el-button type="danger" icon="plus"  @click="showCreate">批量删除</el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-table :data="list" v-loading.body="listLoading" element-loading-text="拼命加载中" border highlight-current-row>
      <el-table-column align="center" type="selection" ></el-table-column>
      <el-table-column align="center" label="账号(工号)" prop="username" ></el-table-column>
      <el-table-column align="center" label="姓名" prop="nickname" ></el-table-column>
      <el-table-column align="center" label="部门" prop="orgName" ></el-table-column>
      <el-table-column align="center" label="手机号" prop="phone" ></el-table-column>
      <el-table-column align="center" label="创建时间" prop="createTime"></el-table-column>
      <el-table-column align="center" label="最后登陆时间" prop="updateTime">
        <template slot-scope="scope">
          {{scope.row.updateTime| dateYMDHMSFormat}}
        </template>
      </el-table-column>
      <el-table-column align="center" label="操作" width="300">
        <template slot-scope="scope">
          <el-button type="primary" icon="edit" @click="showUserRole(scope.$index)">查看角色</el-button>
          <el-button type="primary" icon="edit" @click="showUpdate(scope.$index)">修改</el-button>
          <el-button type="danger" icon="delete" @click="removeUser(scope.$index)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="listQuery.pageNum"
      :page-size="listQuery.pageRow" :total="totalCount" :page-sizes="[10, 20, 50, 100]" layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible" customClass="customWidth">
      <el-form class="small-space" :model="tempUser" label-position="left" label-width="80px" style='width: 300px; margin-left:50px;'>
        <el-form-item label="账户" required>
          <el-input type="text" v-model="tempUser.username">
          </el-input>
        </el-form-item>
        <el-form-item label="姓名" required>
          <el-input type="text" v-model="tempUser.nickname">
          </el-input>
        </el-form-item>
        <el-form-item label="部门" required>
          <el-select v-model="editOrgName" placeholder="请选择">
            <el-option :value="editOrgName" style="height: auto">
                <el-tree
                  :data="rootOrg"
                  ref="org"
                  show-checkbox
                  node-key="orgCode"
                  default-expand-all
                  :highlight-current="true"
                  :default-checked-keys="[]"
                  :props="defaultProps"
                  :check-strictly="true"
                  :check-on-click-node="true"
                  @check-change="handleCheckChange"
                  @node-click="handleNodeClick">
                </el-tree>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="手机" required>
          <el-input type="text" v-model="tempUser.phone">
          </el-input>
        </el-form-item>
        <el-form-item label="密码" required>
          <el-input type="password" v-model="tempUser.password">
          </el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer" style="text-align: center;">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button v-if="dialogStatus=='create'" type="success" @click="createUser">创 建</el-button>
        <el-button type="primary" v-else @click="updateUser">修 改</el-button>
      </div>
    </el-dialog>
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="roleDialogVisible" customClass="customWidth" row-key="roleId">
      <el-table :data="rolelist" ref="roletable" border highlight-current-row max-height="500">
        <el-table-column align="center" type="selection" ></el-table-column>
        <el-table-column align="center" label="角色" prop="roleName" ></el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer" style="text-align: center;">
        <el-button @click="roleDialogVisible = false">取 消</el-button>
        <el-button type="success" @click="updateUserRole">提 交</el-button>
      </div>
    </el-dialog>
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
          pageNum: 1, //页码
          offset:0,
          pageRow: 10, //每页条数
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
          url: "/system/user/list",
          method: "get",
          params: this.listQuery
        }).then(data => {
          this.listLoading = false;
          this.list = data.list;
          this.totalCount = data.totalCount;
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
      handleSizeChange(val) {
        //改变每页数量
        this.listQuery.pageRow = val
        this.listQuery.pageNum = 1
        this.listQuery.offset=(this.listQuery.pageNum-1)*this.listQuery.pageRow
        this.handleFilter();
      },
      handleCurrentChange(val) {
        //改变页码
        this.listQuery.pageNum = val
        this.listQuery.offset=(this.listQuery.pageNum-1)*this.listQuery.pageRow
        this.getList();
      },
      handleFilter() {
        //查询事件
        this.listQuery.pageNum = 1
        this.listQuery.offset=(this.listQuery.pageNum-1)*this.listQuery.pageRow
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
        console.log("handleCheckChange")
        if (node == true) {
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
        console.log(item);
        this.editOrgCode = item.orgCode;
        this.editOrgName = item.orgName;
        this.$refs.org.setCheckedKeys([item.orgCode]);
      },
    }
  }
</script>
<style>
  .customWidth {
    width: 500px;
  }
  .p-20 {
    padding: 20px;
  }
</style>
