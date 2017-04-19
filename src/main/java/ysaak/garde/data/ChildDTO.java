package ysaak.garde.data;

import java.time.LocalDate;

/**
 * Child DTO
 */
public class ChildDTO {
  private Long id;

  private String lastName;

  private String firstName;

  private LocalDate birthDate;

  private String sickness;

  private String comments;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public LocalDate getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(LocalDate birthDate) {
    this.birthDate = birthDate;
  }

  public String getSickness() {
    return sickness;
  }

  public void setSickness(String sickness) {
    this.sickness = sickness;
  }

  public String getComments() {
    return comments;
  }

  public void setComments(String comments) {
    this.comments = comments;
  }
}
