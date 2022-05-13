package com.kyc.campaigns.config;

import com.kyc.core.exception.handlers.KycGenericRestExceptionHandler;
import com.kyc.core.exception.handlers.KycUnhandledExceptionHandler;
import com.kyc.core.exception.handlers.KycValidationRestExceptionHandler;
import com.kyc.core.properties.KycMessages;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static com.kyc.campaigns.constants.AppConstants.MSG_CODE_000;
import static com.kyc.campaigns.constants.AppConstants.MSG_CODE_001;

@Configuration
@Import(value = {KycMessages.class, KycGenericRestExceptionHandler.class})
//BuildDetailConfig
public class GeneralConfig {

    @Bean
    public KycUnhandledExceptionHandler kycUnhandledExceptionHandler(KycMessages messages){
        return new KycUnhandledExceptionHandler(messages.getMessage(MSG_CODE_000));
    }

    @Bean
    public KycValidationRestExceptionHandler kycValidationRestExceptionHandler(KycMessages messages){
        return new KycValidationRestExceptionHandler(messages.getMessage(MSG_CODE_001));
    }
}
