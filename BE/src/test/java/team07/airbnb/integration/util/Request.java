package team07.airbnb.integration.util;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class Request {

    @Value("${jwt.host}")
    private String HOST_TOKEN;

    public ExtractableResponse<Response> get(String url) {
        return RestAssured.given().log().all()
                .auth().oauth2(HOST_TOKEN)
                .when()
                .get(url)
                .then().log().all()
                .extract();
    }

    public ExtractableResponse<Response> post(Object params, String url) {
        return RestAssured.given().log().all()
                .body(params)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post(url)
                .then().log().all()
                .extract();
    }

    public ExtractableResponse<Response> put(Object params, String url) {
        return RestAssured.given().log().all()
                .body(params)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .put(url)
                .then().log().all()
                .extract();
    }

    public ExtractableResponse<Response> patch(Object params, String url){
        return RestAssured.given().log().all()
            .body(params)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .patch(url)
            .then()
            .log().all()
            .extract();
    }

    public ExtractableResponse<Response> delete(String url) {
        return RestAssured.given().log().all()
                .when()
                .delete(url)
                .then().log().all()
                .extract();
    }
}
