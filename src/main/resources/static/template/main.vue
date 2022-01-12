<template>
  <div style="height: 100%">
    <a-layout id="components-layout-demo-custom-trigger" style="height: 100%">
      <a-layout-sider v-model="collapsed" :trigger="null" collapsible width="240">
        <div class="logo"></div>
        <a-menu theme="dark" mode="inline" :default-selected-keys="['1']">
          <template v-for="menu in menus">
            <a-menu-item v-if="!menu.children" :key="menu.menuName">
              <router-link :to="menu.routerPath">{{ menu.title }}</router-link>
            </a-menu-item>
            <sub-menu v-else :menu-info="menu"></sub-menu>
          </template>
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
const SubMenu = {
  template: `
    <a-sub-menu key="1" v-bind="$props" v-on="$listeners">
    <span slot="title">
          <span>{{ menuInfo.title }}</span>
        </span>
    <template v-for="menu in menuInfo.children">
      <a-menu-item v-if="!menu.children" :key="menu.menuName">
        <router-link :to="menu.routerPath">{{ menu.title }}</router-link>
        -->
      </a-menu-item>
      <sub-menu v-else :menu-info="menu"></sub-menu>
    </template>
    </a-sub-menu>
  `,
  isSubMenu: true,
  props: {
    ...Menu.SubMenu.props,
    // Cannot overlap with properties within Menu.SubMenu.props
    menuInfo: {
      type: Object,
      default: () => ({}),
    },
  }
};

Vue.component('sub-menu', {
  functional: true,
  props: {
    menuInfo: {
      type: Object,
      default: () => ({}),
    },
  },
  isSubMenu: true,
  render: function (createElement, context) {
    console.log('this.menuInfo', this.menuInfo)
    console.log('context', context)

    function appropriateListComponent() {
      return SubMenu
    }

    return createElement(
        appropriateListComponent()
        , {
          props: context.props
        },
    )
  }
})


module.exports = {
  components: {},
  data() {
    return {
      collapsed: false,
      menus: [],
    }
  },
  created: function () {
    this.getRoute()
    createWebSocket()
  },
  methods: {
    username() {

      console.log(this.$route.params)

    },
    async getRoute() {
      let result = await httpGet(appUrlSetting['getRoute'])
      if (result && result.code === '200') {
        this.menus = result.returnData
      } else {
        this.$message.error(result.message);
      }
    }
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