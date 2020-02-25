#### mybatis缓存
1. 二级缓存
全局的二级缓存，通过CacheExecutor来实现，其委托TransactionalCacheManager来保存/获取缓存

2. 一级缓存
mybatis默认SESSION级别缓存,BaseExecutor中根据MappedStatement的Id、SQL、参数值以及rowBound(边界)来构造CacheKey，并使用BaseExccutor中的localCache来维护此缓存。
 