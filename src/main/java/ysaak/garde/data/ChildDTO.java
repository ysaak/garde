package ysaak.garde.data;

import com.google.common.base.MoreObjects;

import java.time.LocalDate;
import java.util.Objects;

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


  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (!(obj instanceof ChildDTO)) return false;

    ChildDTO other = (ChildDTO) obj;

    return Objects.equals(id, other.id)
            && Objects.equals(lastName, other.lastName)
            && Objects.equals(firstName, other.firstName)
            && Objects.equals(birthDate, other.birthDate)
            && Objects.equals(sickness, other.sickness)
            && Objects.equals(comments, other.comments);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, lastName, firstName, birthDate, sickness, comments);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
            .add("id", id)
            .add("lastName", lastName)
            .add("firstName", firstName)
            .add("birthDate", birthDate)
            .add("sickness", sickness)
            .add("comments", comments)
            .toString();
  }
}
