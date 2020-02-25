import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/user/login',
    method: 'post',
    params: data
  })
}

export function getInfo(token) {
  return request({
    url: '/user/info',
    method: 'get',
    params: {
      token
    }
  })
}

export function logout() {
  return request({
    url: '/user/logout',
    method: 'post'
  })
}

export function list(data) {
  return request({
    url: '/user/list',
    method: 'get',
    params: data
  })
}

export function updateAdminUserStatus(data) {
  return request({
    url: '/user/updateAdminUserStatus',
    method: 'post',
    params: data
  })
}

export function deleteAdminUser(data) {
  return request({
    url: '/user/deleteAdminUser',
    method: 'get',
    params: data
  })
}

export function addAdminUser(data) {
  return request({
    url: '/user/addAdminUser',
    method: 'post',
    params: data
  })
}

export function updateAdminUser(data) {
  return request({
    url: '/user/updateAdminUser',
    method: 'post',
    params: data
  })
}
