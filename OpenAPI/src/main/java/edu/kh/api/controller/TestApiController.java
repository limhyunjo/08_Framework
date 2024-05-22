package edu.kh.api.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class TestApiController {

	private final String serviceKey = "9p1AY7EhDSYQLjlaf2bdyHV+9ooK9iERT5cm8TSacJqPg3yXPJkyGOwkjmKVzeuCYkPDG0W/C3S5J+YfH1n9kQ==";

	@GetMapping("air")

	public String airPollution(@RequestParam(value = "location", required = false, defaultValue = "서울") String location,
			Model model) throws IOException {

		// 요청 주소 Call Back URL이 작성되지 않았다
		String requestUrl = "http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty";
		String returnType = "JSON";// returnType을 넣을 변수 선언을 안함

		int numOfRows = 100; // 조회할 데이터 개수
		int pageNo = 1;
		String ver = "1.0";

		/*
		 * // 지역 좌표가 저장된 map final Map<String, String> coordinateList = new HashMap<>();
		 * 
		 * coordinateList.put("서울", "60/127"); coordinateList.put("경기", "60/120");
		 * coordinateList.put("인천", "55/38"); coordinateList.put("제주", "52/38");
		 * coordinateList.put("세종", "66/103"); coordinateList.put("광주", "58/74");
		 * coordinateList.put("대구", "89/90"); coordinateList.put("대전", "67/100");
		 * coordinateList.put("부산", "98/76"); coordinateList.put("울산", "102/84");
		 * coordinateList.put("강원", "73/134"); coordinateList.put("경북", "89/91");
		 * coordinateList.put("경남", "91/77"); coordinateList.put("전북", "63/89");
		 * coordinateList.put("전남", "51/67"); coordinateList.put("충북", "69/107");
		 * coordinateList.put("충남", "68/100");
		 * 
		 * 
		 * String[] arr = (coordinateList.get(location)).split("/");
		 */

		String sidoName = location;

		StringBuilder urlBuilder = new StringBuilder(requestUrl);

		// 이것도 작성 안됨 쿼리 스트링
		urlBuilder.append("?" + URLEncoder.encode("sidoName", "UTF-8") + "="
				+ URLEncoder.encode(String.valueOf(sidoName), "UTF-8"));

		
		  urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" +
		  URLEncoder.encode(String.valueOf(pageNo), "UTF-8")); 
		  
		  urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" +
		 URLEncoder.encode(String.valueOf(numOfRows), "UTF-8")); 
		 
		  urlBuilder.append("&" + URLEncoder.encode("returnType","UTF-8") + "=" +
		 URLEncoder.encode(returnType, "UTF-8"));
		 
		urlBuilder.append("&" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + serviceKey); /* Service Key */

		
		  urlBuilder.append( "&" + URLEncoder.encode("ver", "UTF-8") + "=" +
		 URLEncoder.encode(String.valueOf(ver), "UTF-8"));
		 
		// 공공데이터 요청 및 응답

		URL url = new URL(urlBuilder.toString());

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod("GET");

		conn.setRequestProperty("Content-type", "application/json");

		BufferedReader rd;

		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {

			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

		} else {

			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));

		}

		StringBuilder sb = new StringBuilder();

		String line;

		while ((line = rd.readLine()) != null) {

			sb.append(line);

		}

		rd.close();

		conn.disconnect();

		log.debug(sb.toString());

		return "air";

	}
}
