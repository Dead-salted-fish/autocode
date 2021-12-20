<template>
  <div id="app1">
    <a-card :bordered="false">
      <a-row :gutter="24">
        <a-col :span="8">
          <a-form-item :label-col="{ span: 4 }" :wrapper-col="{ span: 20 }" label="表名">
            <a-input style="width: 80%" v-model="searchTableName"/>
          </a-form-item>
        </a-col>
        <a-col :span="8" :offset="8" >
          <a-space style="float: right">
            <a-button type="primary" @click="resetTableName" >
              重置
            </a-button>
            <a-button type="primary" @click="search" >
              查询
            </a-button>
          </a-space>
        </a-col>
      </a-row>
    </a-card>

    <a-row>
      <a-card :bordered="false">
        <a-table :columns="columns" :data-source="data" :pagination="pagination" :bordered="true">
          <template slot="action" slot-scope="text, record">
            <span>
                 <a @click="showGenerateModal(record)">生成</a>
           </span>

          </template>

        </a-table>
      </a-card>
    </a-row>
    <gengrate v-if="visible===true" :visible="visible" :close="close" :table-name="tableName"></gengrate>
  </div>

</template>

<script>

module.exports = {
  props: {},
  components: {
    'gengrate': httpVueLoader('/template/generate.vue')
  },
  data() {
    return {
      searchTableName: null,
      columns: [
        {
          title: '序号',
          dataIndex: 'colIndex',
          customRender: (text, record, index) => `${index + 1}`,
        },
        {
          title: '表名',
          dataIndex: 'tableName',
          key: 'age',
        },
        {
          title: 'Action',
          key: 'action',
          scopedSlots: {customRender: 'action'},
        },
      ],
      visible: false,
      tableName: null,
      data: [{
        tableName: 'tableName1',
      }],
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
    resetTableName() {
      this.searchTableName = null
      this.getTableData(1, 10)
    },
    search() {
      this.getTableData(1, 9999)
    },
    close() {
      this.visible = false
    },
    showGenerateModal(record) {
      this.tableName = record.tableName;
      this.visible = true;
      //console.log('visible = true');
    },
    initTable() {
      this.getTableData(this.pagination.pageNo, this.pagination.pageSize)
    },

    async getTableData(current, pageSize) {
      let result = await httpGet('http://localhost:9090/getAllTableName', {
        page: current,
        pageSize: pageSize,
        tableName: this.searchTableName
      });

      if (result && result.code === "200") {

        this.data = result.returnData.records;
        this.pagination.total = result.returnData.total
        this.pagination.current = result.returnData.current
      } else {
        this.$message.error(result.returnData);
      }

    },
    onShowSizeChange(current, pageSize) {
      this.pagination.current = 1
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
