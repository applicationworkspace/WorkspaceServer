package com.workspace.dto;

public class UserSummaryBuilder {
    private Long id;
    private String name;
    private String email;

    public UserSummaryBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public UserSummaryBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public UserSummaryBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserSummary createUserSummary() {
        return new UserSummary(id, name, email);
    }
}