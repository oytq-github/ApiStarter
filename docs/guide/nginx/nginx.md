1. nginx一直监听80端口

2. 监听到请求链接,解析tcp报文,解析http报文

3. 根据http报文请求行 + 自身配置路由请求到后端服务器监听的端口

  负载均衡

  1. 轮询(支持权重)
  2. 最少连接
  3. ip-hash
  
4. [nginx官网](http://nginx.org/en/docs/)