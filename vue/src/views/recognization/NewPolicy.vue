<template>
  <div class="page-body">
    <el-row class="m-t-20">
      <el-col :span="22" :offset="2">
        <el-steps :active="currentStep" align-center="">
          <el-step title="步骤1" description="选择门禁设备"></el-step>
          <el-step title="步骤2" description="选择授权人员"></el-step>
          <el-step title="步骤3" description="确认门禁策略"></el-step>
          <el-step title="完成" description=""></el-step>
        </el-steps>
      </el-col>
    </el-row>
    <el-row class="m-t-20">
      <div v-if="currentStep === 1" class="text-center m-t-50 m-b-20">
        <el-col :span="8" style="display:flex;justify-content:flex-end">
          <div ref="regionLayout" class="tree">
            <el-tree :data="rootRegion" ref="region" node-key="regionCode" :default-expanded-keys="defaultExpandedKeys"
              :highlight-current="true" :default-checked-keys="[]" :check-strictly="true" :check-on-click-node="true"
              @check-change="handleCheckChange" @node-click="handleNodeClick">
            </el-tree>
          </div>
        </el-col>
        <el-col :span="16" style="display:flex;justify-content:flex-start">
          <el-transfer class="m-t-20 m-b-30 transfer-class" style="display: inline-block; text-align: left;" filterable
            :titles="['备选门禁', '已选门禁']" :button-texts="['移除', '添加']" :filter-method="filterGateway" filter-placeholder="输入门禁名称搜索"
            v-model="selectedGateway" :data="gatewayList">
          </el-transfer>
        </el-col>
      </div>
      <div v-if="currentStep === 2" class="text-center m-t-50 m-b-20">
        <el-col :span="8" style="display:flex;justify-content:flex-end">
          <div id="org" class="tree" ref="orgLayout">
            <el-tree :data="rootOrg" ref="org" node-key="orgCode" :default-expanded-keys="orgDefaultExpandedKeys"
              :highlight-current="true" :default-checked-keys="[]" :check-strictly="true" :check-on-click-node="true"
              @check-change="handleOrgCheckChange" @node-click="handleOrgNodeClick">
            </el-tree>
          </div>
        </el-col>
        <el-col :span="16" style="display:flex;justify-content:flex-start">
          <el-transfer class="m-t-20 m-b-30 transfer-class" style="display: inline-block; text-align: left;" filterable
            :titles="['选择被分配人员', '已选择人员']" :button-texts="['移除', '添加']" :filter-method="filterAccount"
            filter-placeholder="输入姓名搜索" v-model="selectedAccount" :data="accountList">
          </el-transfer>
        </el-col>
      </div>
      <div v-if="currentStep === 3" class="text-center m-t-50 m-b-20">
        <el-col :span="22" :offset="2">
          <div class="flex justify-content-center">
            <el-form>
              <el-form-item>
                <span class="text-bold text-sm text-dark m-r-10" style="vertical-align: middle;">选择允许通行的时间段：</span>
                <el-date-picker v-model="datetime" type="datetimerange" range-separator="至" start-placeholder="开始时间"
                  end-placeholder="结束时间">
                </el-date-picker>
              </el-form-item>
            </el-form>
          </div>
        </el-col>
        <el-col :span="22" :offset="2">
          <el-transfer class="m-t-20 m-b-30 tp" style="display: inline-block; text-align: left;" :titles="['已选择设备', '已选择人员']"
            v-model="selectedPreview" :format="{ noChecked: ' ', hasChecked: ' ' }" :data="preview">
          </el-transfer>
        </el-col>
      </div>
      <div v-else-if="currentStep === 4" class="text-center">
        <el-col :span="22" :offset="2">
          <div class="flex justify-content-center align-items-center">
            <span class="flex justify-content-center align-items-center" style="background: #36C626; margin-top: 110px; width: 72px; height: 72px; padding: 16px; font-size: 40px; color: white; border-radius: 50%;">
              <i class="el-icon-check"></i>
            </span>
          </div>
          <div class="text-center m-b-30 m-t-30">
            <h4 class="text-dark text-lg m-b-30">新增门禁策略成功！</h4>
          </div>
        </el-col>
      </div>
    </el-row>
    <el-row class="m-t-20">
      <div class="text-center">
        <el-button v-if="currentStep === 4" size="medium" style="width: 120px;" type="primary" @click="$router.push('policy')">返回列表</el-button>
        <el-button v-if="currentStep >= 2 && currentStep <= 3" size="medium" style="width: 120px; margin-right: 50px;"
          type="default" @click="currentStep -= 1">上一步</el-button>
        <el-button v-if="currentStep <= 3" type="primary" size="medium" style="width: 120px;" @click="nextStep">{{currentStep === 3 ? "提 交" : "下一步"}}</el-button>
      </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        currentStep: 1,
        rootRegion: [],
        defaultExpandedKeys: [],
        gatewayList: [],
        _gateways: {},
        listQuery: {
          pageNum: 1, //页码
          pageRow: 10000, //每页条数
          regionCode: "",
          name: "",
        },
        selectedGateway: [],

        rootOrg: [],
        orgDefaultExpandedKeys: [],
        accountList: [],
        _accounts: {},
        selectedAccount: [],

        datetime: [],
        priview: [],
        selectedPreview: [],
      }
    },
    created() {
      this.getRootRegion();
      this.getGatewayList();
    },
    methods: {
      getGatewayList() {
        this.listLoading = true;
        this.api({
          url: "/gateway/list",
          method: "post",
          data: this.listQuery
        }).then(data => {
          // this.listLoading = false;
          // this.gatewayList = data.list;
          // this.totalCount = data.totalCount;
          this._gateways = {};
          this.gatewayList = data.list.map(item => {
            this._gateways[item.doorCode] = item;
            return {
              key: item.doorCode,
              label: item.name,
              disabled: false
            };
          });
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
          var defaultExpandedKeys = [];
          if (data != null) {
            rootRegion.push(data);
            while (data.children != null && data.children.length < 3) {
              data = data.children[0];
            }
            if (data != null) {
              defaultExpandedKeys.push(data.regionCode);
            }
          }
          _vue.defaultExpandedKeys = defaultExpandedKeys;
          _vue.rootRegion = rootRegion;
        })
      },
      handleCheckChange(item, node, self) {
        console.log("handleCheckChange:" + node)
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
        console.log("handleNodeClick");
        this.editRegionCode = item.regionCode;
        this.editRegionName = item.regionName;
        this.$refs.region.setCheckedKeys([item.regionCode]);
        this.listQuery.pageNum = 1;
        this.listQuery.regionCode = item.regionCode;
        this.listQuery.name = "";
        this.getGatewayList();
      },
      getRootOrg() {
        var _vue = this;
        this.api({
          url: "/system/org/getRootOrg",
          method: "get"
        }).then(data => {
          var rootOrg = [];
          var orgDefaultExpandedKeys = [];
          if (data != null) {
            rootOrg.push(data);
            while (data.children != null && data.children.length < 3) {
              data = data.children[0];
            }
            if (data != null) {
              orgDefaultExpandedKeys.push(data.orgCode);
            }
          }
          _vue.orgDefaultExpandedKeys = orgDefaultExpandedKeys;
          _vue.rootOrg = rootOrg;
        })
      },
      getAccountList() {
        //查询列表
        this.listLoading = true;
        this.api({
          url: "/account/list",
          method: "post",
          data: this.listQuery
        }).then(data => {
          this._accounts = {};
          this.accountList = data.list.map(item => {
            this._accounts[item.id] = item;
            return {
              key: item.id,
              label: item.usergroup === "厂内人员" ? `${item.name} - ${item.username}` : `${item.name} - ${item.certificateNum}`,
              disabled: false
            };
          });
        }).catch(() => {
          this.listLoading = false;
        })
      },
      handleOrgCheckChange(item, node, self) {
        console.log("handleOrgCheckChange:" + node)
        if (node == true) {
          this.editorgCode = item.orgCode;
          this.editOrgName = item.orgName;
          this.$refs.org.setCheckedKeys([item.orgCode]);
          this.tempUser.orgCode = this.editOrgCode;
        } else {
          if (this.editOrgCode == item.orgCode) {
            this.$refs.org.setCheckedKeys([item.orgCode]);
          }
        }
      },
      handleOrgNodeClick(item, node, self) {
        console.log("handleNodeClick");
        this.$refs.org.setCheckedKeys([item.orgCode]);
        this.listQuery.pageNum = 1;
        this.listQuery.orgId = item.orgCode;
        this.listQuery.name = "";
        this.listQuery.no = "";
        this.listQuery.mobile = "";
        this.listQuery.certificateNum = "";
        this.getAccountList();
      },
      filterGateway(query, gateway) {
        return `${gateway.key}`.indexOf(query) !== -1 || gateway.label.indexOf(query) !== -1;
      },
      filterAccount(query, account) {
        return `${account.key}`.indexOf(query) !== -1 || account.label.indexOf(query) !== -1;
      },
      nextStep() {
        let validators = ['', this.validateFirstStep, this.validateSecondStep, this.validateThirdStep];
        let result = validators[this.currentStep]();
        if (result === true) {
          if (this.currentStep === 1) {
            this.getAccountList();
            this.getRootOrg()
            this.currentStep++;
          } else if (this.currentStep === 2) {
            this.getPreview();
            this.currentStep++;
          } else if (this.currentStep === 3) {
            this.submit();
          }
        } else {
          this.$message.error({
            message: result
          });
        }
      },
      validateFirstStep() {
        if (this.selectedGateway.length === 0) {
          return "请至少选择一个门禁！";
        }
        return true;
      },
      validateSecondStep() {
        if (this.selectedAccount.length === 0) {
          return "请至少选择一个人员！";
        }
        return true;
      },
      validateThirdStep() {
        if (!this.datetime || this.datetime.length < 2) {
          return "请选择允许通行的时间段";
        }
        if (this.datetime[1] < new Date()) {
          return "结束时间不能晚于当前时间"
        }
        return true;
      },
      getPreview() {
        console.log("getPreview");
        let selectedUser = this.selectedAccount;
        selectedUser = selectedUser.map(item => {
          return {
            key: this._accounts[item].id,
            label: this._accounts[item].name,
            disabled: true
          };
        });
        let selectedGateway = this.selectedGateway.map(item => {
          return {
            key: this._gateways[item].doorCode,
            label: this._gateways[item].name,
            disabled: true
          }
        });
        this.preview = selectedGateway.concat(selectedUser);
        this.selectedPreview = selectedUser.map(item => item.key);
      },
      submit() {
        let users = this.selectedAccount.map(item => this._accounts[item]);
        let gateways = this.selectedGateway.map(item => this._gateways[item]);
        let datetime = this.datetime.map(item => this.formatDateTime(item));
        let policies = [];
        users.forEach(user => {
          gateways.forEach(gateway => {
            policies.push({
              userId: user.id,
              gatewayId: gateway.doorCode,
              startAt: datetime[0],
              endAt: datetime[1]
            });
          });
        });
        this.api({
          url: "/policy/gateway",
          method: "post",
          data: policies
        }).then(response => {
          this.currentStep++;
        });
      },
      formatDateTime(date) {
        var y = date.getFullYear();
        var m = date.getMonth() + 1;
        m = m < 10 ? ('0' + m) : m;
        var d = date.getDate();
        d = d < 10 ? ('0' + d) : d;
        var h = date.getHours();
        h = h < 10 ? ('0' + h) : h;
        var minute = date.getMinutes();
        minute = minute < 10 ? ('0' + minute) : minute;
        var second = date.getSeconds();
        second = second < 10 ? ('0' + second) : second;
        return y + '-' + m + '-' + d + ' ' + h + ':' + minute + ':' + second;
      }
    },
  }
</script>

<style>
  .tree {
    width: 300px;
    height: 399px;
    overflow-y: scroll;
    overflow-x: scroll;
    padding-top: 20px;
  }

  .el-tree {
    min-width: 100%;
    font-size: 14px;
    display: inline-block;
  }
</style>
