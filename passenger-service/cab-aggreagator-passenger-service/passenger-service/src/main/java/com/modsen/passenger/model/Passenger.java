package com.modsen.passenger.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "passengers")
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String startPoint;
    private String finalPoint;
    private String status;

    @Override
    public String toString() {
        return "Passenger{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", startPoint='" + startPoint + '\'' +
                ", finalPoint='" + finalPoint + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
