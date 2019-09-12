package com.youyuan.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

/**
 * @author zhangyu
 * @version 1.0
 * @description zk开源客户端框架异步创建节点
 * @date 2018/9/12 15:59
 */
public class CreateNodeASync {
    //服务器列表
    private static String connectString="172.18.32.16:12181,172.18.32.21:12181,172.18.32.25:12181";
    //session超时时间
    private static int sessionTimeoutMs=10000;
    //connection超时时间
    private static int connectionTimeoutMs=10000;

    public static void main(String[] args) {
        RetryPolicy retryPolicy=new ExponentialBackoffRetry(1000,3);//重试策略 1秒1次 最多重试3次
        CuratorFramework client= CuratorFrameworkFactory.builder()
                .connectString(connectString)
                .connectionTimeoutMs(connectionTimeoutMs)
                .sessionTimeoutMs(sessionTimeoutMs)
                .retryPolicy(retryPolicy)
                .namespace("order")
                .build();
        client.start();//启动客户端

        //异步创建节点
        try {
            client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)
                    .inBackground(new BackgroundCallback() {
                        public void processResult(CuratorFramework curatorFramework, CuratorEvent curatorEvent) throws Exception {
                            System.out.println("事件类型内容:"+curatorFramework.create());
                            System.out.println("事件状态内容"+curatorEvent.getResultCode());
                        }
                    });
            Thread.sleep(Integer.MAX_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
