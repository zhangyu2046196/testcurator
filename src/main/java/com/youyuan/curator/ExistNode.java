package com.youyuan.curator;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.ExistsBuilder;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @description zk开源客户端框架 curator 查询节点是否存在
 * @author zhangyu
 * @date 2018/9/12 14:13
 * @version 1.0
 */
public class ExistNode {
    //服务器列表
    private static String connectString="172.18.32.16:12181,172.18.32.21:12181,172.18.32.25:12181";
    //session超时时间
    private static int sessionTimeoutMs=10000;
    //connection超时时间
    private static int connectionTimeoutMs=10000;

    public static void main(String[] args) {
        RetryPolicy retryPolicy=new ExponentialBackoffRetry(1000,3);//重试策略 1秒钟重试一次 最多3次
        CuratorFramework client= CuratorFrameworkFactory.builder()
                .connectString(connectString)
                .connectionTimeoutMs(connectionTimeoutMs)
                .sessionTimeoutMs(sessionTimeoutMs)
                .retryPolicy(retryPolicy)
                .namespace("order")
                .build();
        client.start();//启动客户端

        try {
            //异步回调
            client.checkExists().inBackground(new BackgroundCallback() {
                public void processResult(CuratorFramework curatorFramework, CuratorEvent curatorEvent) throws Exception {
                    System.out.println(curatorFramework.getState());
                    System.out.println(curatorEvent.getContext());
                }
            }).forPath("/order_c");
            Thread.sleep(Integer.MAX_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
