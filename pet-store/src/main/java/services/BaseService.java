package services;

import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class BaseService {

    protected static RequestSpecification defaultRequestSpecification(){
        return restAssured()
                .header("Content-type", "application/json");
//                .header("Authorization", "Bearer e0c05c1c23579cfe6419d50115373692cdcbe350f8259aa32afb6f887a8f22a6");
    }

    protected static RequestSpecification restAssured() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.urlEncodingEnabled = false;

        return given()
                .config(RestAssuredConfig.config()
                        .encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)));
    }
}
