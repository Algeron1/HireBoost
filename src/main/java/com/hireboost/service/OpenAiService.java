package com.hireboost.service;

import com.hireboost.promt.Promts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class OpenAiService {

    private static final Pattern NUMBER_PATTERN = Pattern.compile("\\d+");
    private static final String MODEL = "gpt-4o";
    private static final String ROLE_USER = "user";

    private final RestTemplate restTemplate;

    @Value("${openai.api.key}")
    private String openAiApiKey;

    @Value("${openai.api.score.url}")
    private String apiUrl;

    public String evaluateResumeScore(String resumeText) {
        try {
            String prompt = String.format(Promts.RESUME_ANALYZE_PROMT_RU, resumeText);
            Map<String, Object> requestBody = createRequestBody(prompt);

            ResponseEntity<Map> response = sendRequest(requestBody);

            return extractScoreFromResponse(response.getBody());
        } catch (RestClientException e) {
            log.error("Error while calling OpenAI API", e);
            return "0";
        } catch (Exception e) {
            log.error("Unexpected error during resume evaluation", e);
            return "0";
        }
    }

    private Map<String, Object> createRequestBody(String prompt) {
        return Map.of(
                "model", MODEL,
                "messages", List.of(Map.of(
                        "role", ROLE_USER,
                        "content", prompt
                ))
        );
    }

    private ResponseEntity<Map> sendRequest(Map<String, Object> requestBody) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openAiApiKey);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
        return restTemplate.exchange(apiUrl, HttpMethod.POST,entity,Map.class);
    }

    @SuppressWarnings("unchecked")
    private String extractScoreFromResponse(Map<String, Object> responseBody) {
        try {
            List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");
            Map<String, Object> firstChoice = choices.get(0);
            Map<String, Object> message = (Map<String, Object>) firstChoice.get("message");
            String content = message.get("content").toString();

            return extractNumberFromText(content);
        } catch (Exception e) {
            log.error("Error parsing OpenAI response", e);
            return "0";
        }
    }

    private String extractNumberFromText(String text) {
        return NUMBER_PATTERN.matcher(text)
                .results()
                .findFirst()
                .map(MatchResult::group)
                .orElse("0");
    }

    public String fullResumeAnalysis(String resumeText) {
        try {
            String prompt = String.format(Promts.RESUME_FULL_ANALYSIS_PROMT_RU, resumeText);
            Map<String, Object> requestBody = createRequestBody(prompt);

            ResponseEntity<Map> response = sendRequest(requestBody);

            return extractFullTextFromResponse(response.getBody());
        } catch (RestClientException e) {
            log.error("Error while calling OpenAI API", e);
            return "The analysis failed due to an API error.";
        } catch (Exception e) {
            log.error("Unexpected error during full resume analysis", e);
            return "An unexpected error occurred.";
        }
    }

    @SuppressWarnings("unchecked")
    private String extractFullTextFromResponse(Map<String, Object> responseBody) {
        try {
            List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");
            Map<String, Object> firstChoice = choices.get(0);
            Map<String, Object> message = (Map<String, Object>) firstChoice.get("message");
            return message.get("content").toString();
        } catch (Exception e) {
            log.error("Error parsing OpenAI response", e);
            return "Error parsing OpenAI response";
        }
    }

    public String generateText(String prompt) {
        try {
            Map<String, Object> requestBody = createRequestBody(prompt);
            ResponseEntity<Map> response = sendRequest(requestBody);
            return extractFullTextFromResponse(response.getBody());
        } catch (RestClientException e) {
            log.error("Error while calling OpenAI API", e);
            return "Error while calling OpenAI API";
        } catch (Exception e) {
            log.error("Unexpected error during text generation", e);
            return "Unexpected error during text generation";
        }
    }

}