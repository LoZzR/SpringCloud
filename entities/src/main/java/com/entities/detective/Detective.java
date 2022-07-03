package com.entities.detective;

import com.entities.base.AbstractEntity;
import com.entities.person.Person;
import com.entities.util.EmploymentStatus;
import com.entities.util.Rank;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
public class Detective extends AbstractEntity {

    @NotNull
    @JoinColumn(name = "PERSON_ID")
    private Long personId;

    @Transient
    private Person person;

    @NotEmpty
    @Column(unique = true, nullable = false)
    private String badgeNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Rank rank;

    private Boolean armed = false;

    @NotNull
    @Enumerated(EnumType.STRING)
    private EmploymentStatus status = EmploymentStatus.ACTIVE;

    public Detective() {
        super();
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getBadgeNumber() {
        return badgeNumber;
    }

    public void setBadgeNumber(String badgeNumber) {
        this.badgeNumber = badgeNumber;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public Boolean getArmed() {
        return armed;
    }

    public void setArmed(Boolean armed) {
        this.armed = armed;
    }

    public EmploymentStatus getStatus() {
        return status;
    }

    public void setStatus(EmploymentStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        var detective = (Detective) o;
        return Objects.equals(person, detective.person) &&
                Objects.equals(badgeNumber, detective.badgeNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), person, badgeNumber);
    }

    @Override
    public String toString() {
        return String.format("Detective\n\t[person='%s', badgeNumber='%s', rank='%s', armed='%s', status='%s']",
                person.toString(), badgeNumber,rank, armed, status);
    }
}
