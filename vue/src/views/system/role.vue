<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form>
        <el-form-item>
          <el-button type="success" icon="plus" @click="showCreate">新增
          </el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-table :data="list" v-loading.body="listLoading" element-loading-text="拼命加载中" border fit
              highlight-current-row>
      <el-table-column align="center" type="selection" width="80"></el-table-column>
      <el-table-column align="center" label="角色名称" prop="roleName" width="200"></el-table-column>
      <el-table-column align="center" label="描述" prop="roleDescription" width="200"></el-table-column>
      <el-table-column align="center" label="创建时间" prop="roleCreateTime" width="200">
        <template slot-scope="scope">
          {{scope.row.roleCreateTime| dateYMDHMSFormat}}
        </template>
      </el-table-column>
      <el-table-column align="center" label="是否启用" prop="roleEnable" width="200">
        <template slot-scope="scope">
          <el-radio-group v-model="scope.row.roleEnable" @change.native="enableDisableRole(scope.$index,scope.row)">
              <el-radio :label=1>是</el-radio>
              <el-radio :label=0>否</el-radio>
          </el-radio-group>
        </template>
      </el-table-column>
      <el-table-column align="center" label="操作">
        <template slot-scope="scope">
          <div>
            <el-button type="primary" icon="edit" @click="showUpdate(scope.$index)">修改
            </el-button>
            <el-button type="primary" icon="edit" @click="assignPermissions(scope.$index)">查看权限
            </el-button>
            <el-button type="danger"
                       icon="delete"
                       @click="removeRole(scope.$index)">
              删除
            </el-button>
          </div>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible" customClass="customWidth">
      <el-form class="small-space" :model="tempRole" label-position="left" label-width="100px"
               style='width: 300px; margin-left:50px;'>
        <el-form-item label="角色名称" required>
          <el-input type="text" v-model="tempRole.roleName" style="width: 250px;">
          </el-input>
        </el-form-item>
        <el-form-item label="角色描述" required>
          <el-input type="text" v-model="tempRole.roleDescription" style="width: 250px;">
          </el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer" style="text-align: center;">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button v-if="dialogStatus=='create'" type="success" @click="createRole">创 建</el-button>
        <el-button type="primary" v-else @click="updateRole">修 改</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
  export default {
    data() {
      return {
        list: [],//表格的数据
        allPermission: [],
        listLoading: false,//数据加载等待动画
        dialogStatus: 'create',
        dialogFormVisible: false,
        textMap: {
          update: '编辑',
          create: '新建角色'
        },
        tempRole: {
          roleName: '',
          roleId: '',
          roleDescription: '',
        },
        adminName: '管理员'
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
          url: "/system/role/getAllRoles",
          method: "get"
        }).then(data => {
          this.listLoading = false;
          this.list = data.list;
        })
      },
      enableDisableRole(index,row){                               
        let _vue = this;
        let enable=row.roleEnable;
        this.$confirm((enable==1?'确定启用':'确定禁用')+row.roleName+"?", '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          showCancelButton: true,
          type: 'warning'
        }).then(() => {
          _vue.api({
            url: "/system/role/enableDisableRole",
            method: "post",
            data: {
              roleId: row.roleId,
              roleEnable:enable
            }
          }).then(() => {
            _vue.getList()
          }).catch(e => {
          })
        }).catch(()=>{
          row.roleEnable=enable==1?0:1;
        })
      },
      showCreate() {
        //显示新增对话框
        this.tempRole.roleName = '';
        this.tempRole.roleId = '';
        this.tempRole.permissions = [];
        this.dialogStatus = "create"
        this.dialogFormVisible = true
      },
      showUpdate($index) {
        let role = this.list[$index];
        this.tempRole.roleName = role.roleName;
        this.tempRole.roleId = role.roleId;
        this.tempRole.roleDescription = role.roleDescription;
        this.dialogStatus = "update"
        this.dialogFormVisible = true
      },
      assignPermissions($index) {
        let role = this.list[$index];
        this.$router.push({name: '分配权限',query: { roleId: role.roleId }})
      },
      createRole() {
        if (!this.checkRoleNameUnique()) {
          return;
        }
        //添加新角色
        this.api({
          url: "/system/role/addRole",
          method: "post",
          data: this.tempRole
        }).then(() => {
          this.getList();
          this.dialogFormVisible = false
        })
      },
      updateRole() {
        if (!this.checkRoleNameUnique(this.tempRole.roleId)) {
          return;
        }
        //修改角色
        this.api({
          url: "/system/role/updateRole",
          method: "post",
          data: this.tempRole
        }).then(() => {
          this.getList();
          this.dialogFormVisible = false
        })
      },
      checkPermissionNum() {
        //校验至少有一种权限
        if (this.tempRole.permissions.length === 0) {
          this.$message.error("请至少选择一种权限");
          return false;
        }
        return true;
      },
      checkRoleNameUnique(roleId) {
        //校验名称重复
        let roleName = this.tempRole.roleName;
        let roleDescription = this.tempRole.roleDescription;
        if (!roleName) {
          this.$message.error("请填写角色名称");
          return false;
        }
        if (!roleDescription) {
          this.$message.error("请填写角色描述");
          return false;
        }
        let roles = this.list;
        let result = true;
        for (let j = 0; j < roles.length; j++) {
          if (roles[j].roleName === roleName && (!roleId || roles[j].roleId !== roleId  )) {
            this.$message.error("角色名称已存在");
            result = false;
            break;
          }
        }
        return result;
      },
      removeRole($index) {
        let _vue = this;
        let role = _vue.list[$index];
        this.$confirm('确定删除'+role.roleName+"?", '提示', {
          confirmButtonText: '确定',
          showCancelButton: false,
          type: 'warning'
        }).then(() => {
          _vue.api({
            url: "/system/role/deleteRole",
            method: "post",
            data: {
              roleId: role.roleId
            }
          }).then(() => {
            _vue.getList()
          }).catch(e => {
          })
        })
      },
      isMenuNone(_index) {
        //判断本级菜单内的权限是否一个都没选
        let menu = this.allPermission[_index].permissions;
        let result = true;
        for (let j = 0; j < menu.length; j++) {
          if (this.tempRole.permissions.indexOf(menu[j].id) > -1) {
            result = false;
            break;
          }
        }
        return result;
      },
      isMenuAll(_index) {
        //判断本级菜单内的权限是否全选了
        let menu = this.allPermission[_index].permissions;
        let result = true;
        for (let j = 0; j < menu.length; j++) {
          if (this.tempRole.permissions.indexOf(menu[j].id) < 0) {
            result = false;
            break;
          }
        }
        return result;
      },
      checkAll(_index) {
        //点击菜单   相当于全选按钮
        let v = this;
        if (v.isMenuAll(_index)) {
          //如果已经全选了,则全部取消
          v.noPerm(_index);
        } else {
          //如果尚未全选,则全选
          v.allPerm(_index);
        }
      },
      allPerm(_index) {
        //全部选中
        let menu = this.allPermission[_index].permissions;
        for (let j = 0; j < menu.length; j++) {
          this.addUnique(menu[j].id, this.tempRole.permissions)
        }
      },
      noPerm(_index) {
        //全部取消选中
        let menu = this.allPermission[_index].permissions;
        for (let j = 0; j < menu.length; j++) {
          let idIndex = this.tempRole.permissions.indexOf(menu[j].id);
          if (idIndex > -1) {
            this.tempRole.permissions.splice(idIndex, 1);
          }
        }
      },
      addUnique(val, arr) {
        //数组内防重复地添加元素
        let _index = arr.indexOf(val);
        if (_index < 0) {
          arr.push(val);
        }
      },
      checkRequired(_perm, _index) {
        //本方法会在勾选状态改变之后触发
        let permId = _perm.id;
        if (this.tempRole.permissions.indexOf(permId) > -1) {
          //选中事件
          //如果之前未勾选本权限,现在勾选完之后,tempRole里就会包含本id
          //那么就要将必选的权限勾上
          this.makeReuqiredPermissionChecked(_index);
        } else {
          //取消选中事件
          if (_perm.requiredPerm === 1) {
            //如果是必勾权限,就把本菜单的权限全部移出
            //(其实也可以提示用户本权限是菜单里的必选,请先取消勾选另外几个权限,交互太麻烦,此处就直接全部取消选中了)
            this.noPerm(_index);
          }
        }
      },
      makeReuqiredPermissionChecked(_index) {
        //将本菜单必选的权限勾上
        let menu = this.allPermission[_index].permissions;
        for (let j = 0; j < menu.length; j++) {
          let perm = menu[j];
          if (perm.requiredPerm === 1) {
            //找到本菜单的必选权限,将其勾上
            this.addUnique(perm.id, this.tempRole.permissions)
          }
        }
      }
    }
  }
</script>
<style>
  .requiredPerm {
    color: #ff0e13;
  }
  .customWidth {
    width: 500px;
  }
</style>
