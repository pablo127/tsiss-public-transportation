package org.bitbucket.pablo127.tsiss;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bitbucket.pablo127.tsiss.model.TrafficLines;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {

    private static final File PUBLIC_TRANSPORT_NETWORK_FILE = new File("data.txt");

    private static final File STOPS_FILE = new File("stopNames.txt");

    public static void main(String[] args) throws Exception {
        System.out.println("Started");
        long timestamp = System.currentTimeMillis();

        process();

        System.out.println("Finished with time: " + (System.currentTimeMillis() - timestamp) + " ms.");
    }

    private static void process() throws IOException {
        TrafficLines trafficLines = deserializeFile(PUBLIC_TRANSPORT_NETWORK_FILE, TrafficLines.class);
        TrafficLines stopNames = deserializeFile(STOPS_FILE, TrafficLines.class);

        LinesConverter linesConverter = new LinesConverter(trafficLines, stopNames);
        linesConverter.convert();

        new LinkListPrinter(linesConverter)
                .print(new PrintWriter(System.out));
    }

    private static <T> T deserializeFile(File file, Class<T> type) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        JsonParser jsonParser = new JsonFactory().createParser(file);
        jsonParser.setCodec(objectMapper);
        return jsonParser.readValueAs(type);
    }
}
