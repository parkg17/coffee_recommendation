package com.gyeol.coffee.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gyeol.coffee.dto.request.*;
import com.gyeol.coffee.dto.response.*;
import com.gyeol.coffee.model.dao.CoffeeReview;
import com.gyeol.coffee.model.repository.CoffeeReviewRepository;
import com.gyeol.coffee.model.service.CoffeeReviewDomainService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.json.simple.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class CoffeeReviewService {
    private final CoffeeReviewDomainService coffeeReviewDomainService;
    private final CoffeeReviewRepository coffeeReviewRepository;
    private static final String flask_url = "http://127.0.0.1:80/similar";
    private ObjectMapper objectMapper;

    /**
     * 커피 원두 항목별 추천
     * @param condition1 조건 1
     * @param condition2 조건 2
     * @param condition3 조건 3
     * @return GetItemRecommendationResponse
     */
    public GetItemRecommendationResponse getItemRecommendation(String condition1, String condition2, String condition3) {
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

        coffeeReviewDatas = coffeeReviewDatas.stream().sorted(compare).limit(10)
                .collect(Collectors.toList());

        return GetItemRecommendationResponse.builder()
                .rc(200)
                .result(coffeeReviewDatas)
                .build();
    }

    /**
     * 커피 원두 유사도 기반 추천
     * @param growing_region 커피 지역 데이터
     * @param tree_variety 커피 나무(품종) 데이터
     * @param roast_level 로스팅레벨 데이터
     * @return GetSimilarityRecommendationResponse
     * @throws JsonProcessingException 예외 처리
     * @throws IOException 예외 처리
     */
    public GetSimilarityRecommendationResponse getSimilarityRecommendation(String growing_region, String tree_variety, String roast_level)
            throws JsonProcessingException, IOException {
        log.info("[getSimilarityRecommendation]");

        JSONObject responseJson = new JSONObject();
        try {
            // Spring -> Flask 통신 연결
            URL start_url = new URL(flask_url);
            HttpURLConnection start_con = (HttpURLConnection) start_url.openConnection();
            start_con.setDoOutput(true);
            start_con.setRequestMethod("POST");
            start_con.setRequestProperty("Content-Type", "application/json");

            // Spring -> Flask JSON 전달
            BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(start_con.getOutputStream()));
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("growing_region", growing_region);
            jsonObject.put("tree_variety", tree_variety);
            jsonObject.put("roast_level", roast_level);
            wr.write(jsonObject.toString());
            wr.flush();
            wr.close();

            // Flask -> Spring Result Json 전달받음
            StringBuilder start_sb = new StringBuilder();
            int start_HttpResult = start_con.getResponseCode();

            // HTTP 통신 성공 시
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
                responseJson.put("result", "FAIL");
            }
        } catch (Exception e) {
            responseJson.put("result", "EXCEPTION");
        }

        List<CoffeeReview> resultData =
                objectMapper.readValue(String.valueOf(responseJson.get("data")), new TypeReference<List<CoffeeReview>>(){});

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

    /**
     * 커피 원두 매장별 추천
     * @param market
     * @return GetMarketRecommendationResponse
     */
    public GetMarketRecommendationResponse getMarketRecommendation(String market) {
        log.info("[getMarketRecommendation]");

        var coffeeReviewDatas = coffeeReviewRepository.findAll();

        Comparator<CoffeeReview> compare = Comparator.comparing(CoffeeReview::getAroma);

        if(market.equals("angelinus")) {    // 앤젤리너스 커피
            compare = Comparator.comparing(CoffeeReview::getAftertaste).reversed()
                    .thenComparing(CoffeeReview::getFlavor, Comparator.reverseOrder());
        }
        else if(market.equals("ediya")) {   // 이디야 커피
            compare = Comparator.comparing(CoffeeReview::getBody).reversed()
                    .thenComparing(CoffeeReview::getFlavor, Comparator.reverseOrder());
        }
        else if(market.equals("hollys")) {  // 할리스 커피
            compare = Comparator.comparing(CoffeeReview::getAcidity).reversed()
                    .thenComparing(CoffeeReview::getBody, Comparator.reverseOrder());
        }
        else if(market.equals("starbucks")) {   // 스타벅스 커피
            compare = Comparator.comparing(CoffeeReview::getAroma).reversed()
                    .thenComparing(CoffeeReview::getBody, Comparator.reverseOrder());
        }
        else if(market.equals("twosome")) {     // 투섬 커피
            compare = Comparator.comparing(CoffeeReview::getFlavor).reversed()
                    .thenComparing(CoffeeReview::getBody, Comparator.reverseOrder());
        }
        else if(market.equals("tomntoms")) {  // 커피빈 커피
            compare = Comparator.comparing(CoffeeReview::getFlavor).reversed()
                    .thenComparing(CoffeeReview::getAroma, Comparator.reverseOrder());
        }

        coffeeReviewDatas = coffeeReviewDatas.stream().sorted(compare).limit(10)
                .collect(Collectors.toList());

        return GetMarketRecommendationResponse.builder()
                .rc(200)
                .result(coffeeReviewDatas)
                .build();
    }
}
