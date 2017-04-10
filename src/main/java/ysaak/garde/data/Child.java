package ysaak.garde.data;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

@Data
@Entity
public class Child {
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  @Column(unique = true, nullable = false)
  private Long id;

  @NotEmpty
  @Column(nullable = false)
  private String lastName;

  @NotEmpty
  @Column(nullable = false)
  private String firstName;
  
  @Column
  private LocalDate birthDate;

  @Column
  private String sickness;

  @Column
  private String comments;
}
