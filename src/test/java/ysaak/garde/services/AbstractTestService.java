package ysaak.garde.services;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import ysaak.garde.service.mapping.MappingEngine;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Abstract test class for services
 */
class AbstractTestService {

  @Autowired
  protected MappingEngine mapper;

  @SafeVarargs
  protected final <T> void assertListContains(List<T> list, T... items) {
    assertListContains(list, Arrays.asList(items));
  }

  protected final <T> void assertListContains(List<T> list, List<T> items) {

    Assert.assertNotNull("List is null", list);
    Assert.assertNotNull("Item list is null", items);

    //Assert.assertTrue("List does not contains enough elements", list.size() >= items.size());

    for (T item : items) {
      Assert.assertTrue("List does not contains item : " + item, list.contains(item));
    }
  }

  protected Stream<Field> findAnnotatedFields(Class<?> clazz, Class<? extends Annotation> annotation) {
    Assert.assertNotNull("Class is null", clazz);
    Assert.assertNotNull("Annotation is null", annotation);

    Set<Field> fields = new HashSet<>();

    Class<?> c = clazz;
    while (c != null) {

      for (Field field : clazz.getDeclaredFields()) {
        if (field.isAnnotationPresent(annotation)) {
          fields.add(field);
        }
      }

      c = c.getSuperclass();
    }

    return fields.stream();
  }

  protected List<String> findAnnotatedFieldNames(Class<?> clazz, Class<? extends Annotation> annotation) {
    return findAnnotatedFields(clazz, annotation).map(Field::getName).collect(Collectors.toList());
  }
}
