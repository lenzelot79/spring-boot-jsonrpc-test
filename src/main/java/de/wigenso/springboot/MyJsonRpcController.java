package de.wigenso.springboot;

import de.wigenso.springboot.jsonrpc.JsonRpcController;
import de.wigenso.springboot.jsonrpc.RemoteProcedure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequestMapping(value = MyJsonRpcController.API)
@RestController
public class MyJsonRpcController extends JsonRpcController {

    static final String API = "/jsonrpc/api";

    @Autowired
    private HttpServletRequest httpServletRequest;

    @RemoteProcedure
    public void voidParamAndVoidReturn() {
    }

    @RemoteProcedure
    public String voidParamAndStringReturn() {
        return "Hello World";
    }

    @RemoteProcedure
    public void throwsRuntimeExceptions() {
        throw new RuntimeException("Hello Error");
    }

    @RemoteProcedure
    public String twoParamsAndStringReturn(final String str1, final int int1) {
        return str1 + " " + int1;
    }

    @RemoteProcedure
    public TestParam complexParamAndReturn(final TestParam testParam) {
        final TestParam r = new TestParam();
        r.setStr1(testParam.getStr1() + "+");
        r.setInt1(testParam.getInt1() + 1);
        return r;
    }

    @RemoteProcedure
    public String echoHeader() {
        return httpServletRequest.getHeader("x-test");
    }

    @RemoteProcedure
    public String combineHeaderAndParam(final String value2) {
        return httpServletRequest.getHeader("x-test") + value2;
    }

}
