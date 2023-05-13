package pl.beling.konkurs.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import static org.springframework.http.MediaType.APPLICATION_JSON;

public abstract class BaseControllerTest {

    abstract TestRestTemplate getRestTemplate();

    String getPostResponse(String url, String postData) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        var request = new HttpEntity<>(postData, headers);
        return getRestTemplate().postForObject(
                url, request, String.class
        );
    }
}
