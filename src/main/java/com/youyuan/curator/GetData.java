package com.youyuan.curator;

import com.sun.org.apache.xpath.internal.operations.String;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @description  zk开源客户端框架 curator查询节点内容
 * @author zhangyu
 * @date 2018/9/12 11:29
 * @version 1.0
 */
public class GetData {

    //服务器列表
    private static java.lang.String connectString="172.18.32.16:12181,172.18.32.21:12181,172.18.32.25:12181";
    //session超时时间
    private static int sessionTimeoutMs=10000;
    //connection超时时间
    private static int connectionTimeoutMs=10000;

    public static void main(String[] args) {
        RetryPolicy retryPolicy=new ExponentialBackoffRetry(1000,3);//重试策略 1分钟一次 最多重试3次
        CuratorFramework client= CuratorFrameworkFactory.builder()
                .connectString(connectString)
                .connectionTimeoutMs(connectionTimeoutMs)
                .sessionTimeoutMs(sessionTimeoutMs)
                .retryPolicy(retryPolicy)
                .namespace("order")
                .build();
        client.start();//启动客户端
        try {
            byte[] result=client.getData().forPath("/order_a");
            System.out.println("节点内容信息:"+new java.lang.String(result));
            Thread.sleep(Integer.MAX_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
