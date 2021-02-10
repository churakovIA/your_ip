package com.github.churakovIA.persist.dao;

import com.github.churakovIA.model.to.DummyTo;
import java.util.List;

public interface DummyDao {

  DummyTo get(int id);

  List<DummyTo> getAll();

  int save(DummyTo dummy);

  boolean delete(int id);
}
