package org.bitbucket.pablo127.tsiss.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Line {
    private String number;
    private String type;
    private String direction;
}
