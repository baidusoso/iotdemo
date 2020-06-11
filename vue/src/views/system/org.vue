<template>
  <div class="app-container p-20">
    <div class="toolbar">
      <el-button type="primary" icon="edit" @click="showCreate()">新增</el-button>
      <el-button type="primary" icon="edit" @click="showUpdate()">修改</el-button>
      <el-button type="primary" icon="edit" @click="syncOrg()">同步</el-button>
      <el-button type="danger" icon="edit" @click="deleteOrg()">删除</el-button>
    </div>
    <div v-loading.body="listLoading" element-loading-text="拼命加载中" class="tree">
      <el-tree :data="rootOrg" ref="org" show-checkbox node-key="orgCode" :default-expanded-keys="defaultExpandedKeys"
        :highlight-current="true" :default-checked-keys="[]" :props="defaultProps" :check-strictly="true"
        :check-on-click-node="true" @check-change="handleCheckChange" @node-click="handleNodeClick">
      </el-tree>
      <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible" customClass="customWidth">
        <el-form class="small-space" label-position="left" label-width="100px" style='width: 300px; margin-left:50px;'>
          <el-form-item label="上级部门" v-if="editOrgCode!='' && dialogStatus=='create'">
            <span>{{editOrgName}}</span>
          </el-form-item>
          <el-form-item label="部门名称" required>
            <el-input type="text" v-model="tempOrgName" style="width: 250px;"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer" style="text-align: center;">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="success" @click="createOrg">确定</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>
<script>
  export default {
    data() {
      return {
        listLoading: false, //数据加载等待动画
        rootOrg: [],
        defaultExpandedKeys:[],
        defaultProps: {
          "children": "children",
          "label": "orgName"
        },
        currentOrgCode: 0,
        dialogStatus: 'create',
        dialogFormVisible: false,
        textMap: {
          update: '编辑部门',
          create: '新建部门'
        },
        editOrgCode: "",
        editOrgName: "",
        tempOrgName: "",
      }
    },
    created() {
      this.getRootOrg();
    },
    methods: {
      getRootOrg() {
        var _vue = this;
        this.listLoading=true;
        this.api({
          url: "/system/org/getRootOrg",
          method: "get"
        }).then(data => {
          var rootOrg = [];
          var defaultExpandedKeys=[];
          if (data != null) {
            rootOrg.push(data);
            defaultExpandedKeys.push(data.orgCode);
          }
          _vue.rootOrg = rootOrg;
          _vue.defaultExpandedKeys=defaultExpandedKeys;
          this.listLoading=false;
          console.log(data);
        }).catch(()=>{
          this.listLoading=false;
        })
      },
      showCreate() {
        if (this.editOrgCode == null || this.editOrgCode == "") {
          this.$message.error("请选择一个部门");
          return false;
        }
        this.dialogStatus = "create";
        this.dialogFormVisible = true;
      },
      showUpdate() {
        if (this.editOrgCode == null || this.editOrgCode == "") {
          this.$message.error("请选择一个部门");
          return false;
        }
        this.dialogStatus = "update";
        this.editOrgName = this.editOrgName;
        this.dialogFormVisible = true;
      },
      syncOrg(){
        var _vue = this;
        this.listLoading=true;
        this.api({
          url: "/system/org/syncOrg",
          method: "get"
        }).then(data => {
          _vue.getRootOrg();
        }).catch(()=>{
          this.listLoading=false;
        })
      },
      createOrg() {
        console.log("createOrg");
        if (this.editOrgName == "") {
          this.$message.error("请输入部门名称");
          return false;
        }
        var _vue = this;
        var url="/system/org/addOrg";
        if(this.dialogStatus == "update"){
          url="/system/org/updateOrg";
        }
        this.api({
              url: url,
                method: "post",
                data: {
                  "orgName": this.tempOrgName,
                  "parentOrgCode": (this.editOrgCode == "" ? "0" : this.editOrgCode)
                }
              }).then(data => {
              _vue.editOrgCode = "";
              _vue.editOrgName = "";
              _vue.dialogFormVisible = false;
              _vue.tempOrgName = "";
              _vue.getRootOrg();
            })
          },
          updateOrg() {
            console.log("updateOrg");
            // if(this.tempDepartmentName==""){
            //   this.$message.error("请输入部门名称");
            //   return false;
            // }
            // var _vue=this;
            // this.api({
            //   url: "/system/org/updateDepartment",
            //   method: "post",
            //   data:{
            //     "departmentName":this.tempDepartmentName,
            //     "departmentId":this.editOrgCode
            //   }
            // }).then(data => {
            //   _vue.editOrgCode="";
            //   _vue.editOrgName="";
            //   _vue.dialogFormVisible = false;
            //   _vue.tempDepartmentName="";
            //   _vue.getRootOrg();
            // })
          },
          deleteOrg() {
            if (this.editOrgCode == null || this.editOrgCode == "") {
              this.$message.error("请选择一个部门");
              return false;
            }
            var _vue = this;
            this.$confirm('确定删除"' + this.editOrgName + '"这个部门?', '提示', {
              confirmButtonText: '确定',
              showCancelButton: false,
              type: 'warning'
            }).then(() => {
              _vue.api({
                url: "/system/org/deleteOrg",
                method: "post",
                data: {
                  orgCode: _vue.editOrgCode
                }
              }).then(() => {
                _vue.editOrgCode = "";
                _vue.editOrgName = "";
                _vue.getRootOrg();
              }).catch(e => {})
            })
          },
          handleCheckChange(item, node, self) {
            console.log("handleCheckChange")
            if (node == true) {
              this.editOrgCode = item.orgCode;
              this.editOrgName = item.orgName;
              this.$refs.org.setCheckedKeys([item.orgCode]);
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
      },
    }
</script>
<style>
  .tree {
    overflow-y: auto;
    overflow-x: scroll;
    height: 500px;
    width:600px;
    border: 0px solid blue;
  }

  .toolbar {
    width: 500px;
    text-align: center;
    margin-bottom: 20px;
  }

  .customWidth {
    width: 500px;
  }
  .p-20 {
    padding: 20px;
  }
</style>
