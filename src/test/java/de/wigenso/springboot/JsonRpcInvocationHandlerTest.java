package de.wigenso.springboot;

import de.wigenso.springboot.jsonrpc.JsonRpcClient;
import de.wigenso.springboot.jsonrpc.JsonRpcInvocationHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Proxy;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JsonRpcInvocationHandlerTest {

    @LocalServerPort
    private int port;

    private RestTemplate restTemplate = new RestTemplate();

    @Test
    public void simpleItCase() {

        final String apiUrl = "http://localhost:" + port + MyJsonRpcControllerClient.class.getDeclaredAnnotation(JsonRpcClient.class).value();
        final MyJsonRpcControllerClient client = (MyJsonRpcControllerClient) Proxy.newProxyInstance(
                MyJsonRpcControllerClient.class.getClassLoader(),
                new Class[]{ MyJsonRpcControllerClient.class },
                new JsonRpcInvocationHandler(restTemplate, apiUrl));



        final String res = client.myMethodD("Alice", 7);

    }


}
