package com.github.churakovIA.persist.dao;

import com.github.churakovIA.model.RequestInfo;
import com.github.churakovIA.persist.DBProvider;
import com.github.churakovIA.to.RequestInfoTo;
import com.github.churakovIA.util.exeption.ApplicationException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestInfoDaoImpl implements RequestInfoDao {

  private static final int LIMIT_DB_ROWS = 9000;
  private static final int DELETE_FIRST_ROWS = 5;

  private static final String DELETE_FIRST = "DELETE FROM requests WHERE ctid IN (SELECT ctid FROM requests ORDER BY date LIMIT ?)";
  private static final String TOTAL_DB_ROWS = "SELECT SUM(n_live_tup) FROM pg_stat_user_tables";
  private static final String NEXTVAL = "SELECT nextval('global_seq')";
  private static final String INSERT_REQUESTS = "INSERT INTO requests (protocol, ip, method, full_url, locale, body, id) VALUES (?,?,?,?,?,?,currval('global_seq'))";
  private static final String INSERT_HEADERS = "INSERT INTO headers (request_id, name, value) VALUES (?,?,?)";
  private static final String LAST_REQUESTS = "SELECT id, date, ip, method, full_url, locale, protocol, body FROM requests WHERE id IN (SELECT id FROM requests ORDER BY date desc LIMIT ?) ORDER BY date desc";
  private static final String LAST_REQUESTS_BY_IP = "SELECT id, date, ip, method, full_url, locale, protocol, body FROM requests WHERE id IN (SELECT id FROM requests WHERE ip LIKE ? ORDER BY date desc LIMIT ?) ORDER BY date desc";
  private static final String REQUESTS_BY_ID = "SELECT id, date, ip, method, full_url, locale, protocol, body FROM requests WHERE id = ?";
  private static final String HEADERS = "SELECT request_id, name, value FROM headers WHERE request_id IN (SELECT * FROM unnest(?)) ORDER BY request_id, name desc";

  @Override
  public void save(RequestInfo requestInfo) {
    Objects.requireNonNull(requestInfo, "requestInfo must not be null");
    try (PreparedStatement statementRequest = connection.prepareStatement(INSERT_REQUESTS);
        PreparedStatement statementHeaders = connection.prepareStatement(INSERT_HEADERS);
        Statement statement = connection.createStatement()) {

      int totalRows;
      try (ResultSet resultSet = statement.executeQuery(TOTAL_DB_ROWS)) {
        resultSet.next();
        totalRows = resultSet.getInt(1);
        if (totalRows >= LIMIT_DB_ROWS) {
          deleteFirst(DELETE_FIRST_ROWS);
        }
      }

      int id;
      try (ResultSet resultSet = statement.executeQuery(NEXTVAL)) {
        resultSet.next();
        id = resultSet.getInt(1);
      }

      statementRequest.setString(1, requestInfo.getProtocol());
      statementRequest.setString(2, requestInfo.getIp());
      statementRequest.setString(3, requestInfo.getMethod());
      statementRequest.setString(4, requestInfo.getFullURL());
      statementRequest.setString(5, requestInfo.getLocale());
      statementRequest.setString(6, requestInfo.getBody());
      statementRequest.executeUpdate();

      for (Entry<String, String> entry : requestInfo.getHeaders().entrySet()) {
        statementHeaders.setInt(1, id);
        statementHeaders.setString(2, entry.getKey());
        statementHeaders.setString(3, entry.getValue());
        statementHeaders.addBatch();
      }
      statementHeaders.executeBatch();

      connection.commit();
    } catch (SQLException e) {
      log.error(e.getMessage());
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
  public RequestInfoTo get(int id) {
    try (PreparedStatement statementRequest = connection.prepareStatement(REQUESTS_BY_ID);
        PreparedStatement statementHeader = connection.prepareStatement(HEADERS)) {
      statementRequest.setInt(1, id);
      List<RequestInfoTo> infoList = fillRequestInfoList(statementRequest, statementHeader);
      return infoList.size() == 1 ? infoList.get(0) : null;
    } catch (SQLException e) {
      log.error(e.getMessage());
      throw new ApplicationException("Ошибка при получении", e);
    }
  }

  @Override
  public List<RequestInfo> getAll() {
    return null;
  }

  @Override
  public List<RequestInfoTo> getFiltered(int count) {
    try (PreparedStatement statementRequest = connection.prepareStatement(LAST_REQUESTS);
        PreparedStatement statementHeader = connection.prepareStatement(HEADERS)) {
      statementRequest.setInt(1, count);
      return fillRequestInfoList(statementRequest, statementHeader);
    } catch (SQLException e) {
      log.error(e.getMessage());
      throw new ApplicationException("Ошибка при получении", e);
    }
  }

  @Override
  public List<RequestInfoTo> getFiltered(int count, String ip) {
    try (PreparedStatement statementRequest = connection.prepareStatement(LAST_REQUESTS_BY_IP);
        PreparedStatement statementHeader = connection.prepareStatement(HEADERS)) {
      statementRequest.setString(1, "%" + ip + "%");
      statementRequest.setInt(2, count);
      return fillRequestInfoList(statementRequest, statementHeader);
    } catch (SQLException e) {
      log.error(e.getMessage());
      throw new ApplicationException("Ошибка при получении", e);
    }
  }

  private List<RequestInfoTo> fillRequestInfoList(PreparedStatement statementRequest,
      PreparedStatement statementHeader)
      throws SQLException {
    Map<Integer, RequestInfoTo> requestInfoMap = new LinkedHashMap<>();
    try (ResultSet rs = statementRequest.executeQuery()) {
      while (rs.next()) {
        RequestInfoTo req = RequestInfoTo.builder()
            .id(rs.getInt(1))
            .date(rs.getTimestamp(2).toString())
            .ip(rs.getString(3))
            .method(rs.getString(4))
            .fullURL(rs.getString(5))
            .locale(rs.getString(6))
            .protocol(rs.getString(7))
            .body(rs.getString(8))
            .headers(new LinkedHashMap<>())
            .build();
        requestInfoMap.put(req.getId(), req);
      }
    }

    statementHeader
        .setArray(1, connection.createArrayOf("INTEGER", requestInfoMap.keySet().toArray()));
    try (ResultSet rs = statementHeader.executeQuery()) {
      while (rs.next()) {
        requestInfoMap.get(rs.getInt(1)).getHeaders().put(rs.getString(2), rs.getString(3));
      }
    }
    return new ArrayList<>(requestInfoMap.values());
  }

  @Override
  public void deleteFirst(int count) {
    try (PreparedStatement statementRequest = connection.prepareStatement(DELETE_FIRST)) {
      statementRequest.setInt(1, count);
      int rows = statementRequest.executeUpdate();
      connection.commit();
      log.debug("delete {} rows", rows);
    } catch (SQLException e) {
      log.error(e.getMessage());
      throw new ApplicationException("Ошибка при удалении", e);
    }
  }

  private static final Logger log = LoggerFactory.getLogger(RequestInfoDaoImpl.class);

  public static RequestInfoDaoImpl getInstance() {
    return Holder.INSTANCE;
  }

  private Connection connection;


  private RequestInfoDaoImpl() {
    this.connection = DBProvider.getInstance().getConnection();
  }

  private static class Holder {

    static final RequestInfoDaoImpl INSTANCE = new RequestInfoDaoImpl();
  }

}
