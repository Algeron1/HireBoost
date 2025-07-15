package com.hireboost.service;

import com.hireboost.exception.TikaParseException;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Service
public class TikaAiParserService implements AiParserService {

    private final Tika tika = new Tika();

    @Override
    public String extractText(byte[] fileData, String fileName) {
        try (InputStream stream = new ByteArrayInputStream(fileData)) {
            return tika.parseToString(stream);
        } catch (Exception e) {
            throw new TikaParseException("Error parsing file: " + fileName);
        }
    }
}

