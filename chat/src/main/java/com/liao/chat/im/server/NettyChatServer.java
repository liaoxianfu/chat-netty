package com.liao.chat.im.server;


import com.liao.chat.handler.EchoHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * 使用Netty实现的聊天服务
 */
@Slf4j
public class NettyChatServer implements ChatServer {

    private int port = 8000;

    ServerBootstrap bootstrap;
    NioEventLoopGroup bossGroup;
    NioEventLoopGroup workGroup;


    public void setPort(int port) {
        // 进行判断
        if (port < 0 || port > 65535) throw new RuntimeException("端口的范围必须在1-65535内");
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    @Override
    public void start() throws Exception {
        bootstrap = new ServerBootstrap();
        bossGroup = new NioEventLoopGroup(1);
        workGroup = new NioEventLoopGroup();
        bootstrap.group(bossGroup, workGroup);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.localAddress(port);
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new EchoHandler());
            }
        });
        ChannelFuture future = bootstrap.bind().sync();
        log.info("bind....");
        future.channel().closeFuture().sync();
        log.info("close...");
        stop();
    }

    @Override
    public void stop() throws Exception {
        if (workGroup != null) {
            workGroup.shutdownGracefully();
        }
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }
    }
}
