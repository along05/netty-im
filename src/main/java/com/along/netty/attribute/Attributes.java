package com.along.netty.attribute;

import com.along.netty.session.Session;
import io.netty.util.AttributeKey;

/**
 * @author huanglong
 * @date 2019-12-23
 */
public interface Attributes {

    AttributeKey<Session> SESSION = AttributeKey.newInstance("session") ;

}
