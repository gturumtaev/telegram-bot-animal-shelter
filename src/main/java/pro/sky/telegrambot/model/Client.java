package pro.sky.telegrambot.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "took_a_pet")
    private Boolean tookAPet;

    @Column(name = "chat_id")
    private Long chatId;
    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "date_time_to_took")
    private LocalDateTime dateTimeToTook;
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Report> reports = new ArrayList<>();

    public Client() {
    }

    public Client(String firstName, String phoneNumber) {
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (!Objects.equals(firstName, client.firstName)) return false;
        return Objects.equals(phoneNumber, client.phoneNumber);
    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        return result;
    }

    public List<Report> getReports() {
        return reports;
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
    }

    public void setDateTimeToTook(LocalDateTime dateTimeToTook) {
        this.dateTimeToTook = dateTimeToTook;
    }

    public LocalDateTime getDateTimeToTook() {
        return dateTimeToTook;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setTookAPet(Boolean tookAPet) {
        this.tookAPet = tookAPet;
    }

    public Boolean getTookAPet() {
        return tookAPet;
    }
}