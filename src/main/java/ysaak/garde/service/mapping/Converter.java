package ysaak.garde.service.mapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Converter interface
 */
public interface Converter<Entity, DTO> {

  /**
   * Convert an entity to a DTO
   * @param entity Entity to convert
   * @return DTO
   */
  DTO convertEntity(Entity entity);

  /**
   * Convert a DTO to an entity
   * @param dto DTO to convert
   * @return Entity
   */
  Entity convertDTO(DTO dto);

  /**
   * Convert a stream of entities to a stream of DTOs
   * @param entities Stream of entities to convert
   * @return Stream of DTOs
   */
  default Stream<DTO> convertEntity(Stream<? extends Entity> entities) {
    if (entities == null) {
      return Stream.empty();
    }

    return entities.map(entity -> (entity != null) ? convertEntity(entity) : null);
  }

  /**
   * Convert a stream of DTOs to a stream of entities
   * @param dtos Stream of DTOs to convert
   * @return Stream of entities
   */
  default Stream<Entity> convertDTO(Stream<? extends DTO> dtos) {
    if (dtos == null) {
      return Stream.empty();
    }

    return dtos.map(entity -> (entity != null) ? convertDTO(entity) : null);
  }

  /**
   * Convert entities to a collection of DTOs
   * @param entities Entities to convert
   * @param collectionFactory Collection factory
   * @return Collection of DTOs
   */
  default <DTO_COLLECTION extends Collection<DTO>> DTO_COLLECTION convertEntity(Iterable<? extends Entity> entities, Supplier<DTO_COLLECTION> collectionFactory) {

    if (entities == null) {
      entities = Collections.emptyList();
    }

    return convertEntity(StreamSupport.stream(entities.spliterator(), false)).collect(Collectors.toCollection(collectionFactory));
  }

  /**
   * Convert DTOs to a collection of entities
   * @param dtos DTOs to convert
   * @param collectionFactory Collection factory
   * @return Collection of entities
   */
  default <ENTITY_COLLECTION extends Collection<Entity>> ENTITY_COLLECTION convertDTO(Iterable<? extends DTO> dtos, Supplier<ENTITY_COLLECTION> collectionFactory) {

    if (dtos == null) {
      dtos = Collections.emptyList();
    }

    return convertDTO(StreamSupport.stream(dtos.spliterator(), false)).collect(Collectors.toCollection(collectionFactory));
  }

  /**
   * Convert entities to a list of DTOs
   * @param entities Entities to convert
   * @return List of DTOs
   */
  default List<DTO> convertEntity(Iterable<? extends Entity> entities) {
    return convertEntity(entities, ArrayList::new);
  }

  /**
   * Convert DTOs to a list of entities
   * @param dtos DTOs to convert
   * @return List of entities
   */
  default List<Entity> convertDTO(Iterable<? extends DTO> dtos) {
    return convertDTO(dtos, ArrayList::new);
  }
}
