package myself.custom.asset;

import myself.custom.asset.controller.TransLogController;
import myself.custom.asset.model.TransLog;
import myself.custom.asset.service.TransLogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransLogController.class)
class GlobalExceptionHandlerWebTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransLogService transLogService;

    @Test
    void illegalArgumentFromServiceReturnsBadRequest() throws Exception {
        when(transLogService.saveTransLog(any(TransLog.class)))
                .thenThrow(new IllegalArgumentException("Invalid expense category: 飲食"));

        mockMvc.perform(post("/api/trans/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "type": "支出",
                                  "category": "飲食",
                                  "name": "牛肉麵",
                                  "value": 180,
                                  "transDate": "2026-09-07T12:00:00+08:00"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid expense category: 飲食"));
    }
}
