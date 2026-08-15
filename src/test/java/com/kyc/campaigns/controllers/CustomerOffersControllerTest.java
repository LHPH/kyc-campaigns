package com.kyc.campaigns.controllers;

import com.kyc.campaigns.delegate.CustomerOffersDelegate;
import com.kyc.campaigns.model.OfferData;
import com.kyc.core.model.web.RequestData;
import com.kyc.core.model.web.ResponseData;
import com.kyc.core.util.TestsUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.json.JsonMapper;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CustomerOffersController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class CustomerOffersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CustomerOffersDelegate delegate;

    JacksonTester<Object> jacksonTester;

    @BeforeEach
    public void setUp(){

        JsonMapper jsonMapper = new JsonMapper();
        JacksonTester.initFields(this,jsonMapper);
    }


    @Test
    public void getOffersByCustomer_passRequestToDelegate_returnSuccessfulResponse() throws Exception{

        List<OfferData> list = new ArrayList<>();
        ResponseEntity<ResponseData<List<OfferData>>> response = TestsUtil.getResponseTest(list);

        when(delegate.getOffersByCustomer(any(RequestData.class))).thenReturn(response);

        mockMvc.perform(get("/customer/{customerId}/offers",1)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getOfferByCustomer_passRequestToDelegate_returnSuccessfulResponse() throws Exception{

        ResponseEntity<ResponseData<OfferData>> response = TestsUtil.getResponseTest(new OfferData());

        when(delegate.getOfferByCustomer(any(RequestData.class))).thenReturn(response);

        mockMvc.perform(get("/customer/{customerId}/offers/{offerId}",1,1)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
