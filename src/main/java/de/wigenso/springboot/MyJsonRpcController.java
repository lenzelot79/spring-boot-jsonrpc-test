package de.wigenso.springboot;

import de.wigenso.springboot.jsonrpc.JsonRpc;
import de.wigenso.springboot.jsonrpc.JsonRpcController;
import de.wigenso.springboot.jsonrpc.RpcController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@RequestMapping(value = MyJsonRpcController.API)
@RpcController
public class MyJsonRpcController implements JsonRpcController {

    static final String API = "/jsonrpc/api";

    @Autowired
    private HttpServletRequest httpServletRequest;

    @JsonRpc
    public void voidParamAndVoidReturn() {
    }

    @JsonRpc
    public String voidParamAndStringReturn() {
        return "Hello World";
    }

    @JsonRpc
    public void throwsRuntimeExceptions() {
        throw new RuntimeException("Hello Error");
    }

    @JsonRpc
    public String twoParamsAndStringReturn(final String str1, final int int1) {
        return str1 + " " + int1;
    }

    @JsonRpc
    public TestParam complexParamAndReturn(final TestParam testParam) {
        final TestParam r = new TestParam();
        r.setStr1(testParam.getStr1() + "+");
        r.setInt1(testParam.getInt1() + 1);
        return r;
    }

    @JsonRpc
    public String echoHeader() {
        return httpServletRequest.getHeader("x-test");
    }

    @JsonRpc
    public String combineHeaderAndParam(final String value2) {
        return httpServletRequest.getHeader("x-test") + value2;
    }

}
