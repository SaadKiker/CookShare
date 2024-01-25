package com.project.cookshare.DTOs;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
    private Integer id;
    private String username;
    private String password;
}
