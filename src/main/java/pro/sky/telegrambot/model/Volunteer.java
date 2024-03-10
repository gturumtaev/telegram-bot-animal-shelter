package pro.sky.telegrambot.model;

import javax.persistence.*;

import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "volunteers")
public class Volunteer {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;
    private String firstName;
    private String phoneNumber;

    @JoinColumn(name = "shelter_id")
    @ManyToOne
    private Shelter shelter;

    public Volunteer() {

    }

    public Volunteer(Long id, String firstName, String phoneNumber, Shelter shelter) {
        this.id = id;
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
        this.shelter = shelter;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Shelter getShelter() {
        return shelter;
    }

    public void setShelter(Shelter shelter) {
        this.shelter = shelter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Volunteer volunteer = (Volunteer) o;
        return Objects.equals(id, volunteer.id) && Objects.equals(firstName, volunteer.firstName) && Objects.equals(phoneNumber, volunteer.phoneNumber) && Objects.equals(shelter, volunteer.shelter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, phoneNumber, shelter);
    }
}