package com.along.netty.session;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author huanglong
 * @date 2019-12-23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Session {

    private String userId ;
    private String username ;

}


