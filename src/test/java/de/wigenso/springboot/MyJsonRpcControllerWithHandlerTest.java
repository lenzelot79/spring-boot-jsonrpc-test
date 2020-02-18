package de.wigenso.springboot;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.wigenso.springboot.jsonrpc.JsonRpcResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MyJsonRpcControllerWithHandler.class)
@WithMockUser(value = "bob")
class MyJsonRpcControllerWithHandlerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testRpcFromHandler() throws Exception {

        final MvcResult result = mockMvc.perform(post("/testapi/rpc")
                .content(getResourceAsString("/json/rpcEcho.json"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        final JsonRpcResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), JsonRpcResponse.class);
        final String text = objectMapper.treeToValue(response.getResult(), String.class);
        assertThat(text).isEqualTo("Hello Bob from RPC");

    }

    private String getResourceAsString(final String name) throws IOException {
        return new String(this.getClass().getResourceAsStream(name).readAllBytes());
    }

}
