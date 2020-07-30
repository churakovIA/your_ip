package com.github.churakovIA.persist;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;

class DBProviderTest {

  @Test
  void getInstance() {
    assertNotNull(DBProvider.getInstance());
  }

  @Test
  void getConnection() throws SQLException {
    Connection connection = DBProvider.getInstance().getConnection();
    assertNotNull(connection);
    assertFalse(connection.isClosed());
  }
}