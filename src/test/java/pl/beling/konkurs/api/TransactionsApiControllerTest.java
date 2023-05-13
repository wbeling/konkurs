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
class TransactionsApiControllerTest extends BaseControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Override
    TestRestTemplate getRestTemplate() {
        return restTemplate;
    }

    @Test
    void reportByExample() throws IOException {
        String inputFile = new String(Files.readAllBytes(Paths.get("src/test/resources/transactions/example_request.json")));
        String outputFile = new String(Files.readAllBytes(Paths.get("src/test/resources/transactions/example_response.json")));
        report(inputFile, outputFile);
    }


    private void report(String inputFile, String outputFile) {
        String endPointUrl = "http://localhost:" + port + "/transactions/report";
        assertThat(
                getPostResponse(endPointUrl, inputFile)
        ).isEqualToIgnoringWhitespace(outputFile);
    }
}