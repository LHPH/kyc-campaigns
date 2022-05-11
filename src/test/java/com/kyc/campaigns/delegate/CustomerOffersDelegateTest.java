package com.kyc.campaigns.delegate;

import com.kyc.campaigns.model.OfferData;
import com.kyc.campaigns.services.CustomerOffersService;
import com.kyc.core.model.web.RequestData;
import com.kyc.core.model.web.ResponseData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerOffersDelegateTest {

    @InjectMocks
    private CustomerOffersDelegate delegate;

    @Mock
    private CustomerOffersService service;

    @BeforeAll
    public static void init(){
        MockitoAnnotations.openMocks(CustomerOffersDelegateTest.class);
    }

    @Test
    public void getOffersByCustomer_passRequest_processResponse(){

        when(service.getOffersByCustomer(any(RequestData.class))).thenReturn(ResponseData.of(new ArrayList<>()));

        delegate.getOffersByCustomer(RequestData.<Void>builder().build());

        verify(service,times(1)).getOffersByCustomer(any(RequestData.class));
    }

    @Test
    public void getOfferByCustomer_passRequest_processResponse(){

        when(service.getOfferByCustomer(any(RequestData.class))).thenReturn(ResponseData.of(new OfferData()));

        delegate.getOfferByCustomer(RequestData.<Void>builder().build());

        verify(service,times(1)).getOfferByCustomer(any(RequestData.class));
    }

}
