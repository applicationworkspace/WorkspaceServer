package com.workspace.service;

import com.workspace.dto.UserSummaryBuilder;
import org.springframework.stereotype.Service;
import com.workspace.dto.UserSummary;
import com.workspace.security.UserPrincipal;

@Service
public class UserService {

    public UserSummary getCurrentUser(UserPrincipal userPrincipal) {
        return new UserSummaryBuilder()
                .setId(userPrincipal.getId())
                .setEmail(userPrincipal.getEmail())
                .setName(userPrincipal.getName())
                .createUserSummary();
    }
}
