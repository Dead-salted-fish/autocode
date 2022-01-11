<template>
  <a-descriptions  bordered :column="2" >
    <a-descriptions-item label="区服">{{ roleDetail.serverName }}</a-descriptions-item>
    <a-descriptions-item label="uid">{{ roleDetail.uid }}</a-descriptions-item>
    <a-descriptions-item label="角色名">{{ roleDetail.roleName }}</a-descriptions-item>
    <a-descriptions-item label="角色">{{ roleDetail.roleType }}</a-descriptions-item>
    <a-descriptions-item label="原因" :span="2">{{ roleDetail.reason }}</a-descriptions-item>
    <a-descriptions-item label="评价" :span="2">{{ roleDetail.evaluate }}</a-descriptions-item>
    <a-descriptions-item label="备注" :span="2">{{ roleDetail.remark }}</a-descriptions-item>
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
      roleDetail: {
        serverName: '',
        roleName: '',
        roleType: '',
        uid: '',
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
      if (this.rowId) {
        let result = await httpGet(jx3UrlSetting['getHmdPersonalById'], {id: this.rowId})
        if (result && result.code === "200") {
          this.roleDetail = result.returnData
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