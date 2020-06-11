<template>
  <div class="app-container">
    <el-card style="padding:20px 0;">
        <el-form ref="accountForm" style="width:50%;margin:0 auto;"  :model="tempAccount"  label-width="100px">
          <el-form-item required label="部门"
          prop="orgId"
          :rules="[
          { required: true, message: '部门不能为空'},
          ]">
              <el-select v-model="tempAccount.orgName" placeholder="请选择" style="width: 100%;">
                <el-option :value="tempAccount.orgId" style="height: auto">
                    <el-tree
                      :data="rootOrg"
                      ref="org"
                      show-checkbox
                      node-key="orgCode"
                      default-expand-all
                      :highlight-current="true"
                      :default-checked-keys="[]"
                      :props="defaultProps"
                      :check-strictly="true"
                      :check-on-click-node="true"
                      @check-change="handleCheckChange"
                      @node-click="handleNodeClick">
                    </el-tree>
                </el-option>
              </el-select>
          </el-form-item>
           <el-form-item required label="姓名"
           prop="name"
           :rules="[
           { required: true, message: '姓名不能为空'},
           ]">
               <el-input v-model="tempAccount.name"></el-input>
           </el-form-item>
           <el-form-item required label="身份证号"
            prop="certificateNum"
           :rules="[
           { validator: idCheck, message: '身份证号码错误'},
           { required: true, message: '身份证号码不能为空'},
           ]">
               <el-input v-model="tempAccount.certificateNum"></el-input>
           </el-form-item>
           <el-form-item required label="工号" prop="no" :rules="[
          { required: true, message: '工号不能为空'},
          ]">
               <el-input v-model="tempAccount.no"></el-input>
           </el-form-item>
           <el-form-item  label="手机号">
               <el-input v-model="tempAccount.mobile"></el-input>
           </el-form-item>
            <el-form-item  required label="性别"  prop="gender"
           :rules="[
           { required: true, message: '性别不能为空'},
           ]">
               <el-radio-group v-model="tempAccount.gender">
                   <el-radio label="男"></el-radio>
                   <el-radio label="女"></el-radio>
               </el-radio-group>
           </el-form-item>
           <el-form-item label="上传照片" prop="photo"
           :rules="[
           { required: false, message: '照片不能为空'},
           ]">
               <el-upload
               class="avatar-uploader"
               action="https://jsonplaceholder.typicode.com/posts/"
               :show-file-list="false"
               :on-change="handleAvatarSuccess"
               :on-preview="handlePictureCardPreview"
               :before-upload="beforeAvatarUpload">
                   <img v-if="imageUrl" :src="imageUrl" class="avatar">
                   <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                   <div>点击选择上传的图片</div>
                   <span v-if="imageUrl" class="img-reupload">
                   <i class="el-icon-edit"></i>
                   修改</span>
               </el-upload>
           </el-form-item>
           <el-form-item style="width:100%;text-align:right;">
               <el-button style="width:150px;" @click="resetForm('accountForm')">重置</el-button>
               <el-button style="width:150px;margin-left:10%;" type="primary" @click="submitForm('accountForm')">提交</el-button>
           </el-form-item>
        </el-form>
    </el-card>
  </div>
</template>
<script>
  import {mapGetters} from 'vuex'
  export default {
    data() {
      return {
        listLoading: false, //数据加载等待动画
        imageUrl:'',
        tempAccount: {
          id:'',
          orgId:'',
          orgName:'',
          name: '',
          gender:'',
          certificateNum: '',
          no: '',
          mobile: '',
          facePic:null,
        },
        rootOrg: [],
        defaultExpandedKeys:[],
        defaultProps: {
          "children": "children",
          "label": "orgName"
        },
        canSubmit: true,
        fileList: "",
        imgHost:"http://10.69.212.11:3000/face/user/",
        orgHeight:'',
      }
    },
    created() {
      this.getAccountInfo();
    },
    // mounted() {
    //   this.orgHeight=`${document.documentElement.clientHeight}` - 100
    //   const that = this
    //   window.onresize = () => {
    //     return (() => {
    //       var orgHeight = `${document.documentElement.clientHeight}` - 100
    //       that.orgHeight = orgHeight
    //     })()
    //   }
    // },
    // watch: {
    //   // 如果 `clientHeight` 发生改变，这个函数就会运行
    //   orgHeight(o, n) {
    //     this.changeFixed(this.orgHeight)
    //   }
    // },
    methods: {
      getAccountInfo() {
        var _vue = this;
        this.listLoading = true;
        this.api({
          url: "/account/"+this.$route.query.accountId,
          method: "get",
        }).then(data => {
          console.log(data);
          _vue.listLoading = false;
          _vue.tempAccount.id = data.id;
          _vue.tempAccount.orgId = data.orgId;
          _vue.tempAccount.name = data.name;
          _vue.tempAccount.no = data.no;
          _vue.tempAccount.certificateNum = data.certificateNum;
          _vue.tempAccount.mobile = data.mobile;
          _vue.tempAccount.gender = data.gender;
          _vue.imageUrl = data.facePic;
          _vue.getRootOrg();
        }).catch(()=>{
          _vue.listLoading = false;
        })
      },
      getRootOrg() {
        console.log("getRootOrg");
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
      idCheck : (rule, value, callback) => {
          var p = /^[1-9]\d{5}(18|19|20)\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/;
          if (value === '') {
              callback(new Error('身份证号码不能为空'));
          } else {
              if(!p.test(value)){
                  callback(new Error());
              }
              callback();
          }
      },
      handleCheckChange(item, node, self) {
        console.log("handleCheckChange:"+node)
        if (node == true) {
          console.log(item);
          this.tempAccount.orgId = item.orgCode;
          this.tempAccount.orgName = item.orgName;
          this.$refs.org.setCheckedKeys([item.orgCode]);
        } else {
          if (this.tempAccount.orgId == item.orgCode) {
            this.$refs.org.setCheckedKeys([item.orgCode]);
          }
        }
      },
      handleNodeClick(item, node, self) {
        console.log("handleNodeClick");
        this.tempAccount.orgId = item.orgCode;
        this.tempAccount.orgName = item.orgName;
        this.$refs.org.setCheckedKeys([item.orgCode]);
      },
      handlePictureCardPreview(file) {
          this.imageUrl = file.url;
      },
      handleAvatarSuccess(res, file) {
          this.imageUrl = window.URL.createObjectURL(res.raw)
      },
      beforeAvatarUpload(file) {
          const isJPG = file.type === 'image/jpeg';
          const isLt2M = file.size / 1024 / 1024 < 0.2;

          if (!isJPG) {
              this.$message.error('上传人脸图片只能是 JPG 格式!');
              return false
          }
          if (!isLt2M) {
              this.$message.error('上传人脸图片大小不能超过 200Kb!');
              return false
          }
          this.tempAccount.img = file
          // this.fileList.append('img', file)
          return false
      },
      submitForm(formName) {
          this.$refs[formName].validate((valid) => {
              if (valid) {
                  this.fileList = new FormData()
                  this.canSubmit = false
                  for (const key in this.tempAccount) {
                      this.fileList.append(key, this.tempAccount[key]);
                  }
                  this.infoUpload()
              } else {
                  console.log('error submit!!');
                  return false;
              }
          });
      },
      infoUpload(){
          this.api.post('account/saveOrUpdateAccount',this.fileList).then((params) => {
              if(params.body.code === 200){
                  this.$notify.success({
                      title:this.$route.query.name?"修改外来人员成功":"新增外来人员成功",
                      duration: 1000
                  })
                  setTimeout((params) => {
                      this.$router.go(-1)
                  },1000)
              }else{
                  this.canSubmit = true
              }
              console.log(params)
          })
      },
      resetForm(formName) {
          this.canSubmit = true
          this.imageUrl = ''
          this.$refs[formName].resetFields();
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
  .avatar {
    width: 210px;
    /* height: 210px; */
    display: block;
  }
.avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    /* border: 1px dashed #CCCCCC; */
    /* background: #F5F5F5; */
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    /* height:138px; */
    height: 100px;
    line-height: 178px;
    text-align: center;
  }
  .avatar-uploader-icon + div{
      color: #999999;
      font:12px;
      margin-bottom: 30px;
  }
</style>
