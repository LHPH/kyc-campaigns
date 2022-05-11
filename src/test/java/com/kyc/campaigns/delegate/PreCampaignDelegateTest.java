package com.kyc.campaigns.delegate;

import com.kyc.campaigns.model.ResultPreLoadOffers;
import com.kyc.campaigns.services.PreCampaignService;
import com.kyc.core.model.web.RequestData;
import com.kyc.core.model.web.ResponseData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PreCampaignDelegateTest {

    @InjectMocks
    private PreCampaignDelegate delegate;

    @Mock
    private PreCampaignService service;

    @BeforeAll
    public static void init(){
        MockitoAnnotations.openMocks(PreCampaignDelegateTest.class);
    }

    @Test
    public void uploadFileOfPreOffers_passRequest_processResponse(){

        when(service.uploadFile(any(RequestData.class))).thenReturn(ResponseData.of(new ResultPreLoadOffers()));

        delegate.uploadFileOfPreOffers(RequestData.<MultipartFile>builder().build());

        verify(service,times(1)).uploadFile(any(RequestData.class));
    }

    @Test
    public void cleanness_executeService_processResponse(){

        when(service.cleanPreOffers()).thenReturn(ResponseData.of(true));

        delegate.cleanness();

        verify(service,times(1)).cleanPreOffers();
    }
}
