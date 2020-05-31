<template>
  <div class="app-container">
    <div ref="regionLayout" class="tree">
      <el-row type="flex" justify="space-around">
        <el-col style="display:flex;justify-content:flex-start" :span="12">
          <el-button type="primary" icon="plus" @click="syncRegion" class="m-l-10">同步区域</el-button>
        </el-col>
      </el-row>
      <el-tree :data="rootRegion" ref="region" node-key="regionCode" :default-expanded-keys="defaultExpandedKeys" :highlight-current="true"
        :default-checked-keys="[]" :props="defaultProps" :check-strictly="true" :check-on-click-node="true"
        @check-change="handleCheckChange" @node-click="handleNodeClick">
      </el-tree>
    </div>
    <div class="main">
      <el-row type="flex" justify="space-between">
        <el-col :span="8">
          <el-input v-model="listQuery.name" placeholder="请输入设备名称进行搜索">
            <el-button @click="getList" style="border:1px #1890FF solid;" slot="append" icon="el-icon-search">搜索</el-button>
          </el-input>
        </el-col>
        <el-col style="display:flex;justify-content:flex-end" :span="8">
          <el-button type="primary" icon="plus" @click="syncGateway">设备同步</el-button>
        </el-col>
      </el-row>
      <el-table :data="list" v-loading.body="listLoading" element-loading-text="拼命加载中" :row-style="{height:5+'px'}"
        :cell-style="{padding:5+'px'}" border highlight-current-row style="margin-top: 30px;">
        <el-table-column align="center" type="selection" width="80"></el-table-column>
        <el-table-column align="center" label="设备名称" prop="name"></el-table-column>
        <el-table-column align="center" label="厂商" prop="firm"></el-table-column>
        <el-table-column align="center" label="最后修改时间" prop="updateTime">
          <template slot-scope="scope">
            {{scope.row.updateTime| dateYMDHMSFormat}}
          </template>
        </el-table-column>
        <el-table-column align="center" label="操作" width="300">
          <template slot-scope="scope">
            <el-button type="primary" icon="edit" @click="showUpdate(scope.$index)">修改</el-button>
            <el-button type="primary" icon="edit" @click="open(scope.$index)">开闸</el-button>
            <el-button type="danger" icon="delete" @click="remove(scope.$index)">删除
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
          pageNum: 1, //页码
          pageRow: 10, //每页条数
          regionCode: "",
          name: "",
        },
        rootRegion: [],
        defaultExpandedKeys:[],
        defaultProps: {
          "children": "children",
          "label": "regionName"
        },
        editRegionCode: "",
        editRegionName: "",
        rolelist: [],
        regionHeight: '',
      }
    },
    created() {
      this.getList();
      this.getRootRegion();
    },
    mounted() {
      this.regionHeight=`${document.documentElement.clientHeight}` - 100
      const that = this
      window.onresize = () => {
        return (() => {
          var regionHeight = `${document.documentElement.clientHeight}` - 100
          that.regionHeight = regionHeight
        })()
      }
    },
    watch: {
      // 如果 `clientHeight` 发生改变，这个函数就会运行
      regionHeight(o, n) {
        this.changeFixed(this.regionHeight)
      }
    },
    methods: {
      changeFixed(clientHeight) { //动态修改样式
        console.log(clientHeight);
        this.$refs.regionLayout.style.height = clientHeight + 'px';
      },
      getList() {
        this.listLoading = true;
        this.api({
          url: "/gateway/list",
          method: "post",
          data: this.listQuery
        }).then(data => {
          this.listLoading = false;
          this.list = data.list;
          this.totalCount = data.totalCount;
        }).catch(() => {
          this.listLoading = false;
        })
      },
      getRootRegion() {
        var _vue = this;
        this.api({
          url: "/system/region/getRootRegion",
          method: "get"
        }).then(data => {
          var rootRegion = [];
          var defaultExpandedKeys=[];
          if (data != null) {
            rootRegion.push(data);
            while(data.children!=null && data.children.length<3){
              data=data.children[0];
            }
            if(data!=null){
              defaultExpandedKeys.push(data.regionCode);
            }
          }
          _vue.defaultExpandedKeys=defaultExpandedKeys;
          _vue.rootRegion = rootRegion;
        })
      },
      syncRegion() {
        var _vue = this;
        this.listLoading = true;
        this.api({
          url: "/system/region/sync",
          method: "get"
        }).then(data => {
          _vue.getRootRegion();
        }).catch(() => {
          this.listLoading = false;
        })
      },
      syncGateway() {
        var _vue = this;
        this.listLoading = true;
        this.api({
          url: "/gateway/sync",
          method: "get"
        }).then(data => {
          _vue.getList();
        }).catch(() => {
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
      showUpdate($index) {
        //TODO
      },
      open($index) {
        //TODO
      },
      remove($index) {
        //TODO
      },
      handleCheckChange(item, node, self) {
        console.log("handleCheckChange:" + node)
        if (node == true) {
          console.log(item);
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
        console.log("handleNodeClick");
        this.editRegionCode = item.regionCode;
        this.editRegionName = item.regionName;
        this.$refs.region.setCheckedKeys([item.regionCode]);
        this.listQuery.pageNum = 1;
        this.listQuery.regionCode = item.regionCode;
        this.listQuery.name = "";
        this.getList();
      },
    }
  }
</script>
<style scoped>
  .tree {
    position: fixed;
    width: 300px;
    height: 100%;
    overflow-y: scroll;
    overflow-x: scroll;
    border-right: 1px solid #EEEEEE;
    padding-top: 20px;
  }

  .el-tree {
    min-width: 100%;
    font-size: 14px;
    display: inline-block;
  }

  .main {
    margin-left: 300px;
    padding: 20px;
  }
</style>
