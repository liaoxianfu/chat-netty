package com.liao.chat.im.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Scanner;

public class NettyEchoClient {
    Bootstrap b = new Bootstrap();
    private int port;
    private String serverIP;

    public NettyEchoClient(int port, String serverIP) {
        this.port = port;
        this.serverIP = serverIP;
    }

    public void runClient() throws InterruptedException {
        NioEventLoopGroup worker = new NioEventLoopGroup();
        b.group(worker);
        b.channel(NioSocketChannel.class);
        b.remoteAddress(serverIP, port);
        b.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
        b.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline().addLast(new EchoHandler());
            }
        });

        ChannelFuture f = b.connect();
        f.addListener((ChannelFutureListener) listener -> {
            if (listener.isSuccess()) System.out.println("连接成功");
            else {
                System.out.println("连接失败");
                throw new RuntimeException("连接失败");
            }
        });
        f.sync();
        Channel channel = f.channel();
        System.out.println("请输入发送内容:");
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String next = sc.next();
            ByteBuf buffer = channel.alloc().buffer();
            buffer.writeBytes(next.getBytes());
            channel.writeAndFlush(buffer);
            System.out.println("请输入发送内容:");
        }
        worker.shutdownGracefully();
    }

    public static void main(String[] args) throws InterruptedException {
        NettyEchoClient localhost = new NettyEchoClient(8000, "localhost");
        localhost.runClient();
    }
}

@ChannelHandler.Sharable
class EchoHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        int cnt = buf.readableBytes();
        byte[] bytes = new byte[cnt];
        buf.getBytes(0, bytes);
        System.out.println("接收到消息为" + new String(bytes));
        super.channelRead(ctx, msg);
    }
}