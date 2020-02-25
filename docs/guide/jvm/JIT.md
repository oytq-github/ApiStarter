#### JIT
1. 即时编译

   运行越久执行次数越多的热点代码直接编译为本机代码缓存起来,下次直接执行机器码
   
2. 方法逃逸分析
   
   StringBuffer的锁粗化,代码内的锁清除
   ```java
    private void lockClearUp() {
        StringBuilder sb = new StringBuilder();
        //上面代码逃逸分析以后会优化成下面代码
        //StringBuffer sb = new StringBuffer();
        sb.append("1");
    }
    ```
   
3. 栈上分配
   
   对象不再绝对分配在堆里,有可能直接分配在栈里,通过 **标量替换** 实现
   ```java
   class DemoClass {
       int a = 10;
       int b = 20;
   }
   public void demo() {
       DemoClass variable = new DemoClass();
       //上面对象会被下面代码替换
       //int a = 10;
       //int b = 20;
   }
   ```