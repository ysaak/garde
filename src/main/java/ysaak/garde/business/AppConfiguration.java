package ysaak.garde.business;

import com.google.common.reflect.ClassPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ysaak.garde.service.mapping.Converter;
import ysaak.garde.service.mapping.MappingEngine;

import java.io.IOException;

/**
 * Application configuration
 */
@Configuration
public class AppConfiguration {
  private static final Logger LOGGER = LoggerFactory.getLogger(AppConfiguration.class);

  private static final String MAPPERS_PACKAGE = AppConfiguration.class.getPackage().getName() + ".converter";

  @Bean
  @SuppressWarnings("unchecked")
  public MappingEngine getMappingEngine() {

    MappingEngine engine = new MappingEngine();

    // Dynamically load converters
    try {
      ClassPath classpath = ClassPath.from(getClass().getClassLoader());

      for (ClassPath.ClassInfo classInfo : classpath.getTopLevelClassesRecursive(MAPPERS_PACKAGE)) {
        Class<?> clazz = classInfo.load();

        if (Converter.class.isAssignableFrom(clazz)) {
          engine.register((Class<Converter<?,?>>) clazz);
        }
      }
    }
    catch (IOException e) {
      LOGGER.error("Error while loading converters", e);
    }

    return engine;
  }
}
