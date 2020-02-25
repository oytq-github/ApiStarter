import request from '@/utils/request'

export function list(data) {
  return request({
    url: '/role/list',
    method: 'get',
    params: data
  })
}

export function deleteRole(data) {
  return request({
    url: '/role/deleteRole',
    method: 'delete',
    params: data
  })
}

export function saveOrUpdateRole(data) {
  return request({
    url: '/role/saveOrUpdateRole',
    method: 'post',
    params: data
  })
}

export function roleResources(data) {
  return request({
    url: '/role/roleResources',
    method: 'get',
    params: data
  })
}

export function saveRoleResources(data) {
  return request({
    url: '/role/saveRoleResources',
    method: 'post',
    params: data
  })
}

export function roleList(data) {
  return request({
    url: '/role/list',
    method: 'get',
    params: data
  })
}

export function userRoles(data) {
  return request({
    url: '/role/userRoles',
    method: 'get',
    params: data
  })
}

export function confirmUserRole(data) {
  return request({
    url: '/role/confirmUserRole',
    method: 'post',
    params: data
  })
}
