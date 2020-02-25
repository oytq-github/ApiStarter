<template>
  <div>
    <div>
      <el-row>
        <el-col :span="20">
          <el-form
            inline
            :model="search"
            size="small"
          >
            <el-form-item style="width:120px">
              <el-input
                v-model="search.username"
                placeholder="账号"
              />
            </el-form-item>
            <el-form-item style="width:120px">
              <el-input
                v-model="search.realName"
                placeholder="真实姓名"
              />
            </el-form-item>
            <el-form-item style="width:120px">
              <el-input
                v-model="search.nickName"
                placeholder="昵称"
              />
            </el-form-item>
            <el-form-item style="width:120px">
              <el-input
                v-model="search.userPhone"
                placeholder="手机号"
              />
            </el-form-item>
            <el-form-item style="width:120px">
              <el-input
                v-model="search.userEmail"
                placeholder="邮箱"
              />
            </el-form-item>
            <el-form-item style="width:120px">
              <el-select
                v-model="search.userStatus"
                placeholder="用户状态"
                size="small"
                clearable
              >
                <el-option
                  label="正常"
                  value="1"
                />
                <el-option
                  label="停用"
                  value="-1"
                />
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-date-picker
                v-model="search.searchDate"
                size="small"
                type="datetimerange"
                range-separator="至"
                start-placeholder="添加时间开始"
                end-placeholder="添加时间结束"
                value-format="yyyy-MM-dd HH:mm:ss"
                :picker-options="$pickerOptions"
              />
            </el-form-item>
          </el-form>
        </el-col>
        <el-col :span="4">
          <el-button
            v-if="$hasButtons(buttons,'USER_ADD_BUTTON')"
            type="success"
            size="small"
            plain
            icon="el-icon-plus"
            @click="switchs.userAddModal = true"
          >
            添加用户
          </el-button>
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
            element-loading-text="拼命加载中"
            element-loading-spinner="el-icon-loading"
            :data="users"
            size="small"
            stripe
            border
          >
            <el-table-column
              prop="id"
              label="ID"
              width="80"
            />
            <el-table-column
              prop="avatarUrl"
              label="头像"
            />
            <el-table-column
              prop="username"
              label="账号"
              width="180"
              show-overflow-tooltip
            />
            <el-table-column
              prop="realName"
              label="真实姓名"
              show-overflow-tooltip
            />
            <el-table-column
              prop="nickName"
              label="昵称"
              show-overflow-tooltip
            />
            <el-table-column
              prop="userPhone"
              label="手机号"
              show-overflow-tooltip
            />
            <el-table-column
              prop="userEmail"
              label="邮箱"
              show-overflow-tooltip
            />
            <el-table-column
              label="创建时间"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                {{ $formatDate(scope.row.createTime,'yyyy-MM-dd') }}
              </template>
            </el-table-column>
            <el-table-column
              label="更新时间"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                {{ $formatDate(scope.row.updateTime,'yyyy-MM-dd') }}
              </template>
            </el-table-column>
            <el-table-column
              label="用户状态"
              width="80"
              align="center"
            >
              <template slot-scope="scope">
                <el-tag
                  v-if="scope.row.userStatus === 1"
                  type="success"
                  size="medium"
                  @click="updateAdminUserStatus(scope.row.id)"
                >正常</el-tag>
                <el-tag
                  v-if="scope.row.userStatus === -1"
                  type="warning"
                  size="medium"
                  @click="updateAdminUserStatus(scope.row.id)"
                >停用</el-tag>
              </template>
            </el-table-column>
            <el-table-column
              label="操作"
              width="270"
            >
              <template slot-scope="scope">
                <el-button
                  v-if="$hasButtons(buttons,'USER_EDIT_BUTTON')"
                  size="mini"
                  type="primary"
                  icon="el-icon-circle-check"
                  plain
                  @click="openUserUpdateModal(scope.row)"
                >编辑</el-button>
                <el-button
                  v-if="$hasButtons(buttons,'USER_ROLE_SETTING_BUTTON')"
                  size="mini"
                  type="warning"
                  icon="el-icon-user"
                  plain
                  @click="openRoleModal(scope.row.id)"
                >角色</el-button>
                <el-button
                  v-if="$hasButtons(buttons,'USER_DELETE_BUTTON')"
                  size="mini"
                  type="danger"
                  plain
                  icon="el-icon-delete"
                  @click="deleteUser(scope.row.id)"
                >删除</el-button>
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
      </el-row>
    </div>
    <div>
      <el-dialog
        title="添加用户"
        :visible.sync="switchs.userAddModal"
        width="30%"
        :show-close="false"
        :close-on-click-modal="false"
      >
        <el-form
          :model="userAddData"
          label-width="80px"
          label-position="left"
          style="margin:0px 20px"
        >
          <el-form-item label="账号">
            <el-input
              v-model="userAddData.username"
              placeholder="账号"
            />
          </el-form-item>
          <el-form-item label="密码">
            <el-input
              v-model="userAddData.password"
              placeholder="密码"
            />
          </el-form-item>
          <el-form-item label="真实姓名">
            <el-input
              v-model="userAddData.realName"
              placeholder="真实姓名"
            />
          </el-form-item>
          <el-form-item label="昵称">
            <el-input
              v-model="userAddData.nickName"
              placeholder="昵称"
            />
          </el-form-item>
          <el-form-item label="手机号">
            <el-input
              v-model="userAddData.userPhone"
              placeholder="手机号"
            />
          </el-form-item>
          <el-form-item label="邮箱">
            <el-input
              v-model="userAddData.userEmail"
              placeholder="邮箱"
            />
          </el-form-item>
        </el-form>
        <span
          slot="footer"
          class="dialog-footer"
        >
          <el-button
            size="small"
            type="success"
            icon="el-icon-circle-check"
            @click="addUser"
          >添加</el-button>
          <el-button
            size="small"
            type="primary"
            icon="el-icon-d-arrow-left"
            @click="closeUserAddModal"
          >返回</el-button>
        </span>
      </el-dialog>
      <el-dialog
        title="编辑用户"
        :visible.sync="switchs.userUpdateModal"
        width="30%"
        :show-close="false"
        :close-on-click-modal="false"
      >
        <el-form
          :model="userUpdateData"
          label-width="80px"
          label-position="left"
          style="margin:0px 20px"
        >
          <el-form-item label="账号">
            <el-input
              v-model="userUpdateData.username"
              placeholder="账号"
            />
          </el-form-item>
          <el-form-item label="真实姓名">
            <el-input
              v-model="userUpdateData.realName"
              placeholder="真实姓名"
            />
          </el-form-item>
          <el-form-item label="昵称">
            <el-input
              v-model="userUpdateData.nickName"
              placeholder="昵称"
            />
          </el-form-item>
          <el-form-item label="手机号">
            <el-input
              v-model="userUpdateData.userPhone"
              placeholder="手机号"
            />
          </el-form-item>
          <el-form-item label="邮箱">
            <el-input
              v-model="userUpdateData.userEmail"
              placeholder="邮箱"
            />
          </el-form-item>
        </el-form>
        <span
          slot="footer"
          class="dialog-footer"
        >
          <el-button
            size="small"
            type="success"
            icon="el-icon-refresh"
            @click="updateUser"
          >更新</el-button>
          <el-button
            size="small"
            type="primary"
            icon="el-icon-d-arrow-left"
            @click="closeUserUpdateModal"
          >返回</el-button>
        </span>
      </el-dialog>
      <el-dialog
        title="角色配置"
        :visible.sync="switchs.roleModal"
        width="600px"
        :show-close="false"
        :close-on-click-modal="false"
      >
        <el-transfer
          v-model="rolesSelected"
          v-loading="switchs.transferLoading"
          style="margin: 0 auto;"
          filterable
          element-loading-text="拼命加载中"
          element-loading-spinner="el-icon-loading"
          filter-placeholder="输入角色名称搜索"
          :titles="['可配置角色','已拥有角色']"
          :right-default-checked="roled"
          :props="{
            key: 'id',
            label: 'roleDesc'
          }"
          :data="rolesList"
        />
        <span
          slot="footer"
          class="dialog-footer"
        >
          <el-button
            size="small"
            type="success"
            icon="el-icon-finished"
            @click="confirmUserRole"
          >确认配置</el-button>
          <el-button
            size="small"
            type="primary"
            icon="el-icon-d-arrow-left"
            @click="closeRoleModal"
          >返回</el-button>
        </span>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import {
  list,
  deleteAdminUser,
  updateAdminUserStatus,
  addAdminUser,
  updateAdminUser
} from '@/api/user'

import {
  roleList,
  userRoles,
  confirmUserRole
} from '@/api/roles'

export default {
  name: 'Users',
  data() {
    return {
      pageInfo: {
        total: 0,
        size: 10,
        current: 1
      },
      buttons: this.$store.getters.buttons,
      search: {
        username: '',
        realName: '',
        nickName: '',
        userPhone: '',
        userEmail: '',
        userStatus: '',
        searchDate: []
      },
      switchs: {
        tableLoading: false,
        userAddModal: false,
        roleModal: false,
        transferLoading: true
      },
      userAddData: {
        username: '',
        realName: '',
        nickName: '',
        userPhone: '',
        userEmail: ''
      },
      userUpdateData: {
        id: '',
        username: '',
        realName: '',
        nickName: '',
        userPhone: '',
        userEmail: ''
      },
      users: [],
      currentUserId: '',
      rolesList: [],
      rolesSelected: [],
      roled: [],
      loading: ''
    }
  },
  mounted() {
    this.refreshTable()
  },
  methods: {
    dataInit() {
      this.switchs.userAddModal = false
      this.userAddData = {
        username: '',
        password: '',
        realName: '',
        nickName: '',
        userPhone: '',
        userEmail: ''
      }
      this.switchs.userUpdateModal = false
      this.userUpdateData = {
        username: '',
        password: '',
        realName: '',
        nickName: '',
        userPhone: '',
        userEmail: ''
      }
      this.rolesList = []
      this.rolesSelected = []
      this.roled = []
      this.switchs.transferLoading = true
      this.switchs.roleModal = false
      this.refreshTable()
    },
    confirmUserRole() {
      const ids = this.rolesSelected.join(',')
      const params = {
        ids: ids,
        userId: this.currentUserId
      }
      confirmUserRole(params).then(res => {
        this.$message.success('配置成功')
        this.dataInit()
      })
    },
    roleList() {
      roleList({
        current: 1,
        pageSize: 1000
      }).then(res => {
        this.rolesList = res.data.list
        userRoles({ userId: this.currentUserId }).then(res => {
          if (res.data) {
            this.rolesSelected = res.data.map(item => { return item.roleId })
          }
          this.switchs.transferLoading = false
        })
      })
    },
    closeRoleModal() {
      this.dataInit()
    },
    openRoleModal(id) {
      this.switchs.roleModal = true
      this.currentUserId = id
      this.roleList()
    },
    closeUserAddModal() {
      this.dataInit()
    },
    closeUserUpdateModal() {
      this.dataInit()
    },
    openUserUpdateModal(row) {
      this.switchs.userUpdateModal = true
      this.userUpdateData = row
    },
    addUser() {
      addAdminUser(this.userAddData).then(res => {
        this.switchs.userAddModal = false
        this.$message.success('添加成功')
        this.refreshTable()
      })
    },
    updateUser() {
      updateAdminUser(this.userUpdateData).then(res => {
        this.switchs.userUpdateModal = false
        this.$message.success('更新成功')
        this.refreshTable()
      })
    },
    updateAdminUserStatus(id) {
      const params = {
        id
      }
      updateAdminUserStatus(params).then(res => {
        this.$message.success('更改成功')
        this.refreshTable()
      })
    },
    deleteUser(id) {
      this.$confirm('此操作将永久删除用户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        const params = { id }
        deleteAdminUser(params).then(res => {
          this.$message.success('删除成功')
          this.refreshTable()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    list() {
      const params = {
        current: this.pageInfo.current,
        pageSize: this.pageInfo.size,
        username: this.search.username,
        realName: this.search.realName,
        nickName: this.search.nickName,
        userPhone: this.search.userPhone,
        userEmail: this.search.userEmail,
        userStatus: this.search.userStatus
      }
      if (this.search.searchDate && this.search.searchDate.length !== 0) {
        params.startDateStr = this.search.searchDate[0]
        params.endDateStr = this.search.searchDate[1]
      }
      list(params).then(res => {
        this.loadingOff()
        this.users = res.data.list
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

<style scoped>
</style>
