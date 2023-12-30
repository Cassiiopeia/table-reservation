package com.suh.tablereservation.service;

import com.suh.tablereservation.domain.common.ReservationStatus;
import com.suh.tablereservation.domain.form.ReservationForm;
import com.suh.tablereservation.domain.model.Customer;
import com.suh.tablereservation.domain.model.Reservation;
import com.suh.tablereservation.domain.model.Store;
import com.suh.tablereservation.domain.repository.CustomerRepository;
import com.suh.tablereservation.domain.repository.ReservationRepository;
import com.suh.tablereservation.domain.repository.StoreRepository;
import com.suh.tablereservation.exception.CustomException;
import com.suh.tablereservation.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final CustomerRepository customerRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public Reservation createReservation(Long customerId, ReservationForm form) {
        Store store = storeRepository.findByName(form.getStoreName())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_STORE));

        // 운영시간 확인 , 예약가능매장 여부확인, 예약인원확인
        verifyCreateReservation(form,store);

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        Reservation newReservation = Reservation.builder()
                .reservationTime(form.getReservationTime())
                .status(ReservationStatus.REQUEST)
                .reservationPhone(form.getReservationPhone())
                .reservationName(form.getReservationName())
                .numberOfPerson(form.getNumberOfPerson())
                .customer(customer)
                .store(store)
                .build();

        return reservationRepository.save(newReservation);
    }

    private void verifyCreateReservation(ReservationForm form, Store store){

        if (!store.getIsAvaliableReservation()) {
            throw new CustomException(ErrorCode.NOT_AVAILABLE_RESERVATION_STORE);
        }

        if (form.getNumberOfPerson() > store.getMaxReservationPeople()) {
            throw new CustomException(ErrorCode.TOO_MANY_RESERVATION_PEOPLE);
        }

        if (!isValidOperationTime(form.getReservationTime(), store)) {
            throw new CustomException(ErrorCode.INVALID_STORE_OPERATION_TIME);
        }
    }

    private Boolean isValidOperationTime(LocalDateTime localDateTime, Store store) {
        LocalTime reservationTime = localDateTime.toLocalTime();

        return reservationTime.isBefore(store.getClosingTime()) &&
                reservationTime.isAfter(store.getOpeningTime());
    }
}
