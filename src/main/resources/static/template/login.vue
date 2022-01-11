<template>
  <div class="content_login">
    <div class="content_input">
      <div class="title">
        <p>登录</p>
      </div>
      <div class="login_input">
        <a-form-model ref="loginForm" v-bind="layout" :model="formValue">
          <a-row :gutter="24" type="flex" justify="center">

            <a-col :span="24">
              <a-form-model-item
                  prop="username"
                  :rules="{ required:true,message:'请输入用户名'}"
              >
                <a-input v-model="formValue.username" placeholder="用户名"></a-input>
              </a-form-model-item>
            </a-col>

            <a-col :span="24">
              <a-form-model-item
                  prop="password"
                  :rules="{ required:true,message:'请输入密码'}"
              >
                <a-input-password v-model="formValue.password" placeholder="密码"></a-input-password>
              </a-form-model-item>
            </a-col>

            <a-col :span="15">
              <a-form-model-item
                  prop="password"
                  :rules="{ required:true,message:'请输入验证码'}"
              >
                <a-input v-model="formValue.captchaInput" placeholder="验证码"></a-input>

              </a-form-model-item>
            </a-col>

            <a-col :span="9" style="padding-left: 10px">
              <a-form-model-item
                  prop="password"
              >
                <span  title="点击图片，换一张"><img :src="captcha" @click="reloadCaptcha"/></span>
              </a-form-model-item>
            </a-col>

            <a-col :span="24">
              <div class="content_button login_button">
                <a-button type="primary" @click="login" v-on:keyup.enter.native="login()">登录</a-button>
              </div>
            </a-col>

          </a-row>
        </a-form-model>
      </div>


    </div>
  </div>
</template>

<script>

module.exports = {
  data() {
    return {
      formValue:{
        username: null,
        password: null,
        captchaInput: null,
      },
      captcha: null,
      captchaIdentification: null,
      layout: {
        wrapperCol: {span: 24},
      },
    }
  },
  mounted() {
    // 绑定enter事件
    this.enterKeyup();
  },
  destroyed() {
    // 销毁enter事件
    this.enterKeyupDestroyed();
  },
  created: function () {
    this.getCaptcha()

  },
  methods: {
    async login() {
      this.$refs['loginForm'].validate(async(valid) => {
        if (valid) {
          this.formValue.captchaIdentification= this.captchaIdentification
          let result = await httpPost(loginUrlSetting['userlogin'], this.formValue)
          if (result && result.code === "200") {
            for (const key in result.returnData) {
              localStorage.setItem(key, result.returnData[key])
            }
            this.$message.success('成功');
            this.$router.push({path: 'main'});
          } else {
            console.log(result)
            this.$message.error(result.message);
          }
        } else {
          console.log('error submit!!');
          return false;
        }
      });



    },
    async getCaptcha() {
      // this.$http.get('/captcha.jpg', {responseType: 'blob'})
      //     .then((success) => {
      //         this.captcha = window.URL.createObjectURL(success.data);
      //     }, (error) => {
      //         console.log(error);
      //     })
      let result = await httpGet(loginUrlSetting['captcha'], null, this.$router)
      if (result && result.code === "200") {
        console.log('result', result)
        this.captcha = 'data:image/jpg;base64,' + result.returnData.imgBase64;
        this.captchaIdentification = result.returnData.captchaIdentification;
      } else {
        this.$message.error(result.message);
      }
    },
    reloadCaptcha() {
      this.getCaptcha()
    },
    enterKey(event) {
        const code = event.keyCode
            ? event.keyCode
            : event.which
                ? event.which
                : event.charCode;
        if (code == 13) {
          this.login();
        }
    },
    enterKeyupDestroyed() {
      document.removeEventListener("keyup", this.enterKey);
    },
    enterKeyup() {
      document.addEventListener("keyup", this.enterKey);
    },

  }
}
</script>

<style>
.hello {
  background-color: #ffe;
}
</style>