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
            <el-input
              v-model="search.roleName"
              placeholder="角色名称"
            />
          </el-form-item>
          <el-form-item style="width:120px">
            <el-input
              v-model="search.roleDesc"
              placeholder="角色描述"
            />
          </el-form-item>
        </el-form>
      </el-col>
      <el-col :span="4">
        <el-button
          v-if="$hasButtons(buttons,'ROLE_ADD_BUTTON')"
          type="success"
          size="small"
          plain
          icon="el-icon-circle-plus-outline"
          @click="openAddRoleModel"
        >
          添加角色
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
          :data="show.roles"
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
          <el-table-column
            label="角色名称"
            prop="roleName"
          />
          <el-table-column
            prop="roleDesc"
            label="角色描述"
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
          <el-table-column
            label="操作"
            width="370"
          >
            <template slot-scope="scope">
              <el-button
                v-if="$hasButtons(buttons,'ROLE_EDIT_BUTTON')"
                size="mini"
                type="primary"
                icon="el-icon-circle-check"
                plain
                @click="updateRole(scope.row)"
              >编辑角色</el-button>
              <el-button
                v-if="$hasButtons(buttons,'ROLE_RESOURCE_SETTING_BUTTON')"
                size="mini"
                type="warning"
                icon="el-icon-user"
                plain
                @click="openResourceSettingModel(scope.row)"
              >资源配置</el-button>
              <el-button
                v-if="$hasButtons(buttons,'ROLE_DELETE_BUTTON')"
                size="mini"
                type="danger"
                plain
                icon="el-icon-delete"
                @click="deleteRole(scope.row.id)"
              >删除角色</el-button>
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
    <div>
      <el-dialog
        :title="addOrUpdateTitle"
        :visible.sync="switchs.roleAddModal"
        width="30%"
        :show-close="false"
        :close-on-click-modal="false"
      >
        <el-form
          :model="roleAddOrUpdateData"
          label-width="80px"
          label-position="left"
          style="margin:0px 20px"
        >
          <el-form-item label="角色名称">
            <el-input
              v-model="roleAddOrUpdateData.roleName"
              placeholder="角色名称"
            />
          </el-form-item>
          <el-form-item label="角色描述">
            <el-input
              v-model="roleAddOrUpdateData.roleDesc"
              placeholder="角色描述"
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
            @click="addOrUpdateRole"
          >{{ addOrUpdateButton }}</el-button>
          <el-button
            size="small"
            type="primary"
            icon="el-icon-d-arrow-left"
            @click="closeRoleAddModal"
          >返回</el-button>
        </span>
      </el-dialog>
      <el-dialog
        title="角色资源配置"
        :visible.sync="switchs.resourceSettingModel"
        :show-close="false"
        width="70%"
        :close-on-click-modal="false"
      >
        <el-row>
          <el-col
            :span="6"
            :offset="2"
          >
            <el-card class="box-card">
              <div
                slot="header"
                class="clearfix"
              >
                <span>菜单资源</span>
              </div>
              <el-tree
                ref="menuListTree"
                v-loading="switchs.treeloading"
                :data="show.menuList"
                :filter-node-method="filterNode"
                show-checkbox
                check-strictly
                :default-expand-all="true"
                :default-checked-keys="show.defaultCheckedKeys"
                node-key="id"
                :props="defaultProps"
                element-loading-text="拼命加载中"
                element-loading-spinner="el-icon-loading"
              />
            </el-card>
          </el-col>
          <el-col
            :span="6"
            :offset="1"
          >
            <el-card class="box-card">
              <div
                slot="header"
                class="clearfix"
              >
                <span>按钮资源</span>
              </div>
              <el-tree
                ref="buttonListTree"
                v-loading="switchs.treeloading"
                :data="show.buttonList"
                :filter-node-method="filterNode"
                show-checkbox
                check-strictly
                :default-expand-all="true"
                :default-checked-keys="show.defaultCheckedKeys"
                node-key="id"
                :props="defaultProps"
                element-loading-text="拼命加载中"
                element-loading-spinner="el-icon-loading"
              />
            </el-card>
          </el-col>
          <el-col
            :span="6"
            :offset="1"
          >
            <el-card class="box-card">
              <div
                slot="header"
                class="clearfix"
              >
                <span>接口资源</span>
              </div>
              <el-tree
                ref="interfaceListTree"
                v-loading="switchs.treeloading"
                :data="show.interfaceList"
                :filter-node-method="filterNode"
                show-checkbox
                check-strictly
                :default-expand-all="true"
                :default-checked-keys="show.defaultCheckedKeys"
                node-key="id"
                :props="defaultProps"
                element-loading-text="拼命加载中"
                element-loading-spinner="el-icon-loading"
              />
            </el-card>
          </el-col>
        </el-row>
        <span
          slot="footer"
          class="dialog-footer"
        >
          <el-button
            size="small"
            type="success"
            icon="el-icon-circle-check"
            @click="saveRoleResources"
          >配置</el-button>
          <el-button
            size="small"
            type="primary"
            icon="el-icon-d-arrow-left"
            @click="closeResourceSettingModel"
          >返回</el-button>
        </span>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import {
  list,
  deleteRole,
  saveOrUpdateRole,
  saveRoleResources,
  roleResources
} from '@/api/roles'

import {
  allResourceList
} from '@/api/resource'
export default {
  name: 'Roles',
  data() {
    return {
      pageInfo: {
        total: 0,
        size: 10,
        current: 1
      },
      buttons: this.$store.getters.buttons,
      show: {
        roles: [],
        interfaceList: [],
        buttonList: [],
        menuList: [],
        defaultCheckedKeys: []
      },
      search: {
        roleName: '',
        roleDesc: ''
      },
      roleAddOrUpdateData: {
        roleName: '',
        roleDesc: '',
        id: ''
      },
      addOrUpdateTitle: '',
      addOrUpdateButton: '',
      switchs: {
        tableLoading: false,
        roleAddModal: false,
        resourceSettingModel: false,
        treeloading: true
      },
      defaultProps: {
        children: 'childs',
        label: 'resourceDesc'
      },
      saveRoleResourcesRoleId: ''
    }
  },
  mounted() {
    this.refreshTable()
  },
  methods: {
    saveRoleResources() {
      const menusId = this.$refs.menuListTree.getCheckedNodes(false, true).map(item => { return item.id }).join(',')
      const buttonsId = this.$refs.buttonListTree.getCheckedNodes(false, true).map(item => { return item.id }).join(',')
      const interfacesId = this.$refs.interfaceListTree.getCheckedNodes(false, true).map(item => { return item.id }).join(',')
      const array = []
      if (menusId && menusId.length > 0) {
        array.push(menusId)
      }
      if (buttonsId && buttonsId.length > 0) {
        array.push(buttonsId)
      }
      if (interfacesId && interfacesId.length > 0) {
        array.push(interfacesId)
      }
      const params = {
        roleId: this.saveRoleResourcesRoleId,
        resources: array.join(',')
      }
      saveRoleResources(params).then(res => {
        this.$message.success('更新成功')
        this.dataInit()
      })
    },
    filterNode(value, data) {
      if (!value) return true
      return data.label.indexOf(value) !== -1
    },
    openResourceSettingModel(row) {
      this.switchs.resourceSettingModel = true
      this.saveRoleResourcesRoleId = row.id
      allResourceList().then(res => {
        this.show.interfaceList = res.data.RESOURCE_INTERFACE
        this.show.buttonList = res.data.RESOURCE_BUTTON
        this.show.menuList = res.data.RESOURCE_MENU
        roleResources({ roleId: row.id }).then(res => {
          if (res.data && res.data.length > 0) {
            this.show.defaultCheckedKeys	= res.data.map(item => { return item.resourceId })
          }
          this.switchs.treeloading = false
        })
      })
    },
    closeResourceSettingModel() {
      this.dataInit()
    },
    openAddRoleModel() {
      this.switchs.roleAddModal = true
      this.addOrUpdateTitle = '添加角色'
      this.addOrUpdateButton = '添加'
    },
    updateRole(row) {
      this.roleAddOrUpdateData = row
      this.switchs.roleAddModal = true
      this.addOrUpdateTitle = '编辑角色'
      this.addOrUpdateButton = '更新'
    },
    closeRoleAddModal() {
      this.dataInit()
    },
    dataInit() {
      this.roleAddOrUpdateData = {
        roleName: '',
        roleDesc: '',
        id: ''
      }
      this.saveRoleResourcesRoleId = ''
      this.show.interfaceList = []
      this.show.buttonList = []
      this.show.menuList = []
      this.show.defaultCheckedKeys = []
      this.switchs.resourceSettingModel = false
      this.switchs.roleAddModal = false
      this.switchs.treeloading = true
      this.refreshTable()
    },
    addOrUpdateRole() {
      const params = this.roleAddOrUpdateData
      saveOrUpdateRole(params).then(res => {
        this.$message.success('添加成功')
        this.dataInit()
      })
    },
    deleteRole(id) {
      this.$confirm('此操作将永久删除角色, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        const params = { id }
        deleteRole(params).then(res => {
          this.$message.success('删除成功')
          this.dataInit()
        })
      }).catch(() => {
        this.$message.info('已取消删除')
      })
    },
    list() {
      const params = {
        current: this.pageInfo.current,
        pageSize: this.pageInfo.size,
        roleName: this.search.roleName,
        roleDesc: this.search.roleDesc
      }
      list(params).then(res => {
        this.show.roles = res.data.list
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

<style>
</style>
