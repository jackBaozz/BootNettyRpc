package io.github.forezp.netty.rpc.core.protocol.client;

import io.github.forezp.netty.rpc.core.common.delegate.NettyRpcDelegateImpl;

import io.github.forezp.netty.rpc.core.common.entity.AppEntity;
import io.github.forezp.netty.rpc.core.common.entity.ConnectionEntity;
import io.netty.channel.ChannelFuture;

/**
 * ${DESCRIPTION}
 *
 * @author fangzhipeng
 * create 2018-05-21
 **/
public abstract class AbstractClientExcutor extends NettyRpcDelegateImpl implements IClientExcutor {


    @Override
    public boolean started(AppEntity appEntity) throws Exception {
        return cacheContainer.containsConnection( appEntity.getName() );
    }

    @Override
    public ChannelFuture online(AppEntity appEntity, ChannelFuture channelFuture) throws Exception {
        // TCP调用是多个Interface共享一个通道，注册Interface后，如果对应的通道已经开启，不需要重复开启了
        ConnectionEntity connectionEntity = new ConnectionEntity();
        connectionEntity.setAppEntity( appEntity );
        connectionEntity.setChannelFuture( channelFuture );
        cacheContainer.putConnection( appEntity.getName(), connectionEntity );
        return channelFuture;
    }

    @Override
    public void offline(AppEntity appEntity) throws Exception {
        cacheContainer.removeConnection( appEntity );
    }
}
