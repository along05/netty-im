package com.along.netty.protocol.command;

/**
 * @author huanglong
 * @date 2019-12-22
 */
public interface Command {

    byte LOGIN_REQUEST = 1;

    byte LOGIN_RESPONSE = 2;

    byte SEND_MESSAGE_REQUEST = 3;

    byte SEND_MESSAGE_RESPONSE = 4;

    byte LOGOUT_REQUEST = 5;

    byte LOGOUT_RESPONSE = 6;

    byte CREATE_GROUP_REQ = 7;

    byte CREATE_GROUP_RESP = 8;

    byte SEND_GROUP_MESSAGE_REQ = 9;

    byte SEND_GROUP_MESSAGE_RESP = 10;


}
