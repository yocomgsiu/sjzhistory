<template>
  <el-form :rules="rules" class="login-container" label-position="left"
           label-width="0px" v-loading="loading">
    <h3 v-if="!registerFlag" class="login_title">系统登录</h3>
    <h3 v-if="registerFlag" class="login_title">系统注册</h3>
    <el-form-item prop="account">
      <el-input type="text" v-model="loginForm.username" auto-complete="off" placeholder="账号"></el-input>
    </el-form-item>
    <el-form-item prop="checkPass">
      <el-input type="password" v-model="loginForm.password" auto-complete="off" placeholder="密码" clearable></el-input>
    </el-form-item>
    <!-- <el-checkbox class="login_remember" v-model="checked" label-position="left">记住密码</el-checkbox> -->
    <el-form-item style="width: 100%">
      <el-button type="primary" v-if="!registerFlag" @click.native.prevent="loginClick" style="width: 100%; padding-bottom: 10px">登录</el-button>
      <el-button type="primary" v-if="registerFlag" @click.native.prevent="registerClick" style="width: 100%; padding-bottom: 10px">注册</el-button>
    </el-form-item>
    <el-form-item style="width: 100%">
      <el-button type="text" v-if="!registerFlag" @click.native.prevent="registerFlagClick" style=" color: rgb(199, 199, 199); padding: 0px 0px 0px">我要注册</el-button>
      <el-button type="text" v-if="registerFlag" @click.native.prevent="loginFlagClick" style=" color: rgb(199, 199, 199); padding: 0px 0px 0px">返回登录</el-button>
      <el-button type="text" @click.native.prevent="forgetPwdClick" style=" color: rgb(199, 199, 199); padding: 0px 0px 0px">我把密码忘了</el-button>
    </el-form-item>
  </el-form>
</template>


<script>
  import {postRequest} from '../utils/api'
  import {putRequest} from '../utils/api'
  export default{
    data(){
      return {
        // rules: {
        //   account: [{required: true, message: ' ', trigger: 'blur'}],
        //   checkPass: [{required: true, message: ' ', trigger: 'blur'}]
        // },
        // checked: true,
        loginForm: {
          username: '',
          password: ''
        },
        loading: false,
        registerFlag:false
      }
    },
    methods: {

      loginFlagClick: function () {
        this.registerFlag = false;
      },
      registerFlagClick: function () {
        this.registerFlag = true;
      },

      forgetPwdClick: function () {
        var _this = this;
        this.loading = true;
        _this.$router.replace({path: '/timelineHistory'});
      },

      loginClick: function () {
        var _this = this;
        this.loading = true;
        postRequest('/user/login', {
          username: this.loginForm.username,
          password: this.loginForm.password
        }).then(resp=> {
          _this.loading = false;
          if (resp.status == 200) {
            //成功
            var json = resp.data;
            if (json.status == 'success') {
              _this.$router.replace({path: '/home'});
            } else {
              _this.$alert('登录失败!', 'ops!');
            }
          } else {
            //失败
            _this.$alert('登录失败!', 'ops!');
          }
        }, resp=> {
          _this.loading = false;
          _this.$alert('服务器网线被挖断了!', '啊哦!');
        });
      },

      registerClick: function () {
        var _this = this;
        this.loading = true;
        postRequest('/login', {
          username: this.loginForm.username,
          password: this.loginForm.password
        }).then(resp=> {
          _this.loading = false;
          if (resp.status == 200) {
            //成功
            var json = resp.data;
            if (json.status == 'success') {
              _this.$router.replace({path: '/home'});
            } else {
              _this.$alert('注册失败!', 'ops!');
            }
          } else {
            //失败
            _this.$alert('注册失败!', 'ops!');
          }
        }, resp=> {
          _this.loading = false;
          _this.$alert('服务器网线被挖断了!', '啊哦!');
        });
      }


    }
  }
</script>



<style>
  .login-container {
    border-radius: 15px;
    background-clip: padding-box;
    margin: 150px auto;
    width: 300px;
    padding: 25px 25px 0px 25px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 20px #cac6c6;
  }

  .login_title {
    margin: 0px auto 40px auto;
    text-align: center;
    color: #505458;
  }

  .el-button--primary {
    color: #FFF;
    background-color: #33a6b8;
    border-color: #33a6b8;
  }

  .el-button--primary:focus, .el-button--primary:hover {
    background: #33a6b8;
    border-color: #33a6b8;
    color: #ffffff;
}

  .login_remember {
    margin: 0px 0px 35px 0px;
    text-align: left;
  }
</style>
