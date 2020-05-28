<template>
  <div class="app-container">
    <div class="toolbar">
      <el-button type="primary" icon="edit" @click="showCreate()">新增</el-button>
      <el-button icon="edit" @click="showUpdate()">修改</el-button>
      <el-button icon="edit" @click="syncRegion()">同步</el-button>
      <el-button type="danger" icon="edit" @click="deleteRegion()">删除</el-button>
    </div>
    <div v-loading.body="listLoading" element-loading-text="拼命加载中" class="tree">
      <el-tree
        :data="rootRegion"
        ref="region"
        show-checkbox node-key="regionCode"
        :highlight-current="true"
        :default-checked-keys="[]"
        :default-expanded-keys="defaultExpandedKeys"
        :props="defaultProps"
        :check-strictly="true"
        :check-on-click-node="true"
        @check-change="handleCheckChange"
        @node-click="handleNodeClick">
      </el-tree>
      <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible" customClass="customWidth">
        <el-form class="small-space" label-position="left" label-width="100px" style='width: 300px; margin-left:50px;'>
          <el-form-item label="上级区域" v-if="editRegionCode!='' && dialogStatus=='create'">
            <span>{{editRegionName}}</span>
          </el-form-item>
          <el-form-item label="区域名称" required>
            <el-input type="text" v-model="tempRegionName" style="width: 250px;"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer" style="text-align: center;">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="success" @click="createRegion">确定</el-button>
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
        rootRegion: [],
        defaultExpandedKeys:[],
        defaultProps: {
          "children": "children",
          "label": "regionName"
        },
        currentRegionCode: 0,
        dialogStatus: 'create',
        dialogFormVisible: false,
        textMap: {
          update: '编辑区域',
          create: '新建区域'
        },
        editRegionCode: "",
        editRegionName: "",
        tempRegionName: "",
      }
    },
    created() {
      this.getRootRegion();
    },
    methods: {
      getRootRegion() {
        var _vue = this;
        this.listLoading=true;
        this.api({
          url: "/system/region/getRootRegion",
          method: "get"
        }).then(data => {
          var rootRegion = [];
          var defaultExpandedKeys=[];
          if (data != null) {
            rootRegion.push(data);
            defaultExpandedKeys.push(data.regionCode);
          }
          console.log(defaultExpandedKeys);
          _vue.defaultExpandedKeys=defaultExpandedKeys;
          _vue.rootRegion = rootRegion;
          this.listLoading=false;
          console.log(data);
        }).catch(()=>{
          this.listLoading=false;
        })
      },
      showCreate() {
        if (this.editRegionCode == null || this.editRegionCode == "") {
          this.$message.error("请选择一个区域");
          return false;
        }
        this.dialogStatus = "create";
        this.dialogFormVisible = true;
      },
      showUpdate() {
        if (this.editRegionCode == null || this.editRegionCode == "") {
          this.$message.error("请选择一个区域");
          return false;
        }
        this.dialogStatus = "update";
        this.editRegionName = this.editRegionName;
        this.dialogFormVisible = true;
      },
      syncRegion(){
        var _vue = this;
        this.listLoading=true;
        this.api({
          url: "/system/region/syncRegion",
          method: "get"
        }).then(data => {
          if(data.code==100){
            _vue.getRootRegion();
          }else{
            this.$message.error(data.msg);
            this.listLoading=false;
          }
        }).catch(()=>{
          this.listLoading=false;
        })
      },
      createRegion() {
        console.log("createRegion");

          },
          deleteOrg() {

          },
          handleCheckChange(item, node, self) {
            console.log("handleCheckChange")
            if (node == true) {
              this.editRegionCode = item.regionCode;
              this.editRegionName = item.regionName;
              this.$refs.region.setCheckedKeys([item.regionCode]);
            } else {
              if (this.editRegionCode == item.regionCode) {
                this.$refs.region.setCheckedKeys([item.regionCode]);
              }
            }
          },
          handleNodeClick(item, node, self) {
            console.log(item);
            this.editRegionCode = item.regionCode;
            this.editRegionName = item.regionName;
            this.$refs.region.setCheckedKeys([item.regionCode]);
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
    margin-top: 30px;
    width: 500px;
    text-align: center;
  }

  .customWidth {
    width: 500px;
  }
</style>
