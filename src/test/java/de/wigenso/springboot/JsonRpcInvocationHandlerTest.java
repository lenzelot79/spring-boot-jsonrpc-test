package de.wigenso.springboot;

import de.wigenso.springboot.jsonrpc.JsonRpcClientBuilder;
import de.wigenso.springboot.jsonrpc.JsonRpcClientException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.retry.annotation.Retryable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.UndeclaredThrowableException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class JsonRpcInvocationHandlerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    // can be added as @Bean to a configuration
    MyJsonRpcControllerClient client() {
        return JsonRpcClientBuilder.of(MyJsonRpcControllerClient.class)
                .withRestTemplate(restTemplate
                        .withBasicAuth("bob", "password").getRestTemplate())
                .withBaseUrl("http://localhost:" + port)
                .build();
    }

    @Test
    void simpleItCase() {

        final MyJsonRpcControllerClient client = client();

        client.voidParamAndVoidReturn();

        final String resB = client.voidParamAndStringReturn();
        assertThat(resB).isEqualTo("Hello World");

        final JsonRpcClientException e = assertThrows(JsonRpcClientException.class, client::throwsRuntimeExceptions);
        assertThat(e.getMessage()).isEqualTo("Hello Error");

        final String resD = client.twoParamsAndStringReturn("Alice", 7);
        assertThat(resD).isEqualTo("Alice 7");

        final TestParam testParam = new TestParam();
        testParam.setInt1(1);
        testParam.setStr1("+");
        final TestParam resE = client.complexParamAndReturn(testParam);
        assertThat(resE.getInt1()).isEqualTo(2);
        assertThat(resE.getStr1()).isEqualTo("++");
    }

    @Test
    void headersItCase() {

        final MyJsonRpcControllerClient client = client();

        final String result1 = client.echoHeader("hello");
        assertThat(result1).isEqualTo("hello");

        final String result2 = client.combineHeaderAndParam("hello", "+world");
        assertThat(result2).isEqualTo("hello+world");

    }

}
