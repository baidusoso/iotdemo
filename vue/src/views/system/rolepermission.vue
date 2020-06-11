<template>
  <div class="app-container p-20">
    <h2>给角色“{{roleInfo.roleName}}”分配权限</h2>
    <div v-loading.body="listLoading" class="tree">
      <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange" style="padding-left: 24px;">全选</el-checkbox>
      <el-tree :data="allPermissions" :render-content="renderContent" ref="permissions" show-checkbox node-key="id"
        default-expand-all :default-checked-keys="roleInfo.permissionIds" :props="defaultProps" @check-change="handleCheckedPermissionsChange">
      </el-tree>
    </div>
    <div class="toolbar">
      <el-button icon="edit" @click="reset()">重试</el-button>
      <el-button icon="edit" @click="goback()">返回</el-button>
      <el-button type="primary" icon="edit" @click="save()">保存</el-button>
    </div>
  </div>
</template>
<script>
  export default {
    data() {
      return {
        isIndeterminate: false,
        roleInfo: {}, //表格的数据
        allPermissions: [],
        firstLevelPermissions: [],
        leafPermissionCount: 0,
        checkAll: false,
        listLoading: false, //数据加载等待动画
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
        roleId: '',
        roleName: '',
        defaultProps: {
          "children": "permissions",
          "label": "permissionName"
        },
      }
    },
    created() {
      let roleId = this.$route.query.roleId;
      this.findRole(roleId);
    },
    methods: {
      handleCheckAllChange(val) {
        this.$refs.permissions.setCheckedKeys(val ? this.firstLevelPermissions : []);
        this.isIndeterminate = false;
      },
      handleCheckedPermissionsChange(value) {
        var checkedPermissions = this.$refs.permissions.getCheckedKeys(true);
        console.log(checkedPermissions);
        this.checkAll = (checkedPermissions.length == this.leafPermissionCount);
        this.isIndeterminate = checkedPermissions.length > 0 && !this.checkAll;
      },
      reset() {
        this.$refs.permissions.setCheckedKeys(this.roleInfo.permissionIds);
      },
      goback() {
        this.$router.push({
          path: 'role'
        })
      },
      save() {
        var checkedPermissions = this.$refs.permissions.getCheckedKeys(true);
        if (checkedPermissions.length==0) {
          this.$message.error("至少选择一个权限");
          return false;
        }
        let _vue = this;
        this.api({
          url: "/system/role/updateRolePermission",
          method: "post",
          data: {
            roleId: this.roleInfo.roleId,
            permissionIds: checkedPermissions,
          }
        }).then(data => {
          this.$message({
            message: "保存成功",
            type: 'success',
            duration: 1 * 1000,
            onClose: () => {
              _vue.goback();
            }
          })
        })
      },
      getAllPermisson() {
        //查询所有权限
        this.api({
          url: "/system/role/listAllPermission",
          method: "get"
        }).then(data => {
          this.allPermissions = data.list;
          console.log(this.allPermissions);
          var firstLevelPermissions = [];
          var leafPermissionCount = 0;
          for (var i = 0; i < this.allPermissions.length; i++) {
            firstLevelPermissions.push(this.allPermissions[i].id);
            leafPermissionCount += this.allPermissions[i].permissions.length;
          }
          this.firstLevelPermissions = firstLevelPermissions;
          this.leafPermissionCount = leafPermissionCount;
          this.listLoading = false;
          setTimeout(() => {
            this.changeCss();
          }, 100);
        })
      },
      findRole(roleId) {
        //查询列表
        this.listLoading = true;
        this.api({
          url: "/system/role/findRole",
          method: "post",
          data: {
            roleId: roleId,
          }
        }).then(data => {
          this.roleInfo = data;
          this.roleCheckedPermissions = this.roleInfo.permissionIds;
          console.log(this.roleCheckedPermissions);
          this.getAllPermisson();
        })
      },
      changeCss() {
        console.log("changeCss");
        // 找到之前做标记的class
        var classDomList = document.getElementsByClassName('especially')
        // 改变这几个样式
        for (var i = 0; i < classDomList.length; i++) {
          classDomList[i].parentNode.style.cssText = 'float: left'
          classDomList[i].parentNode.className = 'el-tree-node__content option-wrapper'
          classDomList[i].parentNode.parentNode.parentNode.style.marginLeft = '70px'
        }
        var checkedPermissions = this.$refs.permissions.getCheckedKeys();
        this.checkAll = (checkedPermissions.length == this.firstLevelPermissions.length + this.leafPermissionCount);
        this.isIndeterminate = checkedPermissions.length > 0 && !this.checkAll;
      },
      renderContent(h, {
        node,
        data,
        store
      }) {
        //console.log(node);
        let classname = '';
        // 由于项目中有二级菜单也有三级菜单，就要在此做出判断。
        if (node.level == 2) {
          classname = 'especially';
        }
        return ( <
          div class = {
            classname
          } > {
            node.label
          } <
          /div>);
        },
      }
    }
</script>
<style>
  .tree {
    overflow-y: auto;
    overflow-x: scroll;
    /*width:200px;*/
    border: 0px solid blue;
  }

  .toolbar {
    margin-top: 30px;
    width: 500px;
    text-align: center;
  }

  .option-wrapper {
    padding: 0 !important;
  }
  .p-20 {
    padding: 20px;
  }
</style>
