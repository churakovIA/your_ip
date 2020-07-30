package com.github.churakovIA.persist;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Singleton class with lazy initialisation represents sql factory for connection to database.
 *
 * @author Churakov1-IA 11.03.2020
 */
public class DBProvider {

  private static final Logger log = LoggerFactory.getLogger(DBProvider.class);

  private DBProvider() {
    try {
      Class.forName("org.postgresql.Driver");
      URI dbUri = new URI(System.getenv("DATABASE_URL"));

      String username = dbUri.getUserInfo().split(":")[0];
      String password = dbUri.getUserInfo().split(":")[1];
      String dbUrl =
          "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

      this.connection = DriverManager.getConnection(dbUrl, username, password);
      this.connection.setAutoCommit(false);
    } catch (URISyntaxException | SQLException | ClassNotFoundException e) {
      log.error("Ошибка при подключении к БД: {}", e.getMessage());
    }
  }

  public static DBProvider getInstance() {
    return Holder.INSTANCE;
  }

  private static class Holder {

    static final DBProvider INSTANCE = new DBProvider();
  }

  private Connection connection;

  public Connection getConnection() {
    return connection;
  }
}
