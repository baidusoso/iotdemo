<template>
  <div class="app-container">
    <div id="org" class="tree" ref="orgLayout">
      <el-tree :data="rootOrg" ref="org" node-key="orgCode" :default-expanded-keys="defaultExpandedKeys"
        :highlight-current="true" :default-checked-keys="[]" :props="defaultProps" :check-strictly="true"
        :check-on-click-node="true" @check-change="handleCheckChange" @node-click="handleNodeClick">
      </el-tree>
    </div>
    <div class="main">
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
              <el-button @click="handleFilter" style="border:1px #1890FF solid;" icon="el-icon-search">搜索</el-button>
              <el-button type="primary" icon="plus"  @click="sync">同步</el-button>
              <!-- <el-button type="primary" icon="el-icon-upload2" @click="urlPush(`/`)">批量导入</el-button>
              <el-button type="danger" icon="plus"  @click="showCreate">批量删除</el-button> -->
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
      <el-table-column align="center" label="性别" prop="gender" width="80">
        <template slot-scope="scope">
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
      <el-table-column align="center" label="操作" width="200">
        <template slot-scope="scope">
          <el-button type="primary" icon="edit" @click="showUpdate(scope.$index)">修改</el-button>
          <el-button type="danger" icon="delete" @click="removeStaff(scope.$index)">删除
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
  import {mapGetters} from 'vuex'
  import ScrollBar from '@/components/ScrollBar'
  export default {
    components: {ScrollBar},
    data() {
      return {
        totalCount: 0, //分页组件--数据总条数
        list: [], //表格的数据
        listLoading: false, //数据加载等待动画
        listQuery: {
          pageNum: 1, //页码
          pageRow: 10, //每页条数
          orgId:"",
          name:"",
          no:"",
          mobile:"",
          certificateNum:"",
          usergroup:"厂内人员",
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
        defaultExpandedKeys:[],
        defaultProps: {
          "children": "children",
          "label": "orgName"
        },
        editOrgCode: "",
        editOrgName: "",
        rolelist:[],
        imgHost:"http://10.69.212.11:3000/face/user/",
        orgHeight:'',
      }
    },
    created() {
      this.getList();
      this.getRootOrg();
    },
    mounted() {
      this.orgHeight=`${document.documentElement.clientHeight}` - 100
      const that = this
      window.onresize = () => {
        return (() => {
          var orgHeight = `${document.documentElement.clientHeight}` - 100
          that.orgHeight = orgHeight
        })()
      }
    },
    watch: {
      // 如果 `clientHeight` 发生改变，这个函数就会运行
      orgHeight(o, n) {
        this.changeFixed(this.orgHeight)
      }
    },
    methods: {
      changeFixed(clientHeight) { //动态修改样式
        console.log(clientHeight);
        this.$refs.orgLayout.style.height = clientHeight + 'px';
      },
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
          var defaultExpandedKeys=[];
          if (data != null) {
            rootOrg.push(data);
            while(data.children!=null && data.children.length<3){
              data=data.children[0];
            }
            if(data!=null){
              console.log(data);
              defaultExpandedKeys.push(data.orgCode);
            }
          }
          _vue.defaultExpandedKeys=defaultExpandedKeys;
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
        let account = this.list[$index];
        this.$router.push({name: 'edit-staff',query: { accountId: account.id }})
      },
      removeStaff($index) {
        let _vue = this;
        this.$confirm('确定删除此人员?', '提示', {
          confirmButtonText: '确定',
          showCancelButton: false,
          type: 'warning'
        }).then(() => {
          //TODO
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
  .tree {
    position: fixed;
    width: 300px;
    height: 100%;
    overflow-y:auto;
    overflow-x: scroll;
    border-right: 1px solid #EEEEEE;
    padding-top: 20px;
  }
  .el-tree {
    min-width:100%;
    font-size:14px;
    display: inline-block;;
  }
  .main{
    margin-left: 300px;
    padding: 20px;
  }
</style>
