package org.example;

import lombok.Data;

@Data
public class DBConfig {
    private String url;
    private String username;
    private String password;
    private String driver;
}
