package ysaak.garde.business.model;

import com.google.common.base.MoreObjects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Child {

  @Column(unique = true, nullable = false)
  private Long id;

  @NotNull
  private String lastName;

  @NotNull
  private String firstName;

  private LocalDate birthDate;

  private String sickness;

  private String comments;

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Column(nullable = false)
  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  @Column(nullable = false)
  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  @Column
  public LocalDate getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(LocalDate birthDate) {
    this.birthDate = birthDate;
  }

  @Column
  public String getSickness() {
    return sickness;
  }

  public void setSickness(String sickness) {
    this.sickness = sickness;
  }

  @Column
  public String getComments() {
    return comments;
  }

  public void setComments(String comments) {
    this.comments = comments;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (!(obj instanceof Child)) return false;

    Child other = (Child) obj;

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
