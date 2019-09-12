package com.youyuan.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.RetrySleeper;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author zhangyu
 * @description  zk客户端框架curator创建会话
 * @date 2018/9/12 10:14
 */
public class CreateSession {

    //服务器列表
    private static String connectString="172.18.32.16:12181,172.18.32.21:12181,172.18.32.25:12181";
    //session超时时间
    private static int sessionTimeoutMs=10000;
    //connection超时时间
    private static int connectionTimeoutMs=10000;

    public static void main(String[] args) {
        RetryPolicy retryPolicy=new ExponentialBackoffRetry(1000,3);//设置重试策略,1秒钟重试一次,最多重试3次
        //以下采用Fluent(流式)静态工厂方式创建会话
        CuratorFramework client= CuratorFrameworkFactory.builder()
                .connectString(connectString)
                .sessionTimeoutMs(sessionTimeoutMs)
                .connectionTimeoutMs(connectionTimeoutMs)
                .retryPolicy(retryPolicy)
                .namespace("order")//创建隔离的命名空间
                .build();
        client.start();//启动客户端
        System.out.println("curator通过Fluent流式风格创建隔离命名空间的session:"+client);
        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
