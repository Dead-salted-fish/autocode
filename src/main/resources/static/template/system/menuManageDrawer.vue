<template>
  <div>
    <a-form
        id="components-form-demo-validate-other"
        :form="form"
        v-bind="formItemLayout"
    >
      <a-form-item label="菜单级别">
        <a-radio-group v-decorator="['menuLeavel', { initialValue: 1}]" @change="menuLeavelChange">
          <a-radio :value="1">
            主菜单
          </a-radio>
          <a-radio :value="2">
            子菜单
          </a-radio>
        </a-radio-group>
      </a-form-item>

      <a-form-item label="父菜单" v-if="menuLeavel === 2">
        <a-tree-select
            v-decorator="['parentMenu', { rules: [{ required: true, message: '请选择父菜单' }] }]"
            show-search
            style="width: 100%"
            :dropdown-style="{ maxHeight: '400px', overflow: 'auto' }"
            placeholder="请选择父菜单"
            allow-clear
            :tree-data="parentMenus"
        >
        </a-tree-select>
      </a-form-item>

      <a-form-item label="菜单标识名">
        <a-input
            v-decorator="['menuName',{ rules: [{ required: true, message: '请输入菜单标识名' }]},]"
            placeholder="菜单栏的key"
        >
        </a-input>
      </a-form-item>

      <a-form-item label="菜单名称">
        <a-input
            v-decorator="['title',{ rules: [{ required: true, message: '请输入菜单名称' }]},]"
            placeholder="菜单名称"
        >
        </a-input>
      </a-form-item>

      <a-form-item label="菜单类型">
        <a-input
            v-decorator="['menuType',{ rules: [{ required: true, message: '请输入菜单类型' }]},]"
            placeholder="菜单类型"
        >
        </a-input>
      </a-form-item>

      <a-form-item label="路由地址">
        <a-input
            v-decorator="['routerPath',{ rules: [{ required: true, message: '请输入路由地址' }]},]"
            placeholder="路由地址"
        >
        </a-input>
      </a-form-item>

      <a-form-item label="组件物理地址">
        <a-input
            v-decorator="['componentPath',{ rules: [{ required: true, message: '请输入组件物理地址' }]},]"
            placeholder="组件物理地址"
        >
        </a-input>
      </a-form-item>

      <a-form-item label="排序">
        <a-input
            v-decorator="['sort',{ rules: [{ required: true, message: '请输入序号' }]},]"
            placeholder="排序"
        >
        </a-input>
      </a-form-item>

      <a-form-item label="是否启用">
        <a-switch v-decorator="['ban', { initialValue: true,valuePropName: 'checked'}]"/>
      </a-form-item>
    </a-form>
    <div
        :style="{
          position: 'absolute',
          right: 0,
          bottom: 0,
          width: '100%',
          borderTop: '1px solid #e9e9e9',
          padding: '10px 16px',
          background: '#fff',
          textAlign: 'right',
          zIndex: 1,
        }"
    >
      <a-button :style="{ marginRight: '8px' }" @click="closeDrawer()">
        取消
      </a-button>
      <a-button type="primary" @click="submit()">
        确认
      </a-button>
    </div>
  </div>
</template>

<script type="module">

module.exports = {
  props: {
    menu: {
      type: Object,
      default: null
    },
    close:{
      type: Function,
      default: null
    }
  },
  components: {},
  data() {
    return {
      formItemLayout: {
        labelCol: {span: 6},
        wrapperCol: {span: 14},
      },
      menuLeavel: 1,
      parentMenus: [],
    }
  },
  beforeCreate() {
    this.form = this.$form.createForm(this, {name: 'validate_other'});
  },
  created: function () {
    this.getParentMenusTree()
  },
  methods: {
    loop(tree) {
      if (tree) {
        tree.forEach(item => {
          item.label = item.title;
          item.value = item.key;
          item['selectable'] = true;
          if (item.children) {
            this.loop(item.children);
          }
        });
      }
    },
    async getParentMenusTree() {
      let result = await httpGet(systemUrlSetting['getParentMenusTree'])
      if (result && result.code === "200") {
        this.loop(result.returnData)
        this.parentMenus = result.returnData
      } else {
        this.$message.error(result.message);
      }
    },
    menuLeavelChange(e) {
      this.menuLeavel = e.target.value
    },
    closeDrawer() {
      this.close&&this.close(false)
    },
    submit() {
      this.form.validateFields(async (err, values) => {
        if (!err) {
          values.ban = values.ban?'0':'1'
          values.parentMenuId = values.menuLeavel===1?'0':values.parentMenu
          console.log(values)
          let result = null
          if (this.menu) {

          } else {
             result = await httpPost(systemUrlSetting['addMenu'], values)
          }
          if (result && result.code === "200") {
            this.$message.success(result.message);
            this.close&&this.close(true)
          }else {
            this.$message.error(result.message);
          }
        }

      });
    }
  }
}
</script>

<style scoped="scss">
#components-form-demo-validate-other .dropbox {
  height: 180px;
  line-height: 1.5;
}
</style>
