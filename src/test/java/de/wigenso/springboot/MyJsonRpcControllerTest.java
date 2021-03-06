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
@WebMvcTest(MyJsonRpcController.class)
@WithMockUser(value = "bob")
class MyJsonRpcControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testVoidParamAndVoidReturn() throws Exception {

        mockMvc.perform(post(MyJsonRpcController.API)
                .content(getResourceAsString("/json/voidParamAndVoidReturn.json"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void testVoidParamAndStringReturn() throws Exception {

        MvcResult result = mockMvc.perform(post(MyJsonRpcController.API)
                .content(getResourceAsString("/json/voidParamAndStringReturn.json"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        JsonRpcResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), JsonRpcResponse.class);
        assertThat(objectMapper.treeToValue(response.getResult(), String.class)).isEqualTo("Hello World");
    }

    @Test
    void testThrowsRuntimeExceptions() throws Exception {

        final MvcResult result = mockMvc.perform(post(MyJsonRpcController.API)
                .content(getResourceAsString("/json/throwsRuntimeExceptions.json"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        final JsonRpcResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), JsonRpcResponse.class);
        final RuntimeException error = objectMapper.treeToValue(response.getError(), RuntimeException.class);
        assertThat(error.getLocalizedMessage()).isEqualTo("Hello Error");
    }

    @Test
    void twoParamsAndStringReturn_namedParams() throws Exception {

        final MvcResult result = mockMvc.perform(post(MyJsonRpcController.API)
                .content(getResourceAsString("/json/twoParamsAndStringReturn_namedParams.json"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        JsonRpcResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), JsonRpcResponse.class);
        assertThat(objectMapper.treeToValue(response.getResult(), String.class)).isEqualTo("Bob 42");
    }

    @Test
    void twoParamsAndStringReturn_listParams() throws Exception {

        final MvcResult result = mockMvc.perform(post(MyJsonRpcController.API)
                .content(getResourceAsString("/json/twoParamsAndStringReturn_listParams.json"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        JsonRpcResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), JsonRpcResponse.class);
        assertThat(objectMapper.treeToValue(response.getResult(), String.class)).isEqualTo("Bob 42");
        assertThat(response.getError()).isNull();
    }

    @Test
    void testComplexParamAndReturn() throws Exception {

        final MvcResult result = mockMvc.perform(post(MyJsonRpcController.API)
                .content(getResourceAsString("/json/complexParamAndReturn.json"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        final JsonRpcResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), JsonRpcResponse.class);
        final TestParam testParam = objectMapper.treeToValue(response.getResult(), TestParam.class);
        assertThat(testParam.getStr1()).isEqualTo("Bob+");
        assertThat(testParam.getInt1()).isEqualTo(43);
    }

    private String getResourceAsString(final String name) throws IOException {
        return new String(this.getClass().getResourceAsStream(name).readAllBytes());
    }

}
