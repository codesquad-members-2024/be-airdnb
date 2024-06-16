package team07.airbnb.integration.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Request {

    @Autowired
    private ObjectMapper customObjectMapper;

    private String jwtToken;

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public ExtractableResponse<Response> get(String url) {
        return RestAssured.given().log().all()
                .auth().oauth2(jwtToken)
                .when()
                .get(url)
                .then().log().all()
                .extract();
    }

    public <T> T post(Object params, String url, Class<T> responseType) throws JsonProcessingException {
        return RestAssured.given().log().all()
                .auth().oauth2(jwtToken)
                .body(customObjectMapper.writeValueAsString(params))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post(url)
                .then().log().all()
                .extract()
                .as(responseType);
    }

    public ExtractableResponse<Response> put(Object params, String url) throws JsonProcessingException {
        return RestAssured.given().log().all()
                .auth().oauth2(jwtToken)
                .body(customObjectMapper.writeValueAsString(params))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .put(url)
                .then().log().all()
                .extract();
    }

    public ExtractableResponse<Response> patch(Object params, String url) throws JsonProcessingException {
        return RestAssured.given().log().all()
                .auth().oauth2(jwtToken)
                .body(customObjectMapper.writeValueAsString(params))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .patch(url)
                .then()
                .log().all()
                .extract();
    }

    public ExtractableResponse<Response> delete(String url) {
        return RestAssured.given().log().all()
                .auth().oauth2(jwtToken)
                .when()
                .delete(url)
                .then().log().all()
                .extract();
    }
}
