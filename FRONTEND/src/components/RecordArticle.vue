<template>
  <el-container class="home_container">
    <el-header>
      <div class="top_bar">时间轴历史</div>
      <div class="top_bar">撰写文章</div>
      <el-button class="top_bar_back" @click="backHome" icon="el-icon-back" type="text">返回</el-button>
    </el-header>

    <el-main class="main">

      <div style="margin: 60px 0;"></div>
      <div>
        <span class="word_tip">
          文章标题:
        </span>
        <el-input v-model="title" maxlength="50" show-word-limit placeholder="请输入标题..." style="width: 484px;"
          size="small"></el-input>
      </div>

      <div style="margin: 30px 0;"></div>
      <div>
        <span class="word_tip">
          事件时间:
        </span>
        <el-date-picker class="event_time" v-model="eventTime" size="small" type="date" placeholder="年月日" style="width: 220px;"
          format="yyyy 年 MM 月 dd 日" value-format="timestamp" :picker-options="pickerOptions"></el-date-picker>
        <span class="word_tip">精确到:</span>
        <el-radio v-model="accuracy" label="#A8D8B9">日</el-radio>
        <el-radio v-model="accuracy" label="#F9BF45">月</el-radio>
        <el-radio v-model="accuracy" label="#E83015">年</el-radio>
      </div>

      <div style="margin: 30px 0;"></div>
      <div>
        <span class="word_tip">
          内容概要:
        </span>
        <el-input v-model="content" type="textarea" maxlength="256" show-word-limit style="width: 484px;"
          placeholder="请输入内容" :autosize="{ minRows: 7, maxRows: 10}">
        </el-input>
      </div>

      <div style="margin: 30px 0;"></div>
      <div>
        <el-upload v-loading="loadingPic" class="avatar-uploader" :http-request="uploadPic" :multiple="true"
          :show-file-list="false" action="" :before-upload="beforeAvatarUpload"
          accept="image/jpeg,image/gif,image/png,image/bmp">
          <img v-if="pic1Url" :src="pic1Url + '?x-oss-process=style/sjzxt'" class="avatar">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
        <span class="upload_pic_tip">
          (配图上传及回显速度较慢，耐心等待，不要重复点击上传)
        </span>
        <!-- <el-input class="name_mail" v-model="pic1Name" placeholder="图片名" size="small"></el-input> -->
      </div>

      <div style="margin: 35px 0;"></div>
      <div>
        <span class="word_tip">
          大侠留名:
        </span>
        <el-input style="width: 240px;" class="name_mail" v-model="authorName" maxlength="10" placeholder="昵称" size="small"></el-input>
        <el-input style="width: 240px;" class="name_mail" v-model="authorMail" placeholder="邮箱" size="small"></el-input>
      </div>

      <div style="margin: 45px 0;"></div>
      <div>
        <el-button v-loading="loadingSave" icon="el-icon-check" size="small" @click="send">保存</el-button>
      </div>
    </el-main>

  </el-container>
</template>


<script>
  import {
    postRequest
  } from '../utils/api'
  import {
    isNotNullORBlank
  } from '../utils/utils'
  import {
    client
  } from '../utils/alioss'
  export default {
    mounted: function () {
      var _this = this;
      //localsession取
      if (localStorage.authorName) {
        _this.authorName = localStorage.authorName;
      }
      if (localStorage.authorMail) {
        _this.authorMail = localStorage.authorMail;
      }
    },

    methods: {
      send(state) {
        var _this = this;
        if (!(isNotNullORBlank(this.authorName, this.authorMail))) {
          this.$message({
            type: 'warning',
            message: '壮士留名!',
            showClose: true,
          });
          return;
        }
        localStorage.authorName = _this.authorName;
        localStorage.authorMail = _this.authorMail;
        if (!(isNotNullORBlank(this.title, this.content, this.eventTime, this.accuracy, this.pic1Url))) {
          this.$message({
            type: 'warning',
            message: '都要填哦(图片也是)!',
            showClose: true,
          });
          return;
        }
        _this.loadingSave = true;
        postRequest("/api/article/record", {
          title: _this.title,
          content: _this.content,
          eventTime: _this.eventTime,
          accuracy: _this.accuracy,
          authorName: _this.authorName,
          authorMail: _this.authorMail,
          pic1Url: _this.pic1Url,
          pic1Name: _this.pic1Name
        }).then(resp => {
          _this.loadingSave = false;
          // if (resp.code == 200 && resp.data.data.status == 'success') {
          _this.$message({
            type: 'success',
            message: '感谢你为人类做出的贡献!——司马千',
            showClose: true,
          });
          _this.title = '';
          _this.content = '';
          _this.eventTime = '';
          _this.accuracy = '#A8D8B9';
          _this.pic1Url = '';
          _this.pic1Name = '';

          // }
        }, resp => {
          _this.loadingSave = false;
          _this.$message({
            type: 'error',
            message: state == 0 ? '保存失败!' : '发布失败!',
            showClose: true,
          });
        })
      },
      //格式及大小校验
      beforeAvatarUpload(file) {
        const isJPG = file.type === 'image/jpg';
        const isJPEG = file.type === 'image/jpeg';
        const isGIF = file.type === 'image/gif';
        const isPNG = file.type === 'image/png';
        const isBMP = file.type === 'image/bmp';
        const isLt2M = file.size / 1024 / 1024 < 2;

        if (!isJPG && !isJPEG && !isGIF && !isPNG && !isBMP) {
          this.$message({
            type: 'warning',
            message: '图片只能是 JPG 格式!',
            showClose: true,
          });
        }
        if (!isLt2M) {
          this.$message({
            type: 'warning',
            message: '服务器容量有限，图片不能超过 2MB!',
            showClose: true,
          });
        }
        return (isJPG || isJPEG || isBMP || isGIF || isPNG) && isLt2M;
      },
      //上传配图
      uploadPic(file) {
        var _this = this;
        _this.loadingPic = true;
        var fileName = file.file.name;
        fileName = 'sjzpt' + file.file.uid + fileName.substring(fileName.lastIndexOf('.'));
        //定义唯一的文件名，打印出来的uid其实就是时间戳
        client().put(fileName, file.file).then(
          result => {
            _this.pic1Name = result.name,
              _this.pic1Url = result.url
          })
        _this.loadingPic = false;
      },
      backHome() {
        var _this = this;
        _this.$router.replace({
          path: '/'
        });
      }

    },
    data() {
      return {
        pickerOptions: {
          disabledDate(time) {
            return time.getTime() > Date.now();
          }
        },
        title: '',
        content: '',
        eventTime: '',
        accuracy: '#A8D8B9',
        authorName: '',
        authorMail: '',
        pic1Url: '',
        pic1Name: '',
        loadingPic: false,
        loadingSave: false


      }
    }
  }

</script>
<style>
  .top_bar {
    color: rgb(128, 128, 128);
    font-size: 20px;
    display: inline;
  }
  .top_bar_back {
    color: rgb(128, 128, 128);
    font-size: 15px;
    display: inline;
  }

  .home_container {
    height: 100%;
    position: absolute;
    top: 0px;
    left: 0px;
    width: 100%;
  }

  .el-header {
    padding: 0 10px;
    background-color: #fafafa;
    text-align: center;
    display: flex;
    align-items: center;
    justify-content: space-between;
    height: 50px;
    margin-top: 0px;
    position: fixed;
    width: 100%;
    z-index: 100;
    box-shadow: 0px 0px 1px #d1d1d1;
  }

  .el-loading-mask {
    z-index: 20000;
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
    font-size: 30px;
    color: #8c939d;
    width: 150px;
    height: 150px;
    line-height: 150px;
    text-align: center;
  }

  .avatar {
    width: 150px;
    height: 150px;
    display: block;
  }

  .event_time {
    margin-right: 40px;
  }

  .upload_pic_tip {
    color: #dbdbdb;
    font-size: 13px;
  }

  .word_tip {
    color: #808080;
  }

  .el-radio {
    margin-right: 20px;
  }

  .name_mail {
    width: 240px;
  }

</style>
