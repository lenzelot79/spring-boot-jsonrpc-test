package de.wigenso.springboot;

import de.wigenso.springboot.jsonrpc.JsonRpcClient;
import org.springframework.web.bind.annotation.RequestHeader;

@JsonRpcClient("/jsonrpc/api")
public interface MyJsonRpcControllerClient {

    void myMethodA();

    String myMethodB();

    void myMethodC();

    String myMethodD(final String str1, final int int1);

    TestParam myMethodE(final TestParam testParam);

    String echoHeader(@RequestHeader("x-test") final String value);

}
