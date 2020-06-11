<template>
  <div class="main">
        <el-form ref="visitorForm" style="width:50%;margin:0 auto;"  :model="visitor"  label-width="100px">
           <el-form-item required label="姓名"
           prop="name"
           :rules="[
           { required: true, message: '姓名不能为空'},
           ]">
               <el-input v-model="visitor.name"></el-input>
           </el-form-item>
           <el-form-item required label="身份证号"
            prop="certificateNum"
           :rules="[
           { validator: idCheck, message: '身份证号码错误'},
           { required: true, message: '身份证号码不能为空'},
           ]">
               <el-input v-model="visitor.certificateNum"></el-input>
           </el-form-item>
           <el-form-item  label="手机号">
               <el-input v-model="visitor.mobile"></el-input>
           </el-form-item>
            <el-form-item  required label="性别"  prop="gender"
           :rules="[
           { required: true, message: '性别不能为空'},
           ]">
               <el-radio-group v-model="visitor.gender">
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
                   <img v-if="avatarUrl" :src="avatarUrl" class="avatar">
                   <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                   <div>点击选择上传的图片</div>
                   <span v-if="avatarUrl" class="img-reupload">
                   <i class="el-icon-edit"></i>
                   修改</span>
               </el-upload>
           </el-form-item>
           <el-form-item style="width:100%;text-align:right;">
               <el-button style="width:150px;" @click="$router.go(-1)">取消</el-button>
               <el-button style="width:150px;margin-left:10%;" type="primary" @click="submitForm('visitorForm')">提交</el-button>
           </el-form-item>
        </el-form>
  </div>
</template>
<script>
  import {mapGetters} from 'vuex'
  export default {
    data() {
      return {
        listLoading: false, //数据加载等待动画
        imageUrl:'',
        visitor: {
          id:'',
          name: '',
          gender:'',
          certificateNum: '',
          mobile: '',
          usergroup:'外来访客',
          facePic:null,
          faceImg:null,
        },
        avatarUrl:"",
        visitorInfoList:'',
        canSubmit: true,
      }
    },
    created() {
      this.getAccountInfo();
    },
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
          _vue.visitor.id = data.id;
          _vue.visitor.name = data.name;
          _vue.visitor.certificateNum = data.certificateNum;
          _vue.visitor.mobile = data.mobile;
          _vue.visitor.gender = data.gender;
          _vue.visitor.usergroup=data.usergroup;
          _vue.visitor.facePic=data.facePic;
          _vue.imageUrl = "http://127.0.0.1:8080/face/visitor/"+data.facePic+".jpg";
        }).catch(()=>{
          _vue.listLoading = false;
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
          this.visitor.faceImg = file
          // this.fileList.append('img', file)
          return false
      },
      submitForm(formName) {
          this.$refs[formName].validate((valid) => {
              if (valid) {
                  this.visitorInfoList = new FormData()
                  this.canSubmit = false
                  for (const key in this.visitor) {
                      this.visitorInfoList.append(key, this.visitor[key]);
                  }
                  this.infoUpload()
              } else {
                  console.log('error submit!!');
                  return false;
              }
          });
      },
      infoUpload(){
          this.api.post('account/saveOrUpdateAccount',this.visitorInfoList).then((params) => {
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
    }
  }
</script>
<style>

  .main{
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
