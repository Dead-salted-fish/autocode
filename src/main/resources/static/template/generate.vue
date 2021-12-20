<template>
  <a-modal
      :title="tableName"
      :visible="visible"
      cancel-text="取消"
      ok-text="确认生成"
      @ok="handleOk"
      @cancel="handleCancel"
      width="80%"
      height="80%"
  >

    <a-card>
      <a-form-item>
        <a-table :columns="columns"
                 :data-source="data"
                 :pagination="false "
                 :row-selection="{ selectedRowKeys: selectedRowKeys, onChange: onTableSelectChange }"
                 bordered style="width: 100%">
          <template
              v-for="col in ['remark']"
              :slot="col"
              slot-scope="text, record, index"
          >
            <div :key="col">
              <a-input
                  style="margin: -5px 0"
                  :value="text"
                  @change="e => handleChange(e.target.value, record.rowKey,col)"
              />

            </div>
          </template>

          <template slot="isNull" slot-scope="text, record, index">
            <div class="editable-row-operations">
              <template>
                <a-radio-group name="radioGroup" :default-value="record.canNullable"
                               @change="e=>redioGroupChange(e.target.value, record.rowKey)">
                  <a-radio :value="true">
                    true
                  </a-radio>
                  <a-radio :value="false">
                    false
                  </a-radio>
                </a-radio-group>
              </template>
            </div>
          </template>

          <template slot="javaTypeSelect" slot-scope="text, record, index">
            <div class="editable-row-operations">
              <template>
                <a-select
                    show-search
                    style="width: 200px"
                    :get-popup-container="triggerNode => {
                              return triggerNode.parentNode || document.body}"
                    :default-value="record.javaType"
                    @select="(valuea,option)=>{onSelectChange(valuea,option,record.rowKey)}"
                >
                  <a-select-option v-for="item in javaType" :value="item">{{ item }}</a-select-option>

                </a-select>
              </template>
            </div>
          </template>

          <template slot="queryDtoCheckbox" slot-scope="text, record, index">
            <div class="editable-row-operations">
              <a-checkbox @change="e=>{queryDtoCheckbox(e,record.rowKey)}" q>

              </a-checkbox>
            </div>
          </template>

          <template slot="dtoCheckbox" slot-scope="text, record, index">
            <div class="editable-row-operations">
              <a-checkbox @change="e=>{onDtoCheckboxChange(e,record.rowKey)}"
                          :default-checked="dtoCheckboxChecked(record)">

              </a-checkbox>
            </div>
          </template>
        </a-table>
      </a-form-item>

      <a-form-item>
        <a-checkbox @change="onSwaggerCheckboxChange" :default-checked="swaggerAnnotation">
          swagger注解
        </a-checkbox>
        <a-checkbox @change="onNormalCheckboxChange" :default-checked="normalAnnotation">
          普通注解
        </a-checkbox>
      </a-form-item>

      <a-form-item>
        <a-space>
<!--          <a-button type="primary" @click="() => test()">-->
<!--            test-->
<!--          </a-button>-->
<!--          <a-button type="primary" @click="generateCode">-->
<!--            生成-->
<!--          </a-button>-->

        </a-space>
      </a-form-item>
    </a-card>

  </a-modal>
</template>

<script>

module.exports = {
  props: {
    visible: {
      type: Boolean,
      default: false
    },
    close: {
      type: Function,
    },
    tableName: {
      type: String,
    }
  },
  data: function () {
    return {
      data: [],
      javaType: [],
      editingKey: '',
      treeExpandedKeys: [],
      value: undefined,
      selectedRowKeys: [],
      swaggerAnnotation: false,
      normalAnnotation: true,
      columns: [
        {
          title: 'rowKey',
          dataIndex: 'rowKey',

        },
        {
          title: '字段名',
          dataIndex: 'columnName',
        },
        {
          title: '数据类型',
          dataIndex: 'dataType',
          width: '100px',
        },
        {
          title: 'java类型',
          dataIndex: 'javaType',
          scopedSlots: {customRender: 'javaTypeSelect'},
        },
        {
          title: '索引类型',
          dataIndex: 'columnKey',
          width: '100px',
        },
        {
          title: '可否为null',
          dataIndex: 'canNullable',
          scopedSlots: {customRender: 'isNull'},
        },
        {
          title: '作为queryDto属性',
          dataIndex: '作为queryDtoAttribute',
          scopedSlots: {customRender: 'queryDtoCheckbox'},
          width: '13%',
        },
        {
          title: '作为dto属性',
          dataIndex: 'dtoAttribute',
          scopedSlots: {customRender: 'dtoCheckbox'},
          width: '10%',
        },
        {
          title: '字段注释',
          dataIndex: 'remark',
          width: '20%',
          scopedSlots: {customRender: 'remark'},
        },
        // {
        //     title: 'operation',
        //     dataIndex: 'operation',
        //     width: '210px',
        //     scopedSlots: {customRender: 'operation'},
        // },
      ],

    }
  },
  created: function () {
    this.getTableMetaData();
    this.getAllJavaType();
    console.log(this.tableName);
  },
  methods: {
    onNormalCheckboxChange(e) {
      this.normalAnnotation = e.target.checked
    },
    onSwaggerCheckboxChange(e) {
      this.swaggerAnnotation = e.target.checked
    },
    dtoCheckboxChecked(record) {
      let columnName = record.columnName
      if (columnName !== 'version' && columnName !== 'creator' && columnName !== 'create_time' && columnName !== 'update_by' && columnName !== 'update_time') {
        return record.dtoAttribute
      } else {
        return false
      }
    },
    onDtoCheckboxChange(e, rowKey) {
      console.log(`checked dto = ${e.target.checked}`);
      console.log('rowKey ', rowKey);
      const newData = [...this.data];
      const target = newData.filter(item => rowKey === item.rowKey)[0];
      if (target) {
        target['dtoAttribute'] = e.target.checked;
        this.data = newData;
      }
    },
    queryDtoCheckbox(e, rowKey) {
      console.log(`checked do = ${e.target.checked}`);
      console.log('rowKey ', rowKey);
      const newData = [...this.data];
      const target = newData.filter(item => rowKey === item.rowKey)[0];
      if (target) {
        target['queryDtoAttribute'] = e.target.checked;
        this.data = newData;
      }
    },
    async generateCode() {
      let success = false
      let attributeDate = [];
      this.selectedRowKeys.forEach(key => {
        const newData = [...this.data];
        const target = newData.filter(item => key + 1 === item.rowKey)[0];
        if (target) {
          attributeDate.push(target)
        }
      })
      let returnDate = {
        attributeDate: attributeDate,
        tableName: this.tableName,
        swagger: this.swaggerAnnotation,
        normalAnnotation: this.normalAnnotation,
      }
      let result = await httpPost('http://localhost:9090/generateCode', returnDate)
      if (result && result.code === "200") {
        success = true
        this.$message.success('成功');
      } else {
        this.$message.error(result.returnData);
      }
       return success;
    },
    onTableSelectChange(selectedRowKeys) {
      console.log('selectedRowKeys changed: ', selectedRowKeys);
      this.selectedRowKeys = selectedRowKeys;
    },
    onSelectChange(value, option, rowKey) {
      const newData = [...this.data];
      const target = newData.filter(item => rowKey === item.rowKey)[0];
      if (target) {
        target['javaType'] = value;
        this.data = newData;
      }
    },
    redioGroupChange(value, rowKey) {
      const newData = [...this.data];
      const target = newData.filter(item => rowKey === item.rowKey)[0];
      if (target) {
        target['canNullable'] = value;
        this.data = newData;
      }
    },
    test() {
      console.log(this.data);
    },
    handleChange(value, rowKey, col) {
      const newData = [...this.data];
      const target = newData.filter(item => rowKey === item.rowKey)[0];
      if (target) {
        target[col] = value;
        this.data = newData;
      }
    },
    async getTableMetaData() {
      let result = await httpGet('http://localhost:9090/getTableMetaData', {tableName: this.tableName})
      if (result && result.code === "200") {
        this.data = result.returnData
        this.data.forEach((item, index) => {
          if (item.columnName !== 'version' && item.columnName !== 'creator' && item.columnName !== 'create_time' && item.columnName !== 'update_by' && item.columnName !== 'update_time') {
            this.selectedRowKeys.push(index)
          }
        })
      } else {
        this.$message.error(result.returnData);
      }
    },

    async getAllJavaType() {
      let result = await httpGet('http://localhost:9090/getAllJavaType')
      if (result && result.code === "200") {
        this.javaType = result.returnData
      } else {
        this.$message.error(result.returnData);
      }

    },

     async handleOk(e) {
    let result =  await this.generateCode()
       if(result) {
         this.close()
       }
    },
    handleCancel(e) {
      this.close()
    },
  }
}
</script>

<style>
.hello {
  background-color: #ffe;
}
</style>