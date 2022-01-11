<template>
  <a-descriptions  bordered :column="2" >
    <a-descriptions-item label="区服">{{ teamDetail.serverName }}</a-descriptions-item>
    <a-descriptions-item label="团名">{{ teamDetail.teamName }}</a-descriptions-item>
    <a-descriptions-item label="yy频道">{{ teamDetail.yyChannel }}</a-descriptions-item>
    <a-descriptions-item label="qq群">{{ teamDetail.qqGroup }}</a-descriptions-item>
    <a-descriptions-item label="原因" :span="2">{{ teamDetail.reason }}</a-descriptions-item>
    <a-descriptions-item label="评价" :span="2">{{ teamDetail.evaluate }}</a-descriptions-item>
    <a-descriptions-item label="备注" :span="2">{{ teamDetail.remark }}</a-descriptions-item>
  </a-descriptions>
</template>

<script>

module.exports = {
  props: {
    rowId: {
      type: Number,
      default: null
    }
  },
  data: function () {
    return {
      teamDetail: {
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
    this.init()
  },
  methods: {
    async init() {
      if(this.rowId){
        let result = await httpGet(jx3UrlSetting['getHmdTeamById'],{id:this.rowId})
        if (result && result.code === "200") {
          this.teamDetail= result.returnData
        } else {
          this.$message.error(result.message);
        }
      }
    }
  }
}
</script>

<style>
.hello {
  background-color: #ffe;
}
</style>