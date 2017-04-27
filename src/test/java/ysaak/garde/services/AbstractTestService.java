package ysaak.garde.services;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import ysaak.garde.service.mapping.MappingEngine;

import java.util.List;

/**
 * Created by ysaak on 20/04/2017.
 */
class AbstractTestService {

  @Autowired
  protected MappingEngine mapper;

  @SafeVarargs
  protected final <T> void assertListContains(List<T> list, T... items) {

    Assert.assertNotNull(list);
    Assert.assertTrue("List does not contains enough elements", list.size() >= items.length);

    for (T item : items) {
      Assert.assertTrue("List does not contains item : " + item, list.contains(item));
    }
  }
}
