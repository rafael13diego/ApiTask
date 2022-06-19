package com.spring.professional.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Root{

    public String name;
    public int id;
    public double latitude;
    public double longitude;
    public double altitude;
    public double velocity;
    public String visibility;
    public double footprint;
    public int timestamp;
    public double daynum;
    public double solar_lat;
    public double solar_lon;
    public String units;
}
