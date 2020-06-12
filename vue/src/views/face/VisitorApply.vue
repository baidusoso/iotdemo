<template>
  <div>
    <div class="page-body">
      <el-form ref="form" style="width:50%;margin:0 auto;" :model="form" label-width="100px">
        <el-form-item required label="身份证号" prop="certificateNum" :rules="[
                    { validator: idCheck, message: '身份证号码错误'},
                    { required: true, message: '身份证号码不能为空'},
                    ]">
          <el-input v-model="form.certificateNum" @blur="getUser" @focus="notExist = false">
            <el-alert v-if="notExist" slot="suffix" :title="notExistTitle" type="error" :closable="false" />
            <el-button slot="append" @click="getUser">查询</el-button>
          </el-input>
        </el-form-item>
        <el-form-item required label="姓名" prop="name" :rules="[
                    { required: true, message: '姓名不能为空'},
                    ]">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="人脸照片" prop="facePic" :rules="[
                    { required: true, message: '照片不能为空'},
                    ]">
          <el-upload class="avatar-uploader" action="https://jsonplaceholder.typicode.com/posts/" :show-file-list="false"
            :on-change="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
            <img v-if="imageUrl" :src="imageUrl" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon" />
            <div>点击选择上传的图片</div>
          </el-upload>
          <div @click="cameraTrigger" class="el-upload-div">
            <img v-if="snapshotUrl" :src="snapshotUrl" class="avatar">
            <i v-else class="el-icon-camera avatar-uploader-icon" />
            <div>点击进行拍照上传</div>
          </div>
        </el-form-item>
        <el-form-item required label="手机号" prop="mobile" :rules="[{ required: true, message: '手机号不能为空'}]">
          <el-input v-model="form.mobile" />
        </el-form-item>
        <el-form-item required label="性别" prop="gender" :rules="[
                    { required: true, message: '性别不能为空'},
                    ]">
          <el-radio-group v-model="form.gender" style="margin-top:14px;">
            <el-radio label="男" />
            <el-radio label="女" />
          </el-radio-group>
        </el-form-item>
        <el-form-item required label="被访人员" prop="targetUserId" :rules="[{ required: true, message: '被访人员不能为空'} ]">
          <el-button plain style="border-style: dashed;border-width: 2px;width: 100%" @click="pickTarget()">
            <i class="el-icon-circle-plus-outline">&nbsp; {{target ? target.name : '添加被访人员'}}</i>
          </el-button>
        </el-form-item>
        <el-form-item label="通行时间">
          <span v-if="startAt">{{this.startAt}} 至 {{this.endAt}}</span>
          <span v-if="!startAt" style="color: #F56C6C;">无有效通行时间</span>
        </el-form-item>
        <el-form-item v-if="!startAt" style="width:100%;">
          <el-button style="width:150px;margin-left:30%;" type="primary" @click="submitForm('form')">
            提交
          </el-button>
        </el-form-item>
      </el-form>
    </div>
    <PhotoPicker @snapShotOk='snapshotHandler' @close="cameraVisible=false" :show="cameraVisible" />
    <user-picker v-if="targetVisible" :visible="targetVisible" title="添加被访人员" @confirm="updateTarget" @cancel='targetVisible = false' />
  </div>
</template>

<script>
  import PhotoPicker from './PhotoPicker'
  import UserPicker from './UserPicker'

  export default {
    components: {
      PhotoPicker,
      UserPicker,
    },
    data() {
      return {
        form: {
          certificateNum: '',
          name: '',
          facePic: '',
          faceImg: '',
          mobile: '',
          gender: '',
          targetUserId: '',
          targetUserName: '',
        },
        imageUrl: null,
        snapshotUrl: null,
        target: null,
        cameraVisible: false,
        targetVisible: false,
        notExist: false,
        notExistTitle: "查无此人，请录入",
        startAt: null,
        endAt: null,
      }
    },
    methods: {
      getUser() {
        if (!this.form.certificateNum) {
          this.startAt = null
          this.endAt = null
          return
        }

        this.api.get(`/account/certificatenum/${this.form.certificateNum}`).then((res) => {
          console.log(res)
          const user = res
          if (user) {
            if (user.usergroup != "外来访客") {
              this.notExist = true
              this.notExistTitle = '此人为厂内人员,不能录入'
              this.startAt = null
              this.endAt = null
              return
            }
            this.form.name = user.name
            this.form.facePic = `/face/visitor/${user.facePic}?s=${Math.random()}`
            this.form.mobile = user.mobile
            this.form.gender = user.gender
            this.imageUrl = this.form.facePic
            this.startAt = user.startAt
            this.endAt = user.endAt
          } else {
            this.notExist = true
            this.notExistTitle = "查无此人，请录入"
            this.startAt = null
            this.endAt = null
          }
        })
      },
      pickTarget() {
        this.targetVisible = true
      },
      updateTarget(val) {
        this.target = val
        this.form.targetUserId = val.id
        this.form.targetUserName = val.name
      },
      snapshotHandler(data) {
        this.imageUrl = null
        this.snapshotUrl = data.base64
        this.form.facePic = this.snapshotUrl
        this.form.faceImg = new File([data.blob], "snapshot.jpg", {
          type: "image/jpeg",
          lastModified: Date.now()
        })
        this.cameraVisible = false
      },
      cameraTrigger() {
        this.cameraVisible = true
      },
      idCheck: (rule, value, callback) => {
        var p = /^[1-9]\d{5}(18|19|20)\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/
        if (value === '') {
          callback(new Error('身份证号码不能为空'))
        } else {
          if (!p.test(value)) {
            callback(new Error())
          }
          callback()
        }
      },
      handleAvatarSuccess(res, file) {
        this.imageUrl = window.URL.createObjectURL(res.raw)
        this.form.facePic = this.imageUrl
      },
      beforeAvatarUpload(file) {
        const isJPG = file.type === 'image/jpeg'
        const isLt2M = file.size / 1024 / 1024 < 0.2

        if (!isJPG) {
          this.$message.error('上传人脸图片只能是 JPG 格式!')
          return false
        }
        if (!isLt2M) {
          this.$message.error('上传人脸图片大小不能超过 200Kb!')
          return false
        }
        this.form.faceImg = file
        this.snapshotUrl = null
        return false
      },
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            const formData = new FormData()

            for (const key in this.form)
              formData.append(key, this.form[key] || '')

            this.api.post('/account/guest', formData)
              .then((res) => {
                this.$notify.success({
                  title: '提交成功',
                  duration: 1000
                })
                setTimeout(() => {
                  this.$refs[formName].resetFields()
                  this.imageUrl = null
                  this.snapshotUrl = null
                  this.target = null
                  this.notExist = false
                }, 1000)
              })
          } else {
            console.log('error submit!!')
            return false
          }
        })
      },
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

  .avatar-uploader-icon+div {
    color: #999999;
    font-size: 12px;
    margin-bottom: 30px;
  }

  .avatar {
    width: 210px;
    /* height: 210px; */
    display: block;
  }

  .img-reupload {
    font-family: PingFangSC-Regular;
    font-size: 14px;
    position: relative;
    left: 140px;
    bottom: 15px;
    color: #1890FF;
    letter-spacing: 0;
  }

  .el-form-item__content {
    display: flex;
  }

  .el-upload-div {
    width: 178px;
    height: 184px;
    cursor: pointer;
    margin-left: 20px;
    text-align: center;
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
  }
</style>
