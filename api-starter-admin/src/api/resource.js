import request from '@/utils/request'

export function list(data) {
  return request({
    url: '/resource/list',
    method: 'get',
    params: data
  })
}

export function allResourceList() {
  return request({
    url: '/resource/allResourceList',
    method: 'get'
  })
}
