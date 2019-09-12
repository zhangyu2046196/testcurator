package com.youyuan.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.DeleteBuilder;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author zhangyu
 * @version 1.0
 * @description zk开源客户端框架 curator 删除节点
 * @date 2018/9/12 14:34
 */
public class DelNode {
    //服务器列表
    private static String connectString="172.18.32.16:12181,172.18.32.21:12181,172.18.32.25:12181";
    //session超时时间
    private static int sessionTimeoutMs=10000;
    //connection超时时间
    private static int connectionTimeoutMs=10000;

    public static void main(String[] args) {
        RetryPolicy retryPolicy=new ExponentialBackoffRetry(1000,3);//重试策略 1秒钟1次 最多3次
        CuratorFramework client= CuratorFrameworkFactory.builder()
                .connectString(connectString)
                .connectionTimeoutMs(connectionTimeoutMs)
                .sessionTimeoutMs(sessionTimeoutMs)
                .retryPolicy(retryPolicy)
                .namespace("order")
                .build();
        client.start();//启动客户端

        try {
            client.delete().forPath("/order_c");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
