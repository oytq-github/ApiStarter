<template>
  <div>
    <div>
      <el-row>
        <el-col style="text-align: center;margin-top: 8%;font-size: xx-large;">
          欢迎: <em style="font-weight: 800;"> {{ name }}</em> 使用 ApiStarter后台管理系统
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'Dashboard',
  computed: {
    ...mapGetters([
      'name',
      'roles'
    ])
  }
}
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
</style>
