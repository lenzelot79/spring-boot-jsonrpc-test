package de.wigenso.springboot;

import de.wigenso.springboot.jsonrpc.JsonRpcClientBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class JsonRpcRetryTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JsonRpcClientRetryInterceptor retryInterceptor;

    @LocalServerPort
    private int port;

    // can be added as @Bean to a configuration
    MyJsonRpcControllerClient client() {
        return JsonRpcClientBuilder.of(MyJsonRpcControllerClient.class)
                .withRestTemplate(restTemplate
                        .withBasicAuth("bob", "password").getRestTemplate())
                .withBaseUrl("http://localhost:" + port)
                .withErrorHandler(new MyJsonRpcClientErrorHandler())
                .withInterceptor(retryInterceptor)
                .build();
    }

    @Test
    void testWithRetry() {

        final MyJsonRpcControllerClient client = client();

        client.throwsRuntimeExceptions3Times();
    }


}
