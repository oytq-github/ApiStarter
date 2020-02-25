<template>
  <div>
    <div>
      <el-col :span="20">
        <el-form
          inline
          :model="search"
          size="small"
        >
          <el-form-item style="width:120px">
            <el-select
              v-model="search.resourceType"
              placeholder="资源类型"
              size="small"
              clearable
            >
              <el-option
                label="菜单资源"
                value="RESOURCE_MENU"
              />
              <el-option
                label="按钮资源"
                value="RESOURCE_BUTTON"
              />
              <el-option
                label="接口资源"
                value="RESOURCE_INTERFACE"
              />
            </el-select>
          </el-form-item>
          <el-form-item style="width:120px">
            <el-input
              v-model="search.resourceName"
              placeholder="资源名称"
            />
          </el-form-item>
          <el-form-item style="width:120px">
            <el-input
              v-model="search.resourceDesc"
              placeholder="资源描述"
            />
          </el-form-item>
          <el-form-item>
            <el-date-picker
              v-model="search.searchDate"
              size="small"
              type="datetimerange"
              range-separator="至"
              start-placeholder="创建时间开始"
              end-placeholder="创建时间结束"
              value-format="yyyy-MM-dd HH:mm:ss"
              :picker-options="$pickerOptions"
            />
          </el-form-item>
        </el-form>
      </el-col>
      <el-col
        :span="2"
        :offset="2"
      >
        <el-button
          type="primary"
          size="small"
          plain
          icon="el-icon-search"
          @click="refreshTable"
        >
          查找
        </el-button>
      </el-col>
      <el-col>
        <el-table
          v-loading="switchs.tableLoading"
          :data="show.resources"
          :border="true"
          size="small"
          element-loading-text="拼命加载中"
          element-loading-spinner="el-icon-loading"
        >
          <el-table-column
            prop="id"
            label="ID"
            width="80"
          />
          <el-table-column label="资源类型" width="100">
            <template slot-scope="scope">
              <span v-if="scope.row.resourceType === 'RESOURCE_MENU'">菜单资源</span>
              <span v-if="scope.row.resourceType === 'RESOURCE_BUTTON'">按钮资源</span>
              <span v-if="scope.row.resourceType === 'RESOURCE_INTERFACE'">接口资源</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="resourceName"
            label="资源名称"
          />
          <el-table-column
            prop="resourceDesc"
            label="资源描述"
          />
          <el-table-column
            prop="parentId"
            label="父节点ID"
            width="80"
          />
          <el-table-column label="创建时间">
            <template slot-scope="scope">
              <span>{{ $formatDate(scope.row.createTime) }}</span>
            </template>
          </el-table-column>
          <el-table-column label="更新时间">
            <template slot-scope="scope">
              <span>{{ $formatDate(scope.row.updateTime) }}</span>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
      <el-col><br></el-col>
      <el-col>
        <el-pagination
          :total="pageInfo.total"
          :page-size="pageInfo.size"
          :page-sizes="[5, 10, 30, 40, 50, 100]"
          :background="true"
          layout="prev, pager, next,sizes,slot,total"
          @current-change="onChangePage"
          @size-change="onChangePageSize"
        />
      </el-col>
    </div>
  </div>
</template>

<script>
import {
  list
} from '../../../api/resource'
export default {
  name: 'Resources',
  data() {
    return {
      pageInfo: {
        total: 0,
        size: 10,
        current: 1
      },
      show: {
        resources: []
      },
      search: {
        resourceType: '',
        resourceName: '',
        resourceDesc: '',
        searchDate: []
      },
      switchs: {
        tableLoading: false
      }
    }
  },
  mounted() {
    this.list()
  },
  methods: {
    list() {
      const params = {
        current: this.pageInfo.current,
        pageSize: this.pageInfo.size,
        resourceType: this.search.resourceType,
        resourceName: this.search.resourceName,
        resourceDesc: this.search.resourceDesc
      }
      if (this.search.searchDate && this.search.searchDate.length !== 0) {
        params.startDateStr = this.search.searchDate[0]
        params.endDateStr = this.search.searchDate[1]
      }
      list(params).then(res => {
        this.show.resources = res.data.list
        this.pageInfo.total = res.data.total
        this.loadingOff()
      })
    },
    onChangePage(index) {
      this.pageInfo.current = index
      this.refreshTable()
    },
    onChangePageSize(size) {
      this.pageInfo.size = size
      this.refreshTable()
    },
    refreshTable() {
      this.loadingOn()
      this.list()
    },
    loadingOn() {
      this.switchs.tableLoading = true
    },
    loadingOff() {
      this.switchs.tableLoading = false
    }
  }
}
</script>

<style lang="less" scoped>
</style>
