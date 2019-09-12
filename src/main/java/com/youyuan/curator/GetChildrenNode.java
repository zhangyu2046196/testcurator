package com.youyuan.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.List;

/**
 * @description zk开源客户端curator查询子节点列表
 * @author zhangyu
 * @date 2018/9/12 14:06
 * @version 1.0
 */
public class GetChildrenNode {
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
           List<String> childrens= client.getChildren().forPath("/order_a");
           for (String children:childrens){
               System.out.println("子节点内容:"+children);
           }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
