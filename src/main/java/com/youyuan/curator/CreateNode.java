package com.youyuan.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

/**
 * @author zhangyu
 * @description zk开源客户端框架 curator
 * @date 2018/9/12 10:33
 */
public class CreateNode {
    //服务器列表
    private static String connectString="172.18.32.16:12181,172.18.32.21:12181,172.18.32.25:12181";
    //session超时时间
    private static int sessionTimeoutMs=10000;
    //connection超时时间
    private static int connectionTimeoutMs=10000;

    public static void main(String[] args) {
        RetryPolicy retryPolicy=new ExponentialBackoffRetry(1000,3);//重试策略 1秒钟测试一次  最多3次
        //创建会话
        CuratorFramework client= CuratorFrameworkFactory.builder()
                .retryPolicy(retryPolicy)
                .connectString(connectString)
                .sessionTimeoutMs(sessionTimeoutMs)
                .connectionTimeoutMs(connectionTimeoutMs)
                .namespace("order")
                .build();

        //启动客户端
        client.start();
        try {
            client.create()
                    .creatingParentsIfNeeded()
                    .withMode(CreateMode.PERSISTENT_SEQUENTIAL)//创建节点模式
                    .forPath("/order_b","zk_curator".getBytes());
            Thread.sleep(Integer.MAX_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
