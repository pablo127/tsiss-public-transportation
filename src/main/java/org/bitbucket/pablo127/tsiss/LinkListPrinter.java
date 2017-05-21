package org.bitbucket.pablo127.tsiss;

import org.bitbucket.pablo127.tsiss.domain.StopDistance;

import java.io.PrintWriter;

import static org.bitbucket.pablo127.tsiss.StringUtil.getStringInQuotesEndWithSpace;

public class LinkListPrinter {

    private final LinesConverter linesConverter;

    public LinkListPrinter(LinesConverter linesConverter) {
        this.linesConverter = linesConverter;
    }

    public void print(PrintWriter printWriter) {
        printWriter.println(getHeader());
        printWriter.println(getStopDistanceRows());

        printWriter.flush();
    }

    private String getStopDistanceRows() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String lineNumber : linesConverter.getLineNumbersOrdered()) {
            for (int direction = 0; direction < 2; direction++)
                stringBuilder.append(getStopDistancesForLineWithDirection(lineNumber, direction));
        }
        return stringBuilder.toString();
    }

    private String getStopDistancesForLineWithDirection(String lineNumber, int direction) {
        StringBuilder stringBuilder = new StringBuilder();
        for (StopDistance stopDistance : linesConverter.getStopDistancesForLine(lineNumber, direction)) {
            stringBuilder.append(getStringInQuotesEndWithSpace(stopDistance.getFrom()))
                    .append(getStringInQuotesEndWithSpace(stopDistance.getTo()))
                    .append(getStringInQuotesEndWithSpace(stopDistance.getLine().getNumber()))
                    .append(getStringInQuotesEndWithSpace(stopDistance.getLine().getType()))
                    .append(getStringInQuotesEndWithSpace(stopDistance.getLine().getDirection()))
                    .append("\n");
        }
        return stringBuilder.toString();
    }

    private String getHeader() {
        return new StringBuilder()
                .append(getStringInQuotesEndWithSpace("stop_from"))
                .append(getStringInQuotesEndWithSpace("stop_to"))
                .append(getStringInQuotesEndWithSpace("line_number"))
                .append(getStringInQuotesEndWithSpace("line_type"))
                .append(getStringInQuotesEndWithSpace("line_direction"))
                .toString();
    }
}
