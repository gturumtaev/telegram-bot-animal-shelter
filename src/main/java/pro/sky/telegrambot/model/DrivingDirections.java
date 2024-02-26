package pro.sky.telegrambot.model;

import org.hibernate.annotations.Type;
import pro.sky.telegrambot.model.Shelter;

import javax.persistence.*;

@Entity
public class DrivingDirections {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filePath;


    @OneToOne
    private Shelter shelter;

    public DrivingDirections() {
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getFilePath() {
        return filePath;
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    public Shelter getShelter() {
        return shelter;
    }
    public void setShelter(Shelter shelter) {
        this.shelter = shelter;
    }
}