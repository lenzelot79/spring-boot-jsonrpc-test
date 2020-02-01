package de.wigenso.springboot;

import de.wigenso.springboot.jsonrpc.JsonRpc;
import de.wigenso.springboot.jsonrpc.JsonRpcController;
import de.wigenso.springboot.jsonrpc.RpcController;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = MyJsonRpcController.API)
@RpcController
public class MyJsonRpcController implements JsonRpcController {

    static final String API = "/jsonrpc/api";

    @JsonRpc
    public void myMethodA() {
    }

    @JsonRpc
    public String myMethodB() {
        return "Hello World";
    }

    @JsonRpc
    public void myMethodC() {
        throw new RuntimeException("Hello Error");
    }

    @JsonRpc
    public String myMethodD(final String str1, final int int1) {
        return str1 + " " + int1;
    }

    @JsonRpc
    public TestParam myMethodE(final TestParam testParam) {
        final TestParam r = new TestParam();
        r.setStr1(testParam.getStr1() + "+");
        r.setInt1(testParam.getInt1() + 1);
        return r;
    }

}
