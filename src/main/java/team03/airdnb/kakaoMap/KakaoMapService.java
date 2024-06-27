package team03.airdnb.kakaoMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import team03.airdnb.kakaoMap.dto.CoordinatesDto;
import team03.airdnb.kakaoMap.dto.request.AddressDto;

@Service
@RequiredArgsConstructor
public class KakaoMapService {

    @Value("${kakao.api.key}")
    private String apiKey;

    @Value("${kakao.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public boolean isAddressValid(String address) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + apiKey);

        String url = apiUrl + "?query=" + address;

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        try {
            AddressDto addressDto = objectMapper.readValue(response.getBody(), AddressDto.class);
            AddressDto.Document[] documents = addressDto.getDocuments();
            return documents.length > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public CoordinatesDto getCoordinates(String address) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + apiKey);

        String url = apiUrl + "?query=" + address;

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        try {
            AddressDto addressDto = objectMapper.readValue(response.getBody(), AddressDto.class);
            AddressDto.Document[] documents = addressDto.getDocuments();
            return new CoordinatesDto(Double.parseDouble(documents[0].getY()), Double.parseDouble(documents[0].getX()));
        } catch (Exception e) {
            return null;
        }
    }
}
