package com.gyeol.coffee.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.gyeol.coffee.dto.data.CoffeeReviewData;
import com.gyeol.coffee.dto.request.GetSimilarityRecommendationRequest;
import com.gyeol.coffee.dto.response.GetItemRecommendationResponse;
import com.gyeol.coffee.dto.response.GetSimilarityRecommendationResponse;
import com.gyeol.coffee.model.dao.CoffeeReview;
import com.gyeol.coffee.model.repository.CoffeeReviewRepository;
import com.gyeol.coffee.model.service.CoffeeReviewDomainService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import org.json.simple.*;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@AllArgsConstructor
public class CoffeeReviewService {
    private final CoffeeReviewDomainService coffeeReviewDomainService;
    private final CoffeeReviewRepository coffeeReviewRepository;
    private ObjectMapper objectMapper;

    public GetItemRecommendationResponse getItemRecommendation(String condition1, String condition2, String condition3)
            throws JsonProcessingException {
        log.info("[getItemRecommendation]");

        var coffeeReviewDatas = coffeeReviewRepository.findAll();
        String cond;
        Comparator<CoffeeReview> compare = Comparator.comparing(CoffeeReview::getAroma);
        for(int idx = 0; idx < 3; ++idx) {
            if(idx == 0) {
                cond = condition1;
            }
            else if(idx == 1) {
                cond = condition2;
            }
            else {
                cond = condition3;
            }
            // string compare
            if(cond.equals("aroma")) {
                if(idx == 0) {
                    compare = Comparator.comparing(CoffeeReview::getAroma).reversed();
                }
                else {
                    compare = compare.thenComparing(CoffeeReview::getAroma, Comparator.reverseOrder());
                }
            }
            else if(cond.equals("acidity")) {
                if(idx == 0) {
                    compare = Comparator.comparing(CoffeeReview::getAcidity).reversed();
                }
                else {
                    compare = compare.thenComparing(CoffeeReview::getAcidity, Comparator.reverseOrder());
                }
            }
            else if(cond.equals("body")) {
                if(idx == 0) {
                    compare = Comparator.comparing(CoffeeReview::getBody).reversed();
                }
                else {
                    compare = compare.thenComparing(CoffeeReview::getBody, Comparator.reverseOrder());
                }
            }
            else if(cond.equals("flavor")) {
                if(idx == 0) {
                    compare = Comparator.comparing(CoffeeReview::getFlavor).reversed();
                }
                else {
                    compare = compare.thenComparing(CoffeeReview::getFlavor, Comparator.reverseOrder());
                }
            }
            else if(cond.equals("aftertaste")) {
                if(idx == 0) {
                    compare = Comparator.comparing(CoffeeReview::getAftertaste).reversed();
                }
                else {
                    compare = compare.thenComparing(CoffeeReview::getAftertaste, Comparator.reverseOrder());
                }
            }
        }

        coffeeReviewDatas = coffeeReviewDatas.stream().sorted(compare)
                .collect(Collectors.toList());

        return GetItemRecommendationResponse.builder()
                .rc(200)
                .result(coffeeReviewDatas)
                .build();
    }

    //public GetSimilarityRecommendationResponse getSimilarityRecommendation(String growing_region, String tree_variety, String roast_level)
    public GetSimilarityRecommendationResponse getSimilarityRecommendation(GetSimilarityRecommendationRequest req)
            throws JsonProcessingException, IOException {
        log.info("[getSimilarityRecommendation]");

        String url = "http://127.0.0.1:80/similar";

        JSONObject responseJson = new JSONObject();

        try {
            // 연결할 url 생성
            URL start_object = new URL(url);

            // http 객체 생성
            HttpURLConnection start_con = (HttpURLConnection) start_object.openConnection();
            start_con.setDoOutput(true);
            //start_con.setDoInput(true);

            // 설정 정보
            start_con.setRequestMethod("POST");
            start_con.setRequestProperty("Content-Type", "application/json");
            //start_con.setRequestProperty("Accept", "application/json");
            //start_con.setRequestMethod(option);

            // 출력 부분
            BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(start_con.getOutputStream()));
            //OutputStreamWriter wr = new OutputStreamWriter(start_con.getOutputStream());
            log.info("[req.toString()] " + req.toString());

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("growing_region", req.getGrowing_region());
            jsonObject.put("tree_variety", req.getTree_variety());
            jsonObject.put("roast_level", req.getRoast_level());

            log.info("[jsonObject.toString()]" + jsonObject.toString());


            wr.write(jsonObject.toString());
            wr.flush();
            wr.close();

            // 응답 받는 부분
            StringBuilder start_sb = new StringBuilder();
            int start_HttpResult = start_con.getResponseCode();

            // 결과 성공일 경우 = HttpResult 200일 경우
            if (start_HttpResult == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(start_con.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    start_sb.append(line);
                }
                responseJson.put("data", start_sb);
                responseJson.put("result", "SUCCESS");
                br.close();
            } else {
                // 그 외의 경우(실패)
                responseJson.put("result", "FAIL");
            }
        } catch (Exception e) {
            responseJson.put("result", "EXCEPTION");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        List<CoffeeReview> resultData = objectMapper.readValue(String.valueOf(responseJson.get("data")), new TypeReference<List<CoffeeReview>>(){});

        if (String.valueOf(responseJson.get("result")).equals("SUCCESS")) {
            return GetSimilarityRecommendationResponse.builder()
                    .rc(200)
                    .result(resultData)
                    .build();
        }
        else {
            return GetSimilarityRecommendationResponse.builder()
                    .rc(400)
                    .build();
        }
    }
}
