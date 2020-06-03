<template>
  <div class="app-container p-20">
    <el-row type="flex" justify="space-between">
      <el-col style="display:flex;justify-content:flex-end" :span="24">
        <el-button type="primary" icon="plus" @click="syncPolicy">同步权限</el-button>
        <el-button type="primary" icon="plus" @click="$router.push('newpolicy')">分配权限</el-button>
        <el-button type="danger" icon="plus" @click="batchDeletePolicy">批量删除</el-button>
      </el-col>
    </el-row>
    <el-row type="flex" justify="space-between" style="margin-top: 5px;">
      <el-col style="display:flex;justify-content:flex-start" :span="4">
        <el-date-picker v-model="listQuery.beginTime" type="datetime" placeholder="通行开始时间" style="width: 100%;margin-right: 30px;"></el-date-picker>
      </el-col>
      <el-col style="display:flex;justify-content:flex-start" :span="4">
        <el-date-picker v-model="listQuery.endTime" type="datetime" placeholder="通行结束时间" style="width: 100%;margin-right: 30px;"></el-date-picker>
      </el-col>
      <el-col style="display:flex;justify-content:flex-end" :span="16"></el-col>
    </el-row>
    <el-row type="flex" justify="space-between" style="margin-top: 5px;">
      <el-col style="display:flex;justify-content:flex-end" :span="4">
        <el-input v-model="listQuery.userGroup" placeholder="人员分组" style="width: 100%;margin-right: 30px;"></el-input>
      </el-col>
      <el-col style="display:flex;justify-content:flex-end" :span="4">
        <el-input v-model="listQuery.gatewayName" placeholder="设备名称" style="margin-right: 30px;"></el-input>
      </el-col>
      <el-col style="display:flex;justify-content:flex-end" :span="4">
        <el-input v-model="listQuery.name" placeholder="姓名" style="margin-right: 30px;"></el-input>
      </el-col>
      <el-col style="display:flex;justify-content:flex-end" :span="4">
        <el-input v-model="listQuery.no" placeholder="工号" style="margin-right: 30px;"></el-input>
      </el-col>
      <el-col style="display:flex;justify-content:flex-end" :span="4">
        <el-input v-model="listQuery.certificateNum" placeholder="身份证号" style="margin-right: 30px;"></el-input>
      </el-col>
      <el-col style="display:flex;justify-content:flex-end" :span="4">
        <el-button @click="getList" style="border:1px #1890FF solid;" icon="el-icon-search">搜索</el-button>
      </el-col>
    </el-row>
    <el-table ref="policyListTable" :data="list" v-loading.body="listLoading" element-loading-text="拼命加载中" border
      highlight-current-row style="margin-top: 30px;">
      <el-table-column align="center" type="selection"></el-table-column>
      <el-table-column align="center" label="姓名" prop="name" width="100"></el-table-column>
      <el-table-column align="center" label="性别" prop="gender" width="80">
        <template slot-scope="scope">
          <span v-if="scope.row.gender=='1'">男</span>
          <span v-else-if="scope.row.gender=='2'">女</span>
          <span v-else></span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="部门" prop="orgName"></el-table-column>
      <el-table-column align="center" label="身份证号" prop="certificateNum"></el-table-column>
      <el-table-column align="center" label="工号" prop="no" width="100"></el-table-column>
      <el-table-column align="center" label="设备名称" prop="gatewayName"></el-table-column>
      <el-table-column align="center" label="允许通行开始时间" prop="beginTime"></el-table-column>
      <el-table-column align="center" label="允许通行结束时间" prop="endTime">
        <template slot-scope="scope">
          {{scope.row.endTime| dateYMDHMSFormat}}
        </template>
      </el-table-column>
      <el-table-column align="center" label="操作">
        <template slot-scope="scope">
          <el-button type="danger" icon="delete" @click="deletePolicy(scope.$index)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="listQuery.pageNum"
      :page-size="listQuery.pageRow" :total="totalCount" :page-sizes="[10, 20, 50, 100]" layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
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
          beginTime: "",
          endTime: "",
          userGroup: "",
          gatewayName: "",
          name: "",
          no: "",
          certificateNum: "",
        },
        rootRegion: [],
        defaultProps: {
          "children": "children",
          "label": "regionName"
        },
        editRegionCode: "",
        editRegionName: "",
        rolelist: [],
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
          url: "/policy/list",
          method: "post",
          data: this.listQuery
        }).then(data => {
          console.log(data);
          this.listLoading = false;
          this.list = data.list;
          this.totalCount = data.totalCount;
        }).catch(() => {
          this.listLoading = false;
        })
      },
      syncPolicy() {
        var _vue = this;
        this.listLoading = true;
        this.api({
          url: "/policy/sync",
          method: "get"
        }).then(data => {
          _vue.getList();
        }).catch(() => {
          this.listLoading = false;
        })
      },
      deletePolicy($index) {
        let _vue = this;
        this.$confirm('<strong>确认删除此策略吗？</strong><br/><div class="text-muted">删除后，相应人员将无法通过门禁，确认删除吗？</div>', '', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          dangerouslyUseHTMLString: true,
          type: 'warning'
        }).then(() => {
          let policy = _vue.list[$index];
          _vue.api.delete("/policy/gateway", {
            data: {
              'ids': [policy.id]
            }
          }).then(() => {
            _vue.$message.info("删除任务已提交")
            _vue.getList()
          }).catch(() => {
            _vue.$message.error("删除失败")
          })
        })
      },
      batchDeletePolicy() {
        var selection = this.$refs.policyListTable.store.states.selection;
        if (selection.length == 0) {
          this.$message.error("至少选择一个权限");
          return false;
        }
        var ids = [];
        for (var i = 0; i < selection.length; i++) {
          ids.push(selection[i].id);
        }
        var _vue = this;
        this.api.delete("/policy/gateway", {
          data: {
            'ids': ids
          }
        }).then(() => {
          _vue.$message.info("删除任务已提交")
          _vue.getList()
        }).catch((e) => {
          _vue.$message.error("删除失败:"+e)
        })
      },
      handleSizeChange(val) {
        //改变每页数量
        this.listQuery.pageRow = parseInt(val);
        this.listQuery.offset = 0;
        this.handleFilter();
      },
      handleCurrentChange(val) {
        //改变页码
        this.listQuery.pageNum = parseInt(val)
        this.listQuery.offset = this.listQuery.pageNum * this.listQuery.pageRow;
        this.getList();
      },
      handleFilter() {
        //查询事件
        this.listQuery.pageNum = 1
        this.listQuery.offset = (this.listQuery.pageNum - 1) * this.listQuery.pageRow;
        this.getList()
      },
      newPolicy($index) {
        //TODO
      },
      batchDeletePolicy() {
        //TODO
      },
      delete($index) {
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
        this.listQuery.offset = 0;
        this.listQuery.regionCode = item.regionCode;
        this.listQuery.name = "";
        this.getList();
      },
    }
  }
</script>
<style>
  .p-20 {
    padding: 20px;
  }
</style>
