package de.wigenso.springboot;

import de.wigenso.springboot.jsonrpc.RemoteProcedure;
import de.wigenso.springboot.jsonrpc.JsonRpcController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RequestMapping(value = MyJsonRpcMethodSecurityController.API)
@RestController
public class MyJsonRpcMethodSecurityController extends JsonRpcController {

    static final String API = "/jsonrpc/secureapi";

    @Autowired
    private HttpServletRequest httpServletRequest;

    @RemoteProcedure
    @PreAuthorize("hasRole('SPECIAL_ALICE')")
    public String onlyForAlice() {
        return "hello secret for alice";
    }

    @RemoteProcedure
    @PreAuthorize("hasRole('SPECIAL_BOB')")
    public String onlyForBob() {
        return "hello secret for bob";
    }


    @RemoteProcedure
    public String helloPrincipal(String say, Principal principal, String mark) {
        return say + " " + principal.getName() + mark;
    }

}
