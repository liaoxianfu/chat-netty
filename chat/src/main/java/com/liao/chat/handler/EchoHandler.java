package com.liao.chat.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class EchoHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;

        // readableBytes已经写完了
        int count = buf.readableBytes();
        byte[] bs = new byte[count];
        buf.readBytes(bs);
        String data = new String(bs);
        ByteBuf buffer = ctx.channel().alloc().buffer();
        buffer.writeBytes(bs);
        log.info("获取到的消息为{}", data);
        ctx.writeAndFlush(buffer).addListener(future -> {
            log.debug("发送完成数据");
        });
    }
}
