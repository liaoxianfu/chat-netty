package com.liao.chat.handler;

import com.liao.chat.eneity.ChatPlatform;
import com.liao.chat.eneity.ChatUser;
import com.liao.chat.session.LocalChatSession;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.net.SocketAddress;


@Slf4j
public class LoginHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
    }

    /**
     * 实现简单的登录 只要客户端上线 就默认将其socket作为ID
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SocketAddress socketAddress = ctx.channel().remoteAddress();
        log.debug("type{} info{}", socketAddress.getClass(), socketAddress);
        Channel channel = ctx.channel();
        ByteBuf buffer = channel.alloc().buffer();
        buffer.writeBytes("登录成功".getBytes());
        channel.writeAndFlush(buffer);
        ChatUser chatUser = new ChatUser();
        chatUser.setUserID(socketAddress.toString());
        chatUser.setPlatform(ChatPlatform.PC);
        LocalChatSession.INSTANCE().addSession(chatUser, channel);
        channel.pipeline().remove(this);
        super.channelActive(ctx);
    }
}
