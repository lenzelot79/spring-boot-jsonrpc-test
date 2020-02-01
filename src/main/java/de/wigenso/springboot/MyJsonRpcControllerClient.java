package de.wigenso.springboot;

import de.wigenso.springboot.jsonrpc.JsonRpcClient;

@JsonRpcClient("/jsonrpc/api")
public interface MyJsonRpcControllerClient {

    void myMethodA();

    String myMethodB();

    void myMethodC();

    String myMethodD(final String str1, final int int1);

    TestParam myMethodE(final TestParam testParam);

}
