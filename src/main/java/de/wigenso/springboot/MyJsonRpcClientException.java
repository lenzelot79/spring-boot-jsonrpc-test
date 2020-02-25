package de.wigenso.springboot;

import com.fasterxml.jackson.databind.JsonNode;
import de.wigenso.springboot.jsonrpc.JsonRpcClientException;

public class MyJsonRpcClientException extends JsonRpcClientException {

    public MyJsonRpcClientException(JsonNode error) {
        super(error);
    }

}
