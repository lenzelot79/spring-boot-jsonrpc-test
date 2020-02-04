package de.wigenso.springboot;

import de.wigenso.springboot.jsonrpc.JsonRpcClient;
import org.springframework.web.bind.annotation.RequestHeader;

@JsonRpcClient("/jsonrpc/api")
public interface MyJsonRpcControllerClient {

    void voidParamAndVoidReturn();

    String voidParamAndStringReturn();

    void throwsRuntimeExceptions();

    String twoParamsAndStringReturn(final String str1, final int int1);

    TestParam complexParamAndReturn(final TestParam testParam);

    String echoHeader(@RequestHeader("x-test") final String value);

    String combineHeaderAndParam(@RequestHeader("x-test") final String value1, final String value2);

}
