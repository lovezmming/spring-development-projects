package com.shev.dubbo.provider.api.common.dubbo;

import com.shev.dubbo.provider.api.common.zk.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class DubboInit implements CommandLineRunner
{
    private static final Logger logger = LoggerFactory.getLogger(ZkClient.class);

    @Resource
    private ZkClient zkClient;

    @Override
    public void run(String... args)
    {
        /*if (zkClient.ENABLE)
        {
            // 如果使用zk，自行加载dubbo配置，本地测试
            zkDubboData();
        }*/
    }

    private void zkDubboData()
    {
        /*if (zkClient.isExistNode("/dubbo"))
        {
            // znode节点：/dubbo
            List<String> parentNodes = zkClient.getChildren("/dubbo");
            if (!TextUtil.isEmpty(parentNodes))
            {
                for (String node : parentNodes)
                {
                    logger.info(node);
                }
            } else
            {
            }
        } else
        {
            zkClient.createNode(null, "/dubbo");
        }*/
    }

}
