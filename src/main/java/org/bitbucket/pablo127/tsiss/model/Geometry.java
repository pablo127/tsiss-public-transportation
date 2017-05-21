package org.bitbucket.pablo127.tsiss.model;

import lombok.Data;

import java.util.List;

@Data
public class Geometry {

    private String type;

    private List<List<Double>> coordinates;
}
