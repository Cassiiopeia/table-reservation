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
import java.util.List;
import java.util.UUID;

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
        verifyCreateReservation(form, store);

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        String createdCode = UUID.randomUUID().toString().replace("-","");

        Reservation newReservation = Reservation.builder()
                .reservationTime(form.getReservationTime())
                .status(ReservationStatus.REQUEST)
                .reservationPhone(form.getReservationPhone())
                .reservationName(form.getReservationName())
                .numberOfPerson(form.getNumberOfPerson())
                .reservationCode(createdCode)
                .customer(customer)
                .store(store)
                .build();

        return reservationRepository.save(newReservation);
    }

    private void verifyCreateReservation(ReservationForm form, Store store) {

        if (!store.getIsAvaliableReservation()) {
            throw new CustomException(ErrorCode.NOT_AVAILABLE_RESERVATION_STORE);
        }
        if (!(form.getNumberOfPerson() >= 1)) {
            throw new CustomException(ErrorCode.TOO_LOW_RESERVATION_PEOPLE);
        }

        if (form.getNumberOfPerson() > store.getMaxReservationPeople()) {
            throw new CustomException(ErrorCode.TOO_MANY_RESERVATION_PEOPLE);
        }

        if (!isValidOperationTime(form.getReservationTime(), store)) {
            throw new CustomException(ErrorCode.INVALID_STORE_OPERATION_TIME);
        }
    }


    @Transactional(readOnly = true)
    public List<Reservation> getReservationsByCustomer(Long customerId) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        return reservationRepository.findAllByCustomerId(customerId);

    }

    @Transactional(readOnly = true)
    public List<Reservation> getReservationsByStore(Long partnerId, Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_STORE));

        if (!store.getPartner().getId().equals(partnerId)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
        }

        return reservationRepository.findAllByStoreId(storeId);
    }

    @Transactional
    public Reservation confirmVisit(String reservationCode) {

        Reservation reservation = reservationRepository.findByReservationCode(reservationCode)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_RESERVATION));

        verifyConfirmVisit(reservation);

        reservation.setStatus(ReservationStatus.VISITED);

        return reservationRepository.save(reservation);
    }

    private void verifyConfirmVisit(Reservation reservation) {
        // 방문확인 시간이 유효한지 확인 ( 예약시간 10분 이내~ 1시간이후)
        if (!isValidConfirmVisitTime(reservation.getReservationTime())) {
            throw new CustomException(ErrorCode.INVALID_VISIT_CONFIRM_TIME);
        }

        if (reservation.getStatus().equals(ReservationStatus.REQUEST)) {
            throw new CustomException(ErrorCode.NOT_CONFIRMED_RESERVATION_STATUS);
        }

        if (reservation.getStatus().equals(ReservationStatus.VISITED)) {
            throw new CustomException(ErrorCode.ALREADY_VISITED_RESERVATION_STATUS);
        }
    }

    @Transactional
    public Reservation confirmReservation(Long partnerId, Long reservationId) {

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_RESERVATION));

        if (!reservation.getStore().getPartner().getId().equals(partnerId)) {
            throw new CustomException(ErrorCode.INVALID_USER);
        }

        if (!reservation.getStatus().equals(ReservationStatus.REQUEST)) {
            throw new CustomException(ErrorCode.NOT_REQUEST_RESERVATION_STATUS);
        }

        reservation.setStatus(ReservationStatus.CONFIRMED);
        return reservationRepository.save(reservation);
    }


    private Boolean isValidOperationTime(LocalDateTime localDateTime, Store store) {
        LocalTime reservationTime = localDateTime.toLocalTime();

        return reservationTime.isBefore(store.getClosingTime()) &&
                reservationTime.isAfter(store.getOpeningTime()) ||
                reservationTime.equals(store.getClosingTime()) ||
                reservationTime.equals(store.getOpeningTime());
    }

    private Boolean isValidConfirmVisitTime(LocalDateTime reservationTime) {
        LocalDateTime curTime = LocalDateTime.now();
        return curTime.isAfter(reservationTime.minusMinutes(10)) &&
                curTime.isBefore(reservationTime.plusMinutes(60)) ||
                curTime.equals(reservationTime.minusMinutes(10)) ||
                curTime.equals(reservationTime.plusMinutes(60));
    }

}
