package de.wigenso.springboot;

import de.wigenso.springboot.jsonrpc.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

@Service
public class JsonRpcClientRetryInterceptor implements JsonRpcClientInterceptor {

    @Retryable(value = JsonRpcClientException.class, maxAttempts = 4)
    public ResponseEntity<JsonRpcResponse> execute(final HttpEntity<JsonRpcRequest> entity, final JsonRpcClientTarget target) {
        return target.execute(entity);
    }
}
