<template>
  <div style="height: 100%">
    <a-layout id="components-layout-demo-custom-trigger" style="height: 100%">
      <a-layout-sider v-model="collapsed" :trigger="null" collapsible width="240">
        <div class="logo"></div>
        <a-menu theme="dark" mode="inline" :default-selected-keys="['1']">
          <a-menu-item key="1">
            <router-link to="/main/autoCode">autoCode</router-link>
          </a-menu-item>

          <a-sub-menu key="jx3">
            <span slot="title"><span>jx3</span></span>
            <a-sub-menu key="jx3_hmd">
              <span slot="title"><span>黑名单</span></span>
              <a-menu-item key="2">
                <router-link to="/main/jx3/hmdPersonal">个人黑名单</router-link>
              </a-menu-item>
              <a-menu-item key="3">
                <router-link to="/main/jx3/hmdTeam">团队黑名单</router-link>
              </a-menu-item>
            </a-sub-menu>
          </a-sub-menu>
        </a-menu>
      </a-layout-sider>
      <a-layout>

        <a-layout-header style="background: #fff; padding: 0">
          <a-icon
              class="trigger"
              :type="collapsed ? 'menu-unfold' : 'menu-fold'"
              @click="() => (collapsed = !collapsed)"
          />
        </a-layout-header>

        <a-layout-content
            :style="{ margin: '24px 16px', padding: '24px', background: '#fff', minHeight: '280px%' }"
        >
          <router-view></router-view>
        </a-layout-content>

      </a-layout>

    </a-layout>
  </div>
</template>

<script>

module.exports = {
  components: {
  },
  data() {
    return {
      collapsed: false,
    }
  },
  created: function () {
    // this.getRoute()
  },
  methods: {
    username() {
      // We will see what `params` is shortly
      console.log(this.$route.params)

    },
    // async getRoute() {
    //   // let result = await httpGet("/getRoute")
    //   // router.addRoute({
    //   //   path: '/autoCode',
    //   //   component: httpVueLoader('/template/autoCodeMain.vue'),
    //   // })
    //   //
    //   // router.addRoute({
    //   //   path: '/',
    //   //   redirect: '/autoCode',
    //   // })
    //   // router.beforeEach((to, from, next) => {
    //   //   if (window.localStorage.getItem("token")) {
    //   //     next()
    //   //   } else {
    //   //     this.$message.error('token过期');
    //   //     window.location.href = '/login'
    //   //   }
    //   // })
    //   router.addRoute({
    //     path: '/jx3/hmdpersonal',
    //     component: httpVueLoader('/template/jx3/hmdPersonal.vue'),
    //   })
    //   router.addRoute({
    //     path: '/jx3/hmdteam',
    //     component: httpVueLoader('/template/jx3/hmdTeam.vue'),
    //   })
    //   console.log('result', result)
    // }
  }
}
</script>

<style scoped="scss">
#components-layout-demo-custom-trigger .trigger {
  font-size: 18px;
  line-height: 64px;
  padding: 0 24px;
  cursor: pointer;
  transition: color 0.3s;
}

#components-layout-demo-custom-trigger .trigger:hover {
  color: #1890ff;
}

#components-layout-demo-custom-trigger .logo {
  height: 32px;
  background: rgba(255, 255, 255, 0.2);
  margin: 16px;
}
</style>