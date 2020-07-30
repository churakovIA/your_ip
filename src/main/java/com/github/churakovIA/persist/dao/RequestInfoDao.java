package com.github.churakovIA.persist.dao;

import com.github.churakovIA.model.RequestInfo;
import com.github.churakovIA.to.RequestInfoTo;
import java.util.List;

public interface RequestInfoDao {

  void save(RequestInfo requestInfo);

  RequestInfo get(int id);

  List<RequestInfo> getAll();

  List<RequestInfoTo> getFiltered(int count);

  List<RequestInfoTo> getFiltered(int count, String ip);

  void deleteFirst(int count);
}
