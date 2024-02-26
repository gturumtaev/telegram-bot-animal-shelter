package pro.sky.telegrambot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

import static javax.persistence.GenerationType.*;

@Entity
@Table(name = "report_tg")
public class Report {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private long id;
    @Column(name = "date_added")
    private LocalDateTime dateAdded;

    @Column(name = "general_well_being")
    private String generalWellBeing;

    @Column(name = "photo_name")
    private String photoNameId;

    @Column(name = "check_report")
    private boolean checkReport;

    @JoinColumn(name = "clients_id")
    @ManyToOne
    @JsonIgnore
    private Client client;

    public Report() {
    }

    public Report(LocalDateTime dateAdded, String generalWellBeing, String photoNameId, boolean checkReport, Client client) {
        this.dateAdded = dateAdded;
        this.generalWellBeing = generalWellBeing;
        this.photoNameId = photoNameId;
        this.checkReport = checkReport;
        this.client = client;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(LocalDateTime dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getGeneralWellBeing() {
        return generalWellBeing;
    }

    public void setGeneralWellBeing(String generalWellBeing) {
        this.generalWellBeing = generalWellBeing;
    }

    public String getPhotoNameId() {
        return photoNameId;
    }

    public void setPhotoNameId(String photoNameId) {
        this.photoNameId = photoNameId;
    }

    public boolean isCheckReport() {
        return checkReport;
    }

    public void setCheckReport(boolean checkReport) {
        this.checkReport = checkReport;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Report report = (Report) o;

        if (id != report.id) return false;
        if (checkReport != report.checkReport) return false;
        if (!Objects.equals(dateAdded, report.dateAdded)) return false;
        if (!Objects.equals(generalWellBeing, report.generalWellBeing))
            return false;
        if (!Objects.equals(photoNameId, report.photoNameId)) return false;
        return Objects.equals(client, report.client);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (dateAdded != null ? dateAdded.hashCode() : 0);
        result = 31 * result + (generalWellBeing != null ? generalWellBeing.hashCode() : 0);
        result = 31 * result + (photoNameId != null ? photoNameId.hashCode() : 0);
        result = 31 * result + (checkReport ? 1 : 0);
        result = 31 * result + (client != null ? client.hashCode() : 0);
        return result;
    }

}