package de.wigenso.springboot;

import com.fasterxml.jackson.databind.JsonNode;
import de.wigenso.springboot.jsonrpc.JsonRpcClientErrorHandler;

public class MyJsonRpcClientErrorHandler implements JsonRpcClientErrorHandler {
    @Override
    public void handleError(JsonNode errorNode) {
        throw new MyJsonRpcClientException(errorNode);
    }
}
