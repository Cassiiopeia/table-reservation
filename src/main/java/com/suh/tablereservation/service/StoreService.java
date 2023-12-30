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

    public Store createStore(Long partnerId, StoreCreateForm storeCreateForm) {
        if (storeRepository.findByName(storeCreateForm.getName()).isPresent()) {
            throw new CustomException(ErrorCode.ALREADY_EXIST_STORE);
        }

        Partner partner = partnerRepository.findById(partnerId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        Store newStore = Store.builder()
                .name(storeCreateForm.getName())
                .location(storeCreateForm.getLocation())
                .description(storeCreateForm.getDescription())
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

    public Store editStore(Long storeId, StoreEditForm storeEditForm, Long partnerId) {

        // verify PartnerStoreExist
        Store targetStore = storeRepository.findByIdAndPartnerId(storeId, partnerId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_STORE));

        if( !storeEditForm.getName().equals(targetStore.getName()) &&
                storeRepository.findByName(storeEditForm.getName()).isPresent()){
            throw new CustomException(ErrorCode.ALREADY_EXIST_STORE);
        }

        targetStore.setName(storeEditForm.getName());
        targetStore.setLocation(storeEditForm.getLocation());
        targetStore.setDescription(storeEditForm.getDescription());

        return storeRepository.save(targetStore);
    }

    @Transactional
    public void deleteStore(Long partnerId, Long storeId) {
        // verify PartnerStoreExist

        storeRepository.findByIdAndPartnerId(storeId, partnerId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_STORE));

        storeRepository.deleteById(storeId);
    }
}
