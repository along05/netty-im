package com.along.netty.im.attribute;

import com.along.netty.im.session.Session;
import io.netty.util.AttributeKey;

/**
 * @author huanglong
 * @date 2019-12-16
 */
public interface Attributes {

    AttributeKey<Session> SESSION = AttributeKey.newInstance("session") ;

}
