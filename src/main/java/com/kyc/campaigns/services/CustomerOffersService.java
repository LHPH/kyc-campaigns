package com.kyc.campaigns.services;

import com.kyc.campaigns.model.OfferData;
import com.kyc.core.model.web.RequestData;
import com.kyc.core.model.web.ResponseData;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerOffersService {

    public ResponseData<List<OfferData>> getOffersByCustomer(RequestData<Void> req){
        return null;
    }

    public ResponseData<OfferData> getOfferByCustomer(RequestData<Void> req){
        return null;
    }
}
