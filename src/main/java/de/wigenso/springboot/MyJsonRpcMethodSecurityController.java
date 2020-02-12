package de.wigenso.springboot;

import de.wigenso.springboot.jsonrpc.JsonRpc;
import de.wigenso.springboot.jsonrpc.JsonRpcController;
import de.wigenso.springboot.jsonrpc.RpcController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RequestMapping(value = MyJsonRpcMethodSecurityController.API) // TODO: add to RpcController annotation
@RpcController
public class MyJsonRpcMethodSecurityController extends JsonRpcController {

    static final String API = "/jsonrpc/secureapi";

    @Autowired
    private HttpServletRequest httpServletRequest;

    @JsonRpc
    @PreAuthorize("hasRole('SPECIAL_ALICE')")
    public String onlyForAlice() {
        return "hello secret for alice";
    }

    @JsonRpc
    @PreAuthorize("hasRole('SPECIAL_BOB')")
    public String onlyForBob() {
        return "hello secret for bob";
    }


    @JsonRpc
    public String helloPrincipal(String say, Principal principal, String mark) {
        return say + " " + principal.getName() + mark;
    }

}
