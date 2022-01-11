<template>
  <div id="app1">
    <!--    搜索区-->
    <a-row :gutter="24">
      <a-col :md="6" :sm="12">
        <a-form-item :label-col="{ span: 4 }" :wrapper-col="{ span: 20 }" label="团名">
          <a-input v-model="teamName" placeholder="请输入团名"></a-input>
        </a-form-item>
      </a-col>

      <a-col :md="6" :sm="12">
        <a-form-item :label-col="{ span: 6 }" :wrapper-col="{ span: 18 }" label="yy频道">
          <a-input v-model="yyChannel" placeholder="请输入yy频道"></a-input>
        </a-form-item>
      </a-col>


      <template v-if="toggleSearchStatus">
        <a-col :md="6" :sm="12">
          <a-form-item :label-col="{ span: 4 }" :wrapper-col="{ span: 20 }" label="qq群">
            <a-input v-model="qqGroup" placeholder="请输入qq群"></a-input>
          </a-form-item>
        </a-col>
      </template>

      <a-col :md="6" :sm="8">
        <a-form-item>
            <span style="float: left;overflow: hidden;">
              <a-button type="primary" @click="search" icon="search">查询</a-button>
              <a-button type="primary" @click="reset" icon="reload" style="margin-left: 8px">重置</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? '收起' : '展开' }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
              </a>
            </span>
        </a-form-item>
      </a-col>
    </a-row>

    <a-row :gutter="24" type="flex">
      <a-col :span="8">
        <a-space>
          <a-button type="primary" @click="showHmdModal()">
            新增
          </a-button>
        </a-space>
      </a-col>
    </a-row>

    <!--    表格-->
    <a-row style="margin-top: 10px">
      <a-table :columns="columns" :data-source="data" :pagination="pagination" :bordered="true">
        <template slot="teamName" slot-scope="text, record">
          <a @click="showDetail(record)">{{ text }}</a>
        </template>

        <template slot="action" slot-scope="text, record">
          <a-space>
            <a @click="showHmdModal(record)">修改</a>
            <a-popconfirm placement="topLeft"
                          title="真的要删除吗"
                          ok-text="确定"
                          cancel-text="取消"
                          @confirm="deleteHmdTeam(record)">
              <a href="#">删除</a>
            </a-popconfirm>
          </a-space>
        </template>
      </a-table>
    </a-row>

    <!--    增加修改弹框-->
    <hmd-personal-modal v-if="visible===true" :visible="visible" :close="close"
                        :row-id="selectRowId"></hmd-personal-modal>

    <!--    抽屉-->
    <a-drawer
        title="详情"
        :width="720"
        :visible="drawerVisible"
        :body-style="{ paddingBottom: '80px' }"
        @close="drawerClose"
    >
      <hmd-team-drawer-content v-if="drawerVisible" :row-id="selectRowId"></hmd-team-drawer-content>
    </a-drawer>

  </div>
</template>

<script type="module">


const columns = [
  {
    title: '序号',
    dataIndex: 'colIndex',
    width: 65,
    customRender: (text, record, index) => `${index + 1}`,
  },
  {
    title: '区服',
    dataIndex: 'serverName',
  },
  {
    title: '团名',
    dataIndex: 'teamName',
    scopedSlots: {customRender: 'teamName'},
  },
  {
    title: 'yy频道',
    dataIndex: 'yyChannel',
  },
  {
    title: 'qq群',
    dataIndex: 'qqGroup',
  },
  //太长了，不好显示
  // {
  //   title: '原因',
  //   dataIndex: 'reason',
  // },
  // {
  //   title: '评价',
  //   dataIndex: 'evaluate',
  // },
  // {
  //   title: '备注',
  //   dataIndex: 'remark',
  // },
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
    'hmdPersonalModal': httpVueLoader(urlPrefix + '/template/jx3/hmdTeamModal.vue'),
    'hmdTeamDrawerContent': httpVueLoader(urlPrefix + '/template/jx3/hmdTeamDrawerContent.vue')
  },
  data() {
    return {
      toggleSearchStatus: false,
      drawerVisible: false,
      teamName: null,
      yyChannel: null,
      qqGroup: null,
      columns: columns,
      visible: false,
      selectRowId: null,
      data: [],
      pagination: {
        pageNo: 1,
        pageSize: 10, // 默认每页显示数量
        showSizeChanger: true, // 显示可改变每页数量
        pageSizeOptions: ["10", "20", "50", "100"], // 每页数量选项
        showTotal: total => `共${total}条`, // 显示总数
        onShowSizeChange: (current, pageSize) => {
          this.onShowSizeChange(current, pageSize)
        }, // 改变每页数量时更新显示
        onChange: (pageNumber) => {
          this.onPageChange(pageNumber)
        }, //点击页码事件
        total: 0, //总条数
        current: 0,
        buildOptionText: pageSizeOptions => `${pageSizeOptions.value}条/页`,
        // size: "small",
      },
    }
  },
  created: function () {
    this.initTable()

  },
  methods: {
    drawerClose() {
      this.drawerVisible = false
      this.selectRowId = null
    },
    showDetail(record) {
      this.selectRowId = record.id
      this.drawerVisible = true
    },
    handleToggleSearch() {
      this.toggleSearchStatus = !this.toggleSearchStatus
    },
    reset() {
      this.teamName = null
      this.yyChannel = null
      this.qqGroup = null
      this.getTableData(1, this.pagination.pageSize)
    },
    search() {
      this.getTableData(1, this.pagination.pageSize)
    },
    close(refreshtable) {
      this.visible = false
      this.selectRowId = null
      if (refreshtable) {
        this.getTableData(this.pagination.pageNo, this.pagination.pageSize)
      }
    },
    async deleteHmdTeam(record) {
      let result = await httpPost(jx3UrlSetting['hmdTeamDelete'], {id: record.id})
      if (result && result.code === "200") {
        this.$message.success(result.message);
        this.getTableData(this.pagination.pageNo, this.pagination.pageSize)
      } else {
        this.$message.error(result.message);
      }
    },
    showHmdModal(record) {
      if (record) {
        this.selectRowId = record.id
      }
      this.visible = true;
    },
    initTable() {
      this.getTableData(this.pagination.pageNo, this.pagination.pageSize)
    },

    async getTableData(current, pageSize) {
      console.log({
        page: current,
        pageSize: pageSize,
        teamName: this.teamName === '' ? null : this.teamName,
        yyChannel: this.yyChannel === '' ? null : this.yyChannel,
        qqGroup: this.qqGroup === '' ? null : this.qqGroup,
      })
      let result = await httpGet(jx3UrlSetting['hmdTeamList'], {
        page: current,
        pageSize: pageSize,
        teamName: this.teamName === '' ? null : this.teamName,
        yyChannel: this.yyChannel === '' ? null : this.yyChannel,
        qqGroup: this.qqGroup === '' ? null : this.qqGroup,
      });

      if (result && result.code === "200") {
        let resultData = result.returnData
        this.data = resultData.records;
        this.pagination.total = resultData.total
        this.pagination.current = resultData.current

      } else {
        this.$message.error(result.message);
      }
    },
    onShowSizeChange(current, pageSize) {
      this.pagination.current = current
      this.pagination.pageSize = pageSize
      this.getTableData(current, pageSize)
    },

    onPageChange(pageNumber) {
      this.pagination.current = pageNumber
      this.getTableData(pageNumber, this.pagination.pageSize)
    },
  },
}
</script>

<style scoped="scss">

</style>
