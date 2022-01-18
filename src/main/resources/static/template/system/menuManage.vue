<template>
  <div id="app1">

    <a-row :gutter="24" type="flex">
      <a-col :span="8">
        <a-space>
          <a-button type="primary" @click="showHmdDrawer()">
            新增
          </a-button>
        </a-space>
      </a-col>
    </a-row>

    <!--    表格-->
    <a-row style="margin-top: 10px">
      <a-table :columns="columns" :data-source="menus" :bordered="false">
        <template slot="action" slot-scope="text, record">
          <a-space>
            <a @click="showHmdDrawer(record)">编辑</a>
            <a-dropdown :trigger="['click']">
              <a class="ant-dropdown-link" @click="e => e.preventDefault()">
                更多 <a-icon type="down" />
              </a>
              <a-menu slot="overlay">
<!--                <a-menu-item key="0">-->
<!--                  <a>添加下级</a>-->
<!--                </a-menu-item>-->

                <a-menu-item key="1">
                  <a-popconfirm placement="topLeft"
                                title="真的要删除吗"
                                ok-text="确定"
                                cancel-text="取消"
                                @confirm="deleteMenu(record)">
                    <a href="#">删除</a>
                  </a-popconfirm>
                </a-menu-item>
              </a-menu>
            </a-dropdown>
          </a-space>
        </template>
      </a-table>
    </a-row>

    <!--    抽屉-->
    <a-drawer
        title="详情"
        :width="680"
        :visible="drawerVisible"
        :body-style="{ paddingBottom: '80px' }"
        @close="drawerClose(false)"
    >
   <menu-manage-drawer v-if="drawerVisible" :menu="selectRowRecord" :close="drawerClose"></menu-manage-drawer>
    </a-drawer>

  </div>
</template>

<script type="module">


const columns = [
  {
    title: '菜单名称',
    dataIndex: 'title',
  },
  {
    title: '菜单类型',
    dataIndex: 'menuType',
  },
  {
    title: '路由地址',
    dataIndex: 'routerPath',
  },
  {
    title: '组件物理地址',
    dataIndex: 'componentPath',
  },
  {
    title: '排序',
    dataIndex: 'sort',
  },
  {
    title: '操作',
    dataIndex: 'action',
    align: 'center',
    scopedSlots: {customRender: 'action'},
  }
]

module.exports = {
  props: {},
  components: {
    'menuManageDrawer': httpVueLoader(urlPrefix + '/template/system/menuManageDrawer.vue'),
  },
  data() {
    return {
      columns: columns,
      drawerVisible: false,
      selectRowRecord: null,
      menus: [],
    }
  },
  created: function () {
    this.initTable()

  },
  methods: {
   async deleteMenu(record){
      let result = await httpPost(systemUrlSetting['deleteMenu'],{id:record.id});
      if (result && result.code === "200") {
        this.getMenus()
      } else {
        this.$message.error(result.message);
      }
    },
    drawerClose(refresh) {
      this.drawerVisible = false
      this.selectRowRecord = null
      if(refresh){
        this.getMenus()
      }
    },
    showHmdDrawer(record) {
      if (record) {
        this.selectRowRecord = record
      }
      this.drawerVisible = true;
    },

    initTable() {
      this.getMenus()
    },

    setRowKey(data){
      if (data) {
        data.forEach(item => {
          item['key'] = item.id;
          if (item.children) {
            this.setRowKey(item.children);
          }
        });
      }
    },

    async getMenus() {
      let result = await httpGet(systemUrlSetting['getMenusList']);

      if (result && result.code === "200") {
        this.setRowKey(result.returnData)
        this.menus = result.returnData
      } else {
        this.$message.error(result.message);
      }
    },

  },
}
</script>

<style scoped="scss">

</style>
