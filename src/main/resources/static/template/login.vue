<template>
  <div class="content_login">
    <div class="content_input">
      <div class="title">
        <p>登录</p>
      </div >
      <div class="login_input">
      <a-input v-model="username" placeholder="用户名"></a-input>
      <a-input-password v-model="password" placeholder="密码"></a-input-password>
      <a-input v-model="captchaInput" placeholder="验证码" style="width: 180px"></a-input>
      <span style="margin-left: 16px" title="点击图片，换一张"><img :src="captcha" @click="reloadCaptcha"/></span>
      </div>
        <div class="content_button login_button">
        <a-button type="primary" @click="login">登录</a-button>
      </div>
    </div>
  </div>
</template>

<script>

module.exports = {
  data() {
    return {
      username: null,
      password: null,
      captcha: null,
      captchaInput: null,
      captchaIdentification: null,
    }
  },
  created: function () {
    this.getCaptcha()

  },
  methods: {
    async login() {
      let user = {
        username: this.username === null || this.username === '' ? null : this.username,
        password: this.password === null || this.password === '' ? null : this.password,
        captcha: this.captchaInput === null || this.captchaInput === '' ? null : this.captchaInput,
        captchaIdentification: this.captchaIdentification
      }
      let result = await httpPost(loginUrlSetting['userlogin'], user)
      if (result && result.code === "200") {
        for (const key in result.returnData) {
          localStorage.setItem(key, result.returnData[key])
        }
        this.$message.success('成功');
        this.$router.push({path:'main'});
      } else {
        console.log(result)
        this.$message.error(result.message);
      }

    },
    async getCaptcha() {
      // this.$http.get('/captcha.jpg', {responseType: 'blob'})
      //     .then((success) => {
      //         this.captcha = window.URL.createObjectURL(success.data);
      //     }, (error) => {
      //         console.log(error);
      //     })
      let result = await httpGet(loginUrlSetting['captcha'],null, this.$router)
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
    }

  }
}
</script>

<style>
.hello {
  background-color: #ffe;
}
</style>