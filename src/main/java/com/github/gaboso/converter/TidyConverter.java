package com.github.gaboso.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.tidy.Tidy;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class TidyConverter {

    private static final String UTF_8 = StandardCharsets.UTF_8.toString();
    private static final Logger LOGGER = LoggerFactory.getLogger(TidyConverter.class);

    private TidyConverter() {
    }

    public static String toXHTML(String html) {
        Tidy tidy = new Tidy();
        tidy.setInputEncoding(UTF_8);
        tidy.setOutputEncoding(UTF_8);
        tidy.setXHTML(true);

        try (
            ByteArrayInputStream inputStream = new ByteArrayInputStream(html.getBytes(UTF_8));
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream()
        ) {
            tidy.parseDOM(inputStream, outputStream);
            return outputStream.toString(UTF_8);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return "";
    }

}