package de.wigenso.springboot;

import de.wigenso.springboot.jsonrpc.JsonRpcClientBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.UndeclaredThrowableException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class JsonRpcInvocationHandlerTest {

    private RestTemplate restTemplate = new RestTemplate();

    @LocalServerPort
    private int port;

    // can be added as @Bean to a configuration
    MyJsonRpcControllerClient client() {
        return JsonRpcClientBuilder.of(MyJsonRpcControllerClient.class)
                .withRestTemplate(restTemplate)
                .withBaseUrl("http://localhost:" + port)
                .build();
    }

    @Test
    void simpleItCase() {

        final MyJsonRpcControllerClient client = client();

        client.myMethodA();

        final String resB = client.myMethodB();
        assertThat(resB).isEqualTo("Hello World");

        final Exception e = assertThrows(Exception.class, client::myMethodC);
        assertThat(((UndeclaredThrowableException) e).getUndeclaredThrowable().getMessage()).isEqualTo("Hello Error");

        final String resD = client.myMethodD("Alice", 7);
        assertThat(resD).isEqualTo("Alice 7");

        final TestParam testParam = new TestParam();
        testParam.setInt1(1);
        testParam.setStr1("+");
        final TestParam resE = client.myMethodE(testParam);
        assertThat(resE.getInt1()).isEqualTo(2);
        assertThat(resE.getStr1()).isEqualTo("++");
    }

    @Test
    void headersItCase() {

        final MyJsonRpcControllerClient client = client();

        String result = client.echoHeader("hello");
        assertThat(result).isEqualTo("hello");

    }


}
