package com.kyc.campaigns.config;

import com.kyc.core.config.BuildDetailConfig;
import com.kyc.core.exception.handlers.KycGenericRestExceptionHandler;
import com.kyc.core.exception.handlers.KycUnhandledExceptionHandler;
import com.kyc.core.properties.KycMessages;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value = {KycMessages.class, KycGenericRestExceptionHandler.class})
//BuildDetailConfig
public class GeneralConfig {

    @Bean
    public KycUnhandledExceptionHandler kycUnhandledExceptionHandler(KycMessages messages){
        return new KycUnhandledExceptionHandler(messages.getMessage("001"));
    }
}
