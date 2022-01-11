<template>
  <a-modal
      :title="rowId?formValue.roleName:'新增'"
      :visible="visible"
      cancel-text="取消"
      ok-text="确认"
      @ok="handleOk"
      @cancel="handleCancel"
      width="40%"
      height="40%"
      :closable="false"
  >

    <a-form-model ref="hmdPersonalForm" v-bind="layout" :model="formValue">
      <a-row :gutter="24" type="flex" justify="center">

        <a-col :span="24">
          <a-form-model-item
              label="区服"
              prop="serverName"
              :rules="{ required:true,message:'请输入区服'}"
          >
            <a-input v-model="formValue.serverName" autocomplete="off"/>
          </a-form-model-item>
        </a-col>

        <a-col :span="24">
          <a-form-model-item
              label="团名"
              prop="teamName"
              :rules="{ required:true,message:'请输入角色名'}"
          >
            <a-input v-model="formValue.teamName" autocomplete="off"/>
          </a-form-model-item>
        </a-col>

        <a-col :span="24">
          <a-form-model-item
              label="yy频道"
              prop="yyChannel"
              :rules="{ required:false,message:'请输入角色'}"
          >
            <a-input v-model="formValue.yyChannel" autocomplete="off"/>
          </a-form-model-item>
        </a-col>

        <a-col :span="24">
          <a-form-model-item
              label="qq群"
              prop="qqGroup"
              :rules="{ required:false,message:'请输入uid'}"
          >
            <a-input v-model="formValue.qqGroup" autocomplete="off"/>
          </a-form-model-item>
        </a-col>

        <a-col :span="24">
          <a-form-model-item
              label="原因"
              prop="reason"
              :rules="{ required:false,message:'请输入原因'}"
          >
            <a-input v-model="formValue.reason" type="textarea" autocomplete="off"/>
          </a-form-model-item>
        </a-col>

        <a-col :span="24">
          <a-form-model-item
              label="评价"
              prop="evaluate"
              :rules="{ required:false,message:'请输入评价'}"
          >
            <a-input v-model="formValue.evaluate" type="textarea" autocomplete="off"/>
          </a-form-model-item>
        </a-col>

        <a-col :span="24">
          <a-form-model-item
              label="备注"
              prop="remark"
              :rules="{ required:false,message:'请输入备注'}"
          >
            <a-input v-model="formValue.remark" type="textarea" autocomplete="off"/>
          </a-form-model-item>
        </a-col>
      </a-row>
    </a-form-model>


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
    rowId: {
      type: Number,
      default: null
    }
  },
  data: function () {
    return {
      layout: {
        labelCol: {span: 6},
        wrapperCol: {span: 14},
      },
      formValue: {
        serverName: '',
        teamName: '',
        yyChannel: '',
        qqGroup: '',
        reason: '',
        evaluate: '',
        remark: ''
      }
    }
  },
  created: function () {
    this.initForm()
  },
  methods: {
    async initForm(){
      if(this.rowId){
      let result = await httpGet(jx3UrlSetting['getHmdTeamById'],{id:this.rowId})
        if (result && result.code === "200") {
          this.formValue= result.returnData
        } else {
          this.$message.error(result.message);
        }
      }
    },
     handleOk(e) {
      this.$refs['hmdPersonalForm'].validate(async(valid) => {
        if (valid) {
          if(!this.rowId){
            result  = await httpPost(jx3UrlSetting['hmdTeamAdd'], this.formValue)
          }else {
            result  = await httpPost(jx3UrlSetting['hmdTeamUpdate'], this.formValue)
          }
          if (result && result.code === "200") {
            this.$message.success(result.message);
            this.close(true)
          }else {
            this.$message.error(result.message);
          }
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    handleCancel(e) {
      this.close(false)
    },
  }
}
</script>

<style>
.hello {
  background-color: #ffe;
}
</style>