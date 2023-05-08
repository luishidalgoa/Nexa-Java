package com.luishidalgoa.Nexa.Model.Connections;

import java.sql.Connection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class ConnectionMySQLTest {

    @Test
    void getConnect() {
        Connection c=ConnectionMySQL.getConnect();
    }
}