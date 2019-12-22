package com.along.netty.im.session;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huanglong
 * @date 2019-12-19
 */
@Data
@NoArgsConstructor
public class Session {
    //用户唯一id
    private String userId;
    private String userName;

    public Session(String userId , String userName){
        this.userId = userId ;
        this.userName = userName ;
    }

    @Override
    public String toString(){
        return userId + ":" + userName ;
    }




}
