package com.saas.model.domain;

import com.saas.model.enumModels.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("Users")
public class User {

    @Id
    private String _id;
    private String username;
    private String password;
    private UserRole role;
}
