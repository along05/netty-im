package com.along.netty.server.Handler;

import com.along.netty.protocol.request.CreateGroupReqPacket;
import com.along.netty.protocol.response.CreateGroupRespPacket;
import com.along.netty.session.Session;
import com.along.netty.utils.IDUtils;
import com.along.netty.utils.SessionUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huanglong
 * @date 2019-12-25
 */
@ChannelHandler.Sharable
public class CreateGroupReqHandler extends SimpleChannelInboundHandler<CreateGroupReqPacket> {

    public static final CreateGroupReqHandler INSTANCE = new CreateGroupReqHandler();

    private CreateGroupReqHandler() {}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupReqPacket createGroupReqPacket){
        List<String> ids = createGroupReqPacket.getUserIdList();
        List<String> usernameList = new ArrayList<>();
        // 1. 创建一个 channel 分组
        ChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());

        for (String userId : ids) {
            Channel channel = SessionUtils.getChannel(userId);
            if (channel != null) {
                Session session = SessionUtils.getSession(channel);
                usernameList.add(session.getUsername());
                channelGroup.add(channel);
            }
        }

        String groupId = IDUtils.getGroupId();
        CreateGroupRespPacket createGroupRespPacket = new CreateGroupRespPacket();
        createGroupRespPacket.setUserNameList(usernameList);
        createGroupRespPacket.setGroupId(groupId);

        channelGroup.writeAndFlush(createGroupRespPacket);

        //保存群组信息
        System.out.println("【创建群成功】，群id为"+groupId);
        SessionUtils.bindChannelGroup(groupId, channelGroup);
    }
}
