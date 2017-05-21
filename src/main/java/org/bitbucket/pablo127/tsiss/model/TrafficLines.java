package org.bitbucket.pablo127.tsiss.model;

import lombok.Data;

import java.util.List;

@Data
public class TrafficLines {

    private String type;

    private List<Feature> features;
}
