package org.bitbucket.pablo127.tsiss;

import lombok.Getter;
import org.bitbucket.pablo127.tsiss.domain.Line;
import org.bitbucket.pablo127.tsiss.domain.StopDistance;
import org.bitbucket.pablo127.tsiss.model.Feature;
import org.bitbucket.pablo127.tsiss.model.Properties;
import org.bitbucket.pablo127.tsiss.model.TrafficLines;

import java.util.*;
import java.util.stream.Collectors;

public class LinesConverter {

    private static final Map<String, String> LINE_TYPE_SHORT_DESCRIPTIVE_MAP = Collections.unmodifiableMap(
            new HashMap<String, String>() {
                {
                    put("B", "autobus");
                    put("T", "tramwaj");
                    put("N", "autobus nocny");
                    put("X", "tramwaj nocny");
                }
            });

    private final TrafficLines trafficLines;
    private final TrafficLines stopNames;

    @Getter
    private final Set<Line> lines = new HashSet<>();

    @Getter
    private final Set<StopDistance> stopDistances = new HashSet<>();

    public LinesConverter(TrafficLines lines, TrafficLines stopNames) {
        this.trafficLines = lines;
        this.stopNames = stopNames;
    }

    public void convert() {
        processLines();
        processStopNames();
    }

    private void processLines() {
        for (Feature feature : trafficLines.getFeatures()) {
            Properties properties = feature.getProperties();
            Line line = getLine(properties);
            StopDistance stopDistance = getStopDistance(properties, line);

            lines.add(line);
            stopDistances.add(stopDistance);
        }
        explainLineTypes();
    }

    private void processStopNames() {
        Map<String, String> stopIdStopNameMap = new HashMap<>();
        for (Feature feature : stopNames.getFeatures()) {
            stopIdStopNameMap.put(
                    feature.getId(),
                    feature.getProperties().getStopName());
        }
        translateStops(stopIdStopNameMap);
    }

    private void translateStops(Map<String, String> stopIdStopNameMap) {
        for (StopDistance stopDistance : stopDistances) {
            stopDistance.setFrom(
                    stopIdStopNameMap.keySet()
                            .stream()
                            .filter(key -> stopDistance.getFromRaw()
                                    .equals(key.substring(0, 4)))
                            .findFirst()
                            .map(stopIdStopNameMap::get)
                            .orElse(stopDistance.getFromRaw()));
            stopDistance.setTo(
                    stopIdStopNameMap.keySet()
                            .stream()
                            .filter(key -> stopDistance.getToRaw()
                                    .equals(key.substring(0, 4)))
                            .findFirst()
                            .map(stopIdStopNameMap::get)
                            .orElse(stopDistance.getToRaw()));
        }
    }

    private void explainLineTypes() {
        for (Line line : lines) {
            line.setType(
                    Optional.ofNullable(
                            LINE_TYPE_SHORT_DESCRIPTIVE_MAP.get(line.getType()))
                            .orElse(line.getType()));
        }
    }

    public List<StopDistance> getStopDistancesForLine(String lineNumber, int direction) {
        return stopDistances.stream()
                .filter(stopDistance -> stopDistance.getLine().getNumber().equals(lineNumber)
                        && stopDistance.getLine().getDirection().equals(String.valueOf(direction)))
                .sorted((o1, o2) -> o2.getOrder() - o1.getOrder())
                .collect(Collectors.toList());
    }

    private StopDistance getStopDistance(Properties properties, Line line) {
        return stopDistances.stream()
                .filter(stopDistance -> stopDistance.getFrom().equals(properties.getStopFrom())
                        && stopDistance.getTo().equals(properties.getStopTo())
                        && stopDistance.getLine().getNumber().equals(properties.getLineNumber())
                        && stopDistance.getOrder() == Integer.valueOf(properties.getOrder()))
                .findFirst()
                .orElse(new StopDistance(properties.getStopFrom(), properties.getStopTo(), line,
                        Integer.valueOf(properties.getOrder())));
    }

    private Line getLine(Properties properties) {
        return lines.stream()
                .filter(line -> line.getNumber().equals(properties.getLineNumber())
                        && line.getDirection().equals(properties.getLineDirection()))
                .findFirst()
                .orElse(new Line(properties.getLineNumber(), properties.getLineType(), properties.getLineDirection()));
    }

    public List<String> getLineNumbersOrdered() {
        return lines.stream()
                .map(Line::getNumber)
                .distinct()
                .sorted(String::compareTo)
                .collect(Collectors.toList());
    }
}
