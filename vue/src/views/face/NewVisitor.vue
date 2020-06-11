<template>
    <div>
         <div class="page-body">
                 <el-form ref="guestAddForm" style="width:50%;margin:0 auto;"  :model="guestAddForm"  label-width="100px">
                    <el-form-item required label="姓名"
                    prop="name"
                    :rules="[
                    { required: true, message: '姓名不能为空'},
                    ]">
                        <el-input v-model="guestAddForm.name"></el-input>
                    </el-form-item>
                    <el-form-item required label="身份证号"
                     prop="certificateNum"
                    :rules="[
                    { validator: idCheck, message: '身份证号码错误'},
                    { required: true, message: '身份证号码不能为空'},
                    ]">
                        <el-input v-model="guestAddForm.certificateNum"></el-input>
                    </el-form-item>
                    <el-form-item  label="手机号">
                        <el-input v-model="guestAddForm.mobile"></el-input>
                    </el-form-item>
                     <el-form-item  required label="性别"  prop="gender"
                    :rules="[
                    { required: true, message: '性别不能为空'},
                    ]">
                        <el-radio-group v-model="guestAddForm.gender">
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
                        <div @click="cameraTrigger" class="el-upload-div">
                            <img v-if="snapShotUrl" :src="snapShotUrl" class="avatar">
                            <i v-else class="el-icon-camera avatar-uploader-icon"></i>
                            <div>点击进行拍照上传</div>
                            <span v-if="imageUrl" class="img-reupload">
                            <i class="el-icon-edit"></i>
                            修改</span>
                        </div>
                    </el-form-item>
                    <el-form-item style="width:100%;text-align:right;">
                        <el-button style="width:150px;" @click="resetForm('guestAddForm')">重置</el-button>
                        <el-button style="width:150px;margin-left:10%;" type="primary" @click="submitForm('guestAddForm')">提交</el-button>
                    </el-form-item>
                 </el-form>
         </div>
         <PhotoPicker @snapShotOk='snapShotHandler' @close="photoPickerShow=false" :show="photoPickerShow" />
    </div>
</template>

<script>
import PhotoPicker from './PhotoPicker'
export default {
    components:{
        PhotoPicker
    },
    data(){
        return {
            photoPickerShow:false,
            imageUrl:'',
            snapShotUrl:'',
            imgList:[],
            guestAddForm:{
                img:null,
                name:'',
                certificateNum:'',
                mobile:'',
                gender:''
            },
            canSubmit: true,
            fileList: "",
        }
    },
    mounted() {
        this.fileList = new FormData();
        this.modeHandler()

    },
    methods:{
        snapShotHandler(data){
            this.snapShotUrl = data.base64
            this.photoPickerShow = false
        },
        cameraTrigger(){
            this.photoPickerShow = true
        },
        modeHandler(){
            if(this.$route.query.name){
                this.$http.get(`account/${this.$route.query.name}`).then((params) => {
                    console.log(params);
                    this.guestAddForm.name = params.body.data.name
                    this.guestAddForm.certificateNum = params.body.data.certificateNum
                    this.guestAddForm.mobile = params.body.data.mobile
                    this.guestAddForm.gender = params.body.data.gender
                    this.guestAddForm.imageUrl = params.body.data.facePic
                    this.imageUrl = params.body.data.facePic
                })
            }
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
            this.dialogVisible = true;
        },
        currentChange(currentPage){
            this.currentPage = currentPage;
        },
        modifyGuest(params){
            console.log(params);
        },
        deleteGuest(params){

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
            this.guestAddForm.img = file
            // this.fileList.append('img', file)
            return false
        },
        submitForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    this.fileList = new FormData()
                    this.canSubmit = false
                    for (const key in this.guestAddForm) {
                        this.fileList.append(key, this.guestAddForm[key]);
                    }
                    if(this.$route.query.name){
                        this.fileList.append('id', this.$route.query.name);
                    }
                    this.infoUpload()
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
        },
        infoUpload(){
            this.$http.post('account/visitor',this.fileList).then((params) => {
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
            this.snapShotUrl = ''
            this.imageUrl = ''
            this.$refs[formName].resetFields();
        }
    }
}
</script>

<style>
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

  .avatar {
    width: 210px;
    /* height: 210px; */
    display: block;
  }
  .img-reupload{
    font-family: PingFangSC-Regular;
    font-size: 14px;
    position:relative;
    left:140px;
    bottom:15px;
    color: #1890FF;
    letter-spacing: 0;
  }
  .el-form-item__content{
      display:flex;
  }
    .el-upload-div{
        width: 178px;
        height:184px;
        cursor: pointer;
        margin-left: 20px;
        text-align: center;
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
    }

</style>
