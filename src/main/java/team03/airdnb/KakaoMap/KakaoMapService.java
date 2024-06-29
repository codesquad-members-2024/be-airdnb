package team03.airdnb.KakaoMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import team03.airdnb.KakaoMap.dto.CoordinatesDto;
import team03.airdnb.KakaoMap.dto.request.AddressDto;

@Service
@RequiredArgsConstructor
public class KakaoMapService {

    private static final String AUTHORIZATION = "Authorization";
    private static final String QUERY = "query";
    private static final String KAKAO_AUTHORIZATION_START = "KakaoAK ";

    @Value("${kakao.api.key}")
    private String apiKey;

    @Value("${kakao.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public boolean isAddressValid(String address) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTHORIZATION, KAKAO_AUTHORIZATION_START + apiKey);

        String url = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam(QUERY, address)
                .build()
                .toUriString();

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
        headers.set(AUTHORIZATION, KAKAO_AUTHORIZATION_START + apiKey);

        String url = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam(QUERY, address)
                .build()
                .toUriString();

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
