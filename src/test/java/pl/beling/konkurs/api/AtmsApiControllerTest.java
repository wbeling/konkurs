package pl.beling.konkurs.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AtmsApiControllerTest extends BaseControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Override
    TestRestTemplate getRestTemplate() {
        return this.restTemplate;
    }

    @Test
    void calculateByExample1() throws IOException {
        String inputFile = new String(Files.readAllBytes(Paths.get("src/test/resources/atmservice/example_1_request.json")));
        String outputFile = new String(Files.readAllBytes(Paths.get("src/test/resources/atmservice/example_1_response.json")));
        calculate(inputFile, outputFile);
    }

    @Test
    void calculateByExample2() throws IOException {
        String inputFile = new String(Files.readAllBytes(Paths.get("src/test/resources/atmservice/example_2_request.json")));
        String outputFile = new String(Files.readAllBytes(Paths.get("src/test/resources/atmservice/example_2_response.json")));
        calculate(inputFile, outputFile);
    }


    private void calculate(String inputFile, String outputFile) {
        String endPointUrl = "http://localhost:" + port + "/atms/calculateOrder";
        assertThat(
                getPostResponse(endPointUrl, inputFile)
        ).isEqualToIgnoringWhitespace(outputFile);
    }

}