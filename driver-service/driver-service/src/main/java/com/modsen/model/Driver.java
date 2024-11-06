package com.modsen.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "drivers")
public class Driver {
    @Id
    private String id;
    private String name;
    private String licenseNumber;
    private String phoneNumber;
}