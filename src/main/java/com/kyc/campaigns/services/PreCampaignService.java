package com.kyc.campaigns.services;

import com.kyc.campaigns.entity.ErrorOffersEntity;
import com.kyc.campaigns.entity.OfferTemporalEntity;
import com.kyc.campaigns.mappers.ErrorDetailMapper;
import com.kyc.campaigns.model.ErrorPreOfferDetail;
import com.kyc.campaigns.model.ResultPreLoadOffers;
import com.kyc.campaigns.repositories.ErrorOffersRepository;
import com.kyc.campaigns.repositories.OfferTemporalRepository;
import com.kyc.core.exception.KycRestException;
import com.kyc.core.model.web.MessageData;
import com.kyc.core.model.web.RequestData;
import com.kyc.core.model.web.ResponseData;
import com.kyc.core.properties.KycMessages;
import com.kyc.core.util.DateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.kyc.core.util.GeneralUtil.getValue;
import static com.kyc.core.util.GeneralUtil.toInt;

@Service
public class PreCampaignService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PreCampaignService.class);

    @Autowired
    private OfferTemporalRepository offerTemporalRepository;

    @Autowired
    private ErrorOffersRepository errorOffersRepository;

    @Autowired
    private ErrorDetailMapper errorDetailMapper;

    @Autowired
    private KycMessages kycMessages;

    @Transactional
    public ResponseData<ResultPreLoadOffers> uploadFile(RequestData<MultipartFile> req){

        MultipartFile file = req.getBody();
        String keyPreCampaign = UUID.randomUUID().toString();

        List<OfferTemporalEntity> preOffers = processExcelFile(file,keyPreCampaign);

        LOGGER.info("It was processed from excel {} offers",preOffers.size());
        ResultPreLoadOffers response = new ResultPreLoadOffers();
        response.setKeyPreCampaign(keyPreCampaign);
        if(!preOffers.isEmpty()){

            //https://stackoverflow.com/questions/70569706/hibernate-postgresql-batch-insert-no-longer-working-after-upgrade
            offerTemporalRepository.saveAll(preOffers);

            List<ErrorOffersEntity> errors = errorOffersRepository.getErrors(keyPreCampaign);
            List<ErrorPreOfferDetail> listErrors = errors
                    .stream()
                    .map(e -> errorDetailMapper.mapperToModel(e))
                    .collect(Collectors.toList());

            response.setSuccessfullyLoadedPreOffers(preOffers.size()-listErrors.size());
            response.setUnsuccessfullyLoadedPreOffers(errors.size());
            response.setErrorPreOffers(listErrors);
        }
        return ResponseData.of(response);

    }

    private List<OfferTemporalEntity> processExcelFile(MultipartFile file, String keyPreCampaign){

        try(InputStream in = file.getInputStream()){

            Workbook workbook  = new XSSFWorkbook(in);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.rowIterator();

            List<OfferTemporalEntity> preOffers = new ArrayList<>();
            while(rows.hasNext()){

                Row row = rows.next();
                int rowNum = row.getRowNum();
                if(rowNum>0){

                    OfferTemporalEntity record = new OfferTemporalEntity();
                    record.setKeyPreCampaign(keyPreCampaign);
                    record.setRecordExcel(--rowNum);

                    Cell cellCustomerNumber = row.getCell(0);
                    Double customerNumber = getValue(cellCustomerNumber,Cell::getNumericCellValue);
                    record.setCustomerNumber(toInt(customerNumber,null));

                    Cell cellCustomerEmail = row.getCell(1);
                    record.setCustomerEmail(getValue(cellCustomerEmail,Cell::getStringCellValue));

                    Cell cellOfferName = row.getCell(2);
                    record.setOfferName(getValue(cellOfferName,Cell::getStringCellValue));

                    Cell cellOfferDesc = row.getCell(3);
                    record.setOfferDescription(getValue(cellOfferDesc,Cell::getStringCellValue));

                    Cell cellPromotionalCode = row.getCell(4);
                    record.setPromotionalCode(getValue(cellPromotionalCode,Cell::getStringCellValue));

                    Cell cellDiscount = row.getCell(5);
                    Double discount = getValue(cellDiscount,Cell::getNumericCellValue);
                    record.setDiscount(toInt(discount,null));

                    Cell cellReward = row.getCell(6);
                    record.setReward(getValue(cellReward,Cell::getStringCellValue));

                    Cell cellStartDate = row.getCell(7);
                    String startDate = getValue(cellStartDate,Cell::getStringCellValue);
                    record.setStartDate(DateUtil.stringToDate(startDate,"yyyy-MM-dd"));

                    Cell cellEndDate = row.getCell(8);
                    String endDate = getValue(cellEndDate,Cell::getStringCellValue);
                    record.setEndDate(DateUtil.stringToDate(endDate,"yyyy-MM-dd"));

                    Cell cellTermAndConditions = row.getCell(9);
                    record.setTermAndConditionsLink(getValue(cellTermAndConditions,Cell::getStringCellValue));

                    preOffers.add(record);

                }

            }
           return preOffers;
        }
        catch(IOException ioex){
            MessageData messageData = kycMessages.getMessage("001");
            throw KycRestException.builderRestException()
                    .errorData(messageData)
                    .status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .exception(ioex)
                    .inputData(file)
                    .build();
        }
    }

    public void cleanPreOffers(){
        
    }
}
