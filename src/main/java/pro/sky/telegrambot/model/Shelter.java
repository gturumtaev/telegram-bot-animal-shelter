package pro.sky.telegrambot.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Shelter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String workSchedule;
    private String address;
    private String phoneNumberSecurity;
    private String safetyPrecautions;

    public Shelter() {
    }

    public Shelter(String workSchedule, String address, String security, String safetyPrecautions) {
        this.workSchedule = workSchedule;
        this.address = address;
        this.phoneNumberSecurity = security;
        this.safetyPrecautions = safetyPrecautions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Shelter shelter = (Shelter) o;

        if (!Objects.equals(id, shelter.id)) return false;
        if (!Objects.equals(workSchedule, shelter.workSchedule))
            return false;
        if (!Objects.equals(address, shelter.address)) return false;
        if (!Objects.equals(phoneNumberSecurity, shelter.phoneNumberSecurity))
            return false;
        return Objects.equals(safetyPrecautions, shelter.safetyPrecautions);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (workSchedule != null ? workSchedule.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (phoneNumberSecurity != null ? phoneNumberSecurity.hashCode() : 0);
        result = 31 * result + (safetyPrecautions != null ? safetyPrecautions.hashCode() : 0);
        return result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWorkSchedule() {
        return workSchedule;
    }

    public void setWorkSchedule(String workSchedule) {
        this.workSchedule = workSchedule;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSecurity() {
        return phoneNumberSecurity;
    }

    public void setSecurity(String security) {
        this.phoneNumberSecurity = security;
    }

    public String getSafetyPrecautions() {
        return safetyPrecautions;
    }

    public void setSafetyPrecautions(String safetyPrecautions) {
        this.safetyPrecautions = safetyPrecautions;
    }

    @Override
    public String toString() {
        return "Shelter{" +
                "id=" + id +
                ", workSchedule='" + workSchedule + '\'' +
                ", address='" + address + '\'' +
                ", securityContact='" + phoneNumberSecurity + '\'' +
                ", safetyPrecautions='" + safetyPrecautions + '\'' +
                '}';
    }
}