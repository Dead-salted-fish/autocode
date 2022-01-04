<template>
  <div id="app1">
    <a-row :gutter="24">
      <a-col :md="6" :sm="12">
        <a-form-item :label-col="{ span: 4 }" :wrapper-col="{ span: 20 }" label="角色名">
          <a-input v-model="roleName"/>
        </a-form-item>
      </a-col>

      <a-col :md="6" :sm="12">
        <a-form-item :label-col="{ span: 4 }" :wrapper-col="{ span: 20 }" label="uid">
          <a-input v-model="uid"/>
        </a-form-item>
      </a-col>

      <a-col :span="4">
        <a-form-item>
          <a-space style="float: right">
            <a-button type="primary" @click="reset">
              重置
            </a-button>
            <a-button type="primary" @click="search">
              查询
            </a-button>
          </a-space>
        </a-form-item>
      </a-col>
    </a-row>

    <a-row :gutter="24" type="flex">
      <a-col :span="8">
        <a-space>
          <a-button type="primary" @click="showHmdModal()">
            新增
          </a-button>
          <a-button type="primary" @click="test()">
            test route
          </a-button>
        </a-space>
      </a-col>
    </a-row>

    <a-row style="margin-top: 10px">
      <a-table :columns="columns" :data-source="data" :pagination="pagination" :bordered="true">
        <template slot="action" slot-scope="text, record">
          <a-space>
            <a @click="showHmdModal(record)">修改</a>
            <a @click="deleteHmdRole(record)">删除</a>
          </a-space>
        </template>
      </a-table>
    </a-row>

    <hmd-personal-modal v-if="visible===true" :visible="visible" :close="close"
                        :role="hmdModelParam"></hmd-personal-modal>
  </div>

</template>

<script type="module">


const columns = [
  {
    title: '序号',
    dataIndex: 'colIndex',
    customRender: (text, record, index) => `${index + 1}`,
  },
  {
    title: '区服',
    dataIndex: 'serverName',
  },
  {
    title: '角色名',
    dataIndex: 'roleName',
  },
  {
    title: '角色',
    dataIndex: 'roleType',
  },
  {
    title: 'uid',
    dataIndex: 'uid',
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
    'hmdPersonalModal': httpVueLoader('/template/jx3/hmdPersonalModal.vue')
  },
  data() {
    return {
      roleName: null,
      uid: null,
      columns: columns,
      visible: false,
      hmdModelParam: null,
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
    test(){
      this.$router.push({path:'/autoCode'});
    },
    reset() {
      this.roleName = null
      this.uid = null
      this.getTableData(1, this.pagination.pageSize)
    },
    search() {
      this.getTableData(1, this.pagination.pageSize)
    },
    close(refreshtable) {
      this.visible = false
      if (refreshtable) {
        this.getTableData(this.pagination.pageNo, this.pagination.pageSize)
      }
    },
    async deleteHmdRole(record) {
      let result = await httpPost(jx3UrlSetting['hmdPersonalDelete'], {id: record.id})
      if (result && result.code === "200") {
        this.$message.success(result.message);
        this.getTableData(this.pagination.pageNo, this.pagination.pageSize)
      } else {
        this.$message.error(result.message);
      }
    },
    showHmdModal(record) {
      this.hmdModelParam = record
      this.visible = true;
    },
    initTable() {
      this.getTableData(this.pagination.pageNo, this.pagination.pageSize)
    },

    async getTableData(current, pageSize) {
      let result = await httpGet(jx3UrlSetting['hmdPersonalList'], {
        page: current,
        pageSize: pageSize,
        roleName: this.roleName === '' ? null : this.roleName,
        uid: this.uid === '' ? null : this.uid,
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
