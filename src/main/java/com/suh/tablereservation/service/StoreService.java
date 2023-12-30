package com.suh.tablereservation.service;

import com.suh.tablereservation.domain.form.StoreCreateForm;
import com.suh.tablereservation.domain.form.StoreEditForm;
import com.suh.tablereservation.domain.model.Partner;
import com.suh.tablereservation.domain.model.Store;

import com.suh.tablereservation.domain.repository.PartnerRepository;
import com.suh.tablereservation.domain.repository.StoreRepository;
import com.suh.tablereservation.exception.CustomException;
import com.suh.tablereservation.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final PartnerRepository partnerRepository;

    @Transactional
    public Store createStore(Long partnerId, StoreCreateForm form) {

        verifyCreateStore(form);

        Partner partner = partnerRepository.findById(partnerId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        Store newStore = Store.builder()
                .name(form.getName())
                .location(form.getLocation())
                .description(form.getDescription())
                .openingTime(form.getOpeningTime())
                .closingTime(form.getClosingTime())
                .isAvaliableReservation(form.getIsAvaliableReservation())
                .maxReservationPeople(form.getMaxReservationPeople())
                .partner(partner)
                .build();
        return storeRepository.save(newStore);
    }

    public List<Store> getStoresByPartnerId(Long partnerId) {
        return storeRepository.findByPartnerId(partnerId);
    }

    public List<Store> searchStoresByName(String storeName){
        return storeRepository.findByNameContainingIgnoreCase(storeName);
    }

    @Transactional
    public Store editStore(Long storeId, StoreEditForm form, Long partnerId) {
        Store targetStore = storeRepository.findByIdAndPartnerId(storeId, partnerId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_STORE));

        if (!form.getName().equals(targetStore.getName()) &&
                storeRepository.findByName(form.getName()).isPresent()) {
            throw new CustomException(ErrorCode.ALREADY_EXIST_STORE);
        }

        if(form.getIsAvaliableReservation()){
            if(!(form.getMaxReservationPeople() >=1)){
                throw new CustomException(ErrorCode.INVALID_MAX_RESERVATION_PEOPLE_COUNT);
            }
        }

        targetStore.setName(form.getName());
        targetStore.setLocation(form.getLocation());
        targetStore.setDescription(form.getDescription());
        targetStore.setOpeningTime(form.getOpeningTime());
        targetStore.setClosingTime(form.getClosingTime());
        targetStore.setIsAvaliableReservation(form.getIsAvaliableReservation());
        targetStore.setMaxReservationPeople(form.getMaxReservationPeople());

        return storeRepository.save(targetStore);
    }

    @Transactional
    public void deleteStore(Long partnerId, Long storeId) {
        // verify PartnerStoreExist

        storeRepository.findByIdAndPartnerId(storeId, partnerId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_STORE));

        storeRepository.deleteById(storeId);
    }

    private void verifyCreateStore(StoreCreateForm form){
        if (storeRepository.findByName(form.getName()).isPresent()) {
            throw new CustomException(ErrorCode.ALREADY_EXIST_STORE);
        }

        if(form.getIsAvaliableReservation()){
            if(!(form.getMaxReservationPeople() >=1)){
                throw new CustomException(ErrorCode.INVALID_MAX_RESERVATION_PEOPLE_COUNT);
            }
        }
    }
}
