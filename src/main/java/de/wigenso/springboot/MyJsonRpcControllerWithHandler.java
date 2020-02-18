package de.wigenso.springboot;

import de.wigenso.springboot.jsonrpc.*;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/testapi")
@RestController
public class MyJsonRpcControllerWithHandler extends JsonRpcHandler {

    @PostMapping("/rpc")
    @ResponseBody
    public JsonRpcResponse rpcEndpoint(@RequestBody JsonRpcRequest request) throws Throwable {
        return jsonRpcCall(request);
    }

    @RemoteProcedure
    public String rpcEcho(String name) {
        return "Hello " + name + " from RPC";
    }

    @PostMapping("/other")
    @ResponseBody
    public String otherEcho(@RequestBody String name) {
        return "Hello " + name;
    }

}
