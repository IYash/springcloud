package com.clnn.security.db.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "t_user")
@Data
public class UserDO implements Serializable {

    @Id
    @GenericGenerator(name = "user_uuid", strategy = "uuid2")
    @GeneratedValue(generator = "user_uuid")
    private String id;

    /**
     * 账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

}
