package com.example.article_project;

import java.sql.Connection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class DatabaseConnectionTest {

    @Autowired
    private javax.sql.DataSource dataSource;

    @Test
    void testConnection() throws Exception {
        try (Connection conn = dataSource.getConnection()) {
            System.out.println("DB 연결 성공! URL: " + conn.getMetaData().getURL());
        }
    }
}
