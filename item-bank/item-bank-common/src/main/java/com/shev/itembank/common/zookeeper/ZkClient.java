package com.shev.itembank.common.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class ZkClient
{
    private static final Logger logger = LoggerFactory.getLogger(ZkClient.class);

    private CuratorFramework client;

    public TreeCache cache;

    @Value("${zookeeper.enable}")
    private String enable;

    @Value("${zookeeper.baseSleepTimeMs}")
    private String baseSleepTimeMs;

    @Value("${zookeeper.maxRetries}")
    private String maxRetries;

    @Value("${zookeeper.server}")
    private String server;

    @Value("${zookeeper.sessionTimeoutMs}")
    private String sessionTimeoutMs;

    @Value("${zookeeper.connectionTimeoutMs}")
    private String connectionTimeoutMs;

    @Value("${zookeeper.namespace}")
    private String namespace;

    public static Boolean ENABLE = true;

    @PostConstruct
    public void init()
    {
        try
        {
            if ("false".equals(enable))
            {
                logger.info("zookeeper disable!");
                ENABLE = false;
                return;
            }

            RetryPolicy retryPolicy = new ExponentialBackoffRetry(Integer.valueOf(baseSleepTimeMs), Integer.valueOf(maxRetries));
            CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder()
                    .connectString(server).retryPolicy(retryPolicy)
                    .sessionTimeoutMs(Integer.valueOf(sessionTimeoutMs))
                    .connectionTimeoutMs(Integer.valueOf(connectionTimeoutMs))
                    .namespace(namespace);
            client = builder.build();
            client.start();

//            initLocalCache("/edu");

            client.getConnectionStateListenable().addListener((client, state) ->
            {
                if (state == ConnectionState.LOST)
                {
                    logger.info("lost session with zookeeper");
                } else if (state == ConnectionState.CONNECTED)
                {
                    logger.info("connected with zookeeper");
                } else if (state == ConnectionState.RECONNECTED)
                {
                    logger.info("reconnected with zookeeper");
                }
            });
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private void initLocalCache(String watchRootPath) throws Exception
    {
        cache = new TreeCache(client, watchRootPath);
        TreeCacheListener listener = (client1, event) ->
        {
            logger.info("event:" + event.getType() +
                    " |path:" + (null != event.getData() ? event.getData().getPath() : null));
            if(event.getData()!=null && event.getData().getData()!=null){
                logger.info("发生变化的节点内容为：" + new String(event.getData().getData()));
            }
        };
        cache.getListenable().addListener(listener);
        cache.start();
    }

    public void stop()
    {
        client.close();
    }

    public CuratorFramework getClient()
    {
        return client;
    }

    /**
     * 节点类型:0-持久化节点 1-临时节点 2-持久顺序节点 3-临时顺序节点
     * @param mode
     * @param path
     * @param nodeData
     */
    public void createNode(CreateMode mode, String path , String nodeData) {
        try
        {
            client.create()
                    .creatingParentsIfNeeded()
                    .withMode(mode)
                    .forPath(path,nodeData.getBytes("UTF-8"));
        } catch (Exception e)
        {
            logger.error("注册出错", e);
        }
    }

    public void deleteNode(final String path)
    {
        try
        {
            client.delete().guaranteed().deletingChildrenIfNeeded().forPath(path);
        } catch (Exception ex)
        {
            logger.error("{}",ex);
        }
    }

    public void setNodeData(String path, byte[] datas)
    {
        try
        {
            client.setData().forPath(path, datas);
        }catch (Exception ex)
        {
            logger.error("{}",ex);
        }
    }

    public byte[] getNodeData(String path)
    {
        client.sync();
        Byte[] bytes = null;
        try
        {
            if(cache != null)
            {
                ChildData data = cache.getCurrentData(path);
                if(data != null)
                {
                    return data.getData();
                }
            }
            return client.getData().forPath(path);
        }catch (Exception ex)
        {
            logger.error("{}",ex);
        }
        return null;
    }

    public boolean isExistNode(final String path)
    {
        client.sync();
        try
        {
            return null != client.checkExists().forPath(path);
        } catch (Exception ex)
        {
            return false;
        }
    }

    public List<String> getChildren(String path)
    {
        List<String> childrenList = new ArrayList<>();
        try
        {
            childrenList = client.getChildren().forPath(path);
        } catch (Exception e)
        {
            logger.error("获取子节点出错", e);
        }
        return childrenList;
    }
}
