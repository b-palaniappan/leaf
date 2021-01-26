package io.c12.bala.web.leaf.entity;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.util.List;

@Document("user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @MongoId
    private String id = NanoIdUtils.randomNanoId();
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<String> roles;
    private boolean active;
    private boolean locked;
    private boolean resetPassword;
    private LocalDateTime createDate;
    private LocalDateTime updatedDate;
}
