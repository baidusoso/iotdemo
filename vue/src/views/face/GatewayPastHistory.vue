<template>
    <div>
        <div class="page-body">
            <div>
                <el-row>
                    <el-col :span="4">
                        <el-form>
                            <el-form-item label="人员分组：" label-width="100px" >
                                <el-select @change="handleFilter" v-model="listQuery.userGroup" value="全部">
                                    <el-option
                                    v-for="item in persionType"
                                    :key="item.value"
                                    :label="item.label"
                                    :value="item.value">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                        </el-form>
                    </el-col>
                    <el-col :span="4">
                        <el-form>
                            <el-form-item label="通过状态：" label-width="100px" >
                                <el-select @change="handleFilter" v-model="listQuery.eventStatus" value="全部">
                                     <el-option
                                    v-for="item in accessType"
                                    :key="item.value"
                                    :label="item.label"
                                    :value="item.value">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                        </el-form>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="8">
                        <el-form>
                            <el-form-item label="时间区间：" label-width="100px" >
                                <el-date-picker
                                        @change="handleDatetimerangeChanged"
                                        style="width: 100%;"
                                        v-model="period"
                                        type="datetimerange"
                                        range-separator="至"
                                        start-placeholder="开始时间"
                                        end-placeholder="结束时间">
                                </el-date-picker>
                            </el-form-item>
                        </el-form>
                    </el-col>
                    <el-col :span="8" :offset="8">
                        <el-input @change="handleFilter" placeholder="请输入姓名/工号/身份证号/手机号进行搜索" v-model="listQuery.query">
                            <el-button slot="append" type="primary" icon="el-icon-search" style="background: #1890FF;border-radius: 0 4px 4px 0;font-size: 16px;color: #FFFFFF;">搜索</el-button>
                        </el-input>
                    </el-col>
                </el-row>
                <el-row>
                    <el-table :data="list" style="width: 100%;">
                        <el-table-column label="访问时间" prop="happentime"></el-table-column>
                        <el-table-column label="门禁名称" prop="srcname"></el-table-column>
                        <el-table-column label="姓名" prop="name"></el-table-column>
                        <el-table-column label="性别" prop="gender"></el-table-column>
                        <el-table-column label="分组" prop="usergroup"></el-table-column>
                        <el-table-column label="工号">
                            <template slot-scope="scope">
                                {{scope.row.no && scope.row.no !== '' ? scope.row.no : "— —"}}
                            </template>
                        </el-table-column>
                        <el-table-column label="身份证号">
                            <template slot-scope="scope">
                                {{scope.row.certificateNum && scope.row.certificateNum !== '' ? scope.row.certificateNum : "— —"}}
                            </template>
                        </el-table-column>
                        <el-table-column label="手机号" prop="mobile"></el-table-column>
                        <el-table-column label="结果">
                            <template slot-scope="scope">
                                <span v-if="scope.row.result=== '允许通过'" class="text-success">允许通过</span>
                                <span v-else-if="scope.row.result=== '禁止通过'" class="text-danger">禁止通过</span>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-row>
                <el-row>
                    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="listQuery.pageNum"
                      :page-size="listQuery.pageRow" :total="totalCount" :page-sizes="[10, 20, 50, 100]" layout="total, sizes, prev, pager, next, jumper">
                    </el-pagination>
                </el-row>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        data () {
            return {
                period:[],
                persionType:[
                    {
                        label:'全部',
                        value:'',
                    },
                    {
                        label:'厂内人员',
                        value:'厂内人员',
                    },
                    {
                        label:'外来访客',
                        value:'外来访客',
                    }
                ],
                accessType:[
                    {
                        label:'全部',
                        value:'',
                    },
                    {
                        label:'允许通过',
                        value:'允许通过',
                    },
                    {
                        label:'禁止通过',
                        value:'禁止通过',
                    }
                ],
                list: [],
                listQuery: {
                  pageNum: 1, //页码
                  pageRow: 10, //每页条数

                  userGroup: "",
                  eventStatus: "",
                  startAt: "",
                  endAt:"",
                  query:"",
                },
            }
        },
        methods: {
            getList() {
              //查询列表
              this.listLoading = true;
              this.api({
                url: "/event/list",
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
            handleDatetimerangeChanged(){
                this.listQuery.startAt = String(new Date(this.period[0]));
                this.listQuery.endAt = String(new Date(this.period[1]));
                this.handleFilter();
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
        },
        created () {
            this.getList();
        }
    }
</script>

<style>
    thead th {
        background: #F5F5F5 !important;
        border-radius: 4px 4px 0 0;
        font-size: 14px;
        color: #333333;
        line-height: 22px;
    }

    thead th {
        background: #F5F5F5 !important;
        border-radius: 4px 4px 0 0;
    }
</style>
