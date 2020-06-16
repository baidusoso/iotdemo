<template>
  <el-dialog
    :title="title"
    :visible.sync="visible"
    width="45%"
    :before-close="handleCancel">
    <div class="newsup-add-body">
      <div class="newsup-add-header">
        <treeselect
          placeholder="请选择部门"
          v-model="filterDep"
          :multiple="false"
          :options="deptList"/>
      </div>
      <div class="newsup-add-header m-t-10">
        <el-select style="width: 50%;" :disabled='filterDep === ""' filterable clearable v-model="filterPosition"
                   placeholder="请选择岗位">
          <el-option
            v-for="item in positionList.filter(item=>item.orgId == filterDep)"
            :key="item.id"
            :label="item.name"
            :value="item.id">
          </el-option>
        </el-select>
        <el-input
          style="width:50%;"
          @keydown.enter.native="getUserData"
          placeholder="请输入关键字搜索"
          v-model="filterQuery">
          <i slot="suffix" class="el-input__icon el-icon-search" />
        </el-input>
      </div>
      <el-row class="m-t-10" style="height: 300px">
        <el-scrollbar class="page-component__scroll">
          <el-table
            :ref="multiple ? 'multipleTable' : 'singleTable'"
            :data="staffList"
            :highlight-current-row="!multiple"
            tooltip-effect="dark"
            style="width: 100%"
            @current-change="handleSelectionChange"
            @selection-change="handleSelectionChange">
            <el-table-column
              v-if="multiple"
              type="selection"
              width="55">
            </el-table-column>
            <el-table-column
              prop="name"
              label="姓名"
              width="120">
            </el-table-column>
            <el-table-column
              prop="no"
              label="工号"
              width="120">
            </el-table-column>
            <el-table-column
              prop="dep"
              header-align="center"
              label="部门-岗位"
              show-overflow-tooltip>
              <template slot-scope="scope">
                <ul>
                  <li v-for="depPos in scope.row.depPos">{{depPos}}</li>
                </ul>
              </template>
            </el-table-column>
          </el-table>
        </el-scrollbar>
      </el-row>
    </div>
    <span slot="footer" class="dialog-footer">
        <el-button @click="handleCancel">取 消</el-button>
        <el-button type="primary" @click="handleChange">确 定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  import Treeselect from '@riophae/vue-treeselect'
  import '@riophae/vue-treeselect/dist/vue-treeselect.css'

  export default {
    name: "UserPicker",
    components: {
      Treeselect
    },
    props: {
      visible: Boolean,
      title: String,
      multiple: Boolean,
    },
    data() {
      return {
        deptList: [],
        positionList: [],
        staffList: [],
        filterDep: null,
        filterPosition: '',
        filterQuery: '',
        selection: null,
      }
    },
    watch: {
      filterDep() {
        this.filterPosition = ''
        this.getUserData()
      },
      filterPosition() {
        this.getUserData()
      },
      filterQuery() {
        this.getUserData()
      },
    },
    mounted() {
      this.getDepData()
      this.getPositionData()
    },
    methods: {
      handleSelectionChange(val) {
        this.selection = val
      },
      handleChange() {
        this.$emit('confirm', this.selection || (this.multiple ? [] : null))
        this.handleCancel()
      },
      handleCancel() {
        this.$emit('cancel')
        this.staffList = []
        this.filterDep = ''
        this.filterPosition = ''
        this.filterQuery = ''
      },
      getDepData() {
        this.api.get('/system/org/getRootOrg').then((params) => {
          // if (params.body.code === 200) {
            function process(nodes) {
              for (let i = 0; i < nodes.length; i++) {
                nodes[i].id = nodes[i].orgCode
                nodes[i].label = nodes[i].orgName
                nodes[i].isDefaultExpanded = true
                if (nodes[i].children instanceof Array) {
                  if (nodes[i].children.length === 0) {
                    delete nodes[i].children
                  } else {
                    process(nodes[i].children)
                  }
                }
              }
            }
            var orgList=[params]
            process(orgList)
            this.deptList = orgList
          // }
        })
      },
      getPositionData() {
        // this.$http.get('post').then((params) => {
        //   if (params.body.code === 200) {
        //     this.positionList = params.body.data
        //   }
        // })
      },
      getUserData() {
        this.api.post('/account/list', {
            orgId: this.filterDep,
            // posId: this.filterPosition,
            name: this.filterQuery,
            usergroup:'厂内人员',
            pageNum: 1, //页码
            pageRow: 10000, //每页条数
        }).then((params) => {
            console.log(params)
          // if (params.body.code === 200) {
            // params.list.map(item => {
            //   item.depPos = item.poss.map(k => `${k.orgName} - ${k.posName}`)
            // })
            this.staffList = params.list
          // }
        })
      }
    }
  }
</script>

<style scoped>
  .newsup-add-body {
    padding: 20px;
  }

  .newsup-add-header {
    display: flex;
    justify-content: space-around;
  }
</style>
