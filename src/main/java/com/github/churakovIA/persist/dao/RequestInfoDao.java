package com.github.churakovIA.persist.dao;

import com.github.churakovIA.model.RequestInfo;
import com.github.churakovIA.to.RequestInfoTo;
import java.util.List;

public interface RequestInfoDao {

  void save(RequestInfo requestInfo);

  RequestInfo get(int id);

  List<RequestInfo> getAll();

  List<RequestInfoTo> getLast(int count);

  void deleteFirst(int count);
}
