package org.bitbucket.pablo127.tsiss.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StopDistance {

    private String from;
    private String to;
    private Line line;
    private int order;

    public String getFromRaw() {
        return from.substring(0, from.length() - 2);
    }

    public String getToRaw() {
        return to.substring(0, to.length() - 2);
    }
}
