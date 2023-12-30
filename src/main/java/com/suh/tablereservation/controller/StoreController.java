package com.suh.tablereservation.controller;

import com.suh.tablereservation.config.JwtAuthenticationProvider;
import com.suh.tablereservation.domain.common.UserDto;
import com.suh.tablereservation.domain.common.UserType;
import com.suh.tablereservation.domain.dto.StoreDto;
import com.suh.tablereservation.domain.form.StoreCreateForm;

import com.suh.tablereservation.domain.form.StoreEditForm;
import com.suh.tablereservation.domain.model.Store;
import com.suh.tablereservation.exception.CustomException;
import com.suh.tablereservation.exception.ErrorCode;
import com.suh.tablereservation.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/store")
public class StoreController {
    private final StoreService storeService;
    private final JwtAuthenticationProvider provider;

    @PostMapping("/partner/create")
    public ResponseEntity<StoreDto> createStore(
            @RequestHeader(name = "X-AUTH-TOKEN") String token,
            @RequestBody StoreCreateForm storeCreateForm
    ){
        UserDto userDto = provider.getUserDto(token);
        if (userDto.getUserType() != UserType.PARTNER){
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
        }
        Store store = storeService.createStore(userDto.getId(), storeCreateForm);
        return ResponseEntity.ok(StoreDto.from(store));
    }

    @GetMapping("/partner/stores-info")
    public ResponseEntity<List<StoreDto>> getStoresByPartner(
            @RequestHeader(name = "X-AUTH-TOKEN") String token
    ){
        UserDto userDto = provider.getUserDto(token);
        List<Store> stores
                = storeService.getStoresByPartnerId(userDto.getId());
        return ResponseEntity.ok(stores.stream()
                .map(StoreDto::from)
                .collect(Collectors.toList())
        );
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchStores(
        @RequestHeader(name = "X-AUTH-TOKEN") String token,
        @RequestParam String keyWord
    ){
        if(!provider.validateToken(token)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    "Invalid token");
        }

        List<Store> stores = storeService.searchStoresByName(keyWord);
        if(stores.isEmpty()){
            return ResponseEntity.ok("검색 결과가 없습니다.");
        }

        List<StoreDto> storeDtos = stores.stream()
                .map(StoreDto::from)
                .toList();
        return ResponseEntity.ok(storeDtos);
    }

    @PutMapping("/partner/edit/{storeId}")
    public ResponseEntity<StoreDto> editStore(
            @RequestHeader(name = "X-AUTH-TOKEN") String token,
            @PathVariable Long storeId,
            @RequestBody StoreEditForm storeEditForm
    ){
        UserDto userDto = provider.getUserDto(token);
        Store store = storeService.editStore(storeId, storeEditForm, userDto.getId());

        return ResponseEntity.ok(StoreDto.from(store));
    }

    @DeleteMapping("/partner/delete/{storeId}")
    public ResponseEntity<Void> deleteStore(
            @RequestHeader(name = "X-AUTH-TOKEN") String token,
            @PathVariable Long storeId
    ){
        UserDto userDto = provider.getUserDto(token);
        storeService.deleteStore(userDto.getId(),storeId);
        return ResponseEntity.noContent().build();
    }
}
