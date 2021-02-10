package com.github.churakovIA.persist.dao;

import com.github.churakovIA.model.to.DummyTo;
import com.github.churakovIA.persist.DBProvider;
import com.github.churakovIA.util.exeption.ApplicationException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DummyDaoImpl implements DummyDao {

  private static final String NEXTVAL = "SELECT nextval('global_seq')";
  private static final String DELETE_BY_ID = "DELETE FROM dummies WHERE id = ?";
  private static final String DUMMY_ALL = "SELECT id, date, description, content_type, dummy FROM dummies";
  private static final String DUMMY_BY_ID = "SELECT id, date, description, content_type, dummy FROM dummies WHERE id = ?";
  private static final String INSERT_DUMMY = "INSERT INTO dummies (description, content_type, dummy, id) VALUES (?,?,?,currval('global_seq'))";

  @Override
  public DummyTo get(int id) {
    try (PreparedStatement statement = connection.prepareStatement(DUMMY_BY_ID)) {
      statement.setInt(1, id);
      List<DummyTo> list = new ArrayList<>();
      try (ResultSet rs = statement.executeQuery()) {
        while (rs.next()) {
          list.add(
              new DummyTo(
                  rs.getInt(1),
                  rs.getTimestamp(2).toString(),
                  rs.getString(3),
                  rs.getString(4),
                  rs.getString(5)
              )
          );
        }
      }
      return list.size() == 1 ? list.get(0) : null;
    } catch (SQLException e) {
      log.error(e.getMessage());
      throw new ApplicationException("Ошибка при получении", e);
    }
  }

  @Override
  public List<DummyTo> getAll() {
    try (PreparedStatement statement = connection.prepareStatement(DUMMY_ALL)) {
      List<DummyTo> list = new ArrayList<>();
      try (ResultSet rs = statement.executeQuery()) {
        while (rs.next()) {
          list.add(
              new DummyTo(
                  rs.getInt(1),
                  rs.getTimestamp(2).toString(),
                  rs.getString(3),
                  rs.getString(4),
                  rs.getString(5)
              )
          );
        }
      }
      return list;
    } catch (SQLException e) {
      log.error(e.getMessage());
      throw new ApplicationException("Ошибка при получении", e);
    }
  }

  @Override
  public int save(DummyTo dummy) {
    Objects.requireNonNull(dummy, "dummy must not be null");
    try (PreparedStatement statementInsert = connection.prepareStatement(INSERT_DUMMY);
        Statement statement = connection.createStatement()) {

      int id;
      try (ResultSet resultSet = statement.executeQuery(NEXTVAL)) {
        resultSet.next();
        id = resultSet.getInt(1);
      }

      statementInsert.setString(1, dummy.getDescription());
      statementInsert.setString(2, dummy.getContentType());
      statementInsert.setString(3, dummy.getDummy());
      statementInsert.executeUpdate();

      connection.commit();
      return id;
    } catch (SQLException e) {
      log.error("error save to DB", e);
      try {
        connection.rollback();
      } catch (SQLException ex) {
        log.error(e.getMessage());
        throw new ApplicationException("Ошибка при откате записи", e);
      }
      throw new ApplicationException("Ошибка при записи", e);
    }
  }

  @Override
  public boolean delete(int id) {
    try (PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID)) {
      statement.setInt(1, id);
      int rows = statement.executeUpdate();
      connection.commit();
      log.debug("delete {} rows", rows);
      return rows > 0;
    } catch (SQLException e) {
      log.error(e.getMessage());
      throw new ApplicationException("Ошибка при удалении", e);
    }
  }

  private static final Logger log = LoggerFactory.getLogger(DummyDaoImpl.class);

  public static DummyDaoImpl getInstance() {
    return DummyDaoImpl.Holder.INSTANCE;
  }

  private Connection connection;


  private DummyDaoImpl() {
    this.connection = DBProvider.getInstance().getConnection();
  }

  private static class Holder {

    static final DummyDaoImpl INSTANCE = new DummyDaoImpl();
  }

}
