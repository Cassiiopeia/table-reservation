package com.suh.tablereservation.exception;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    // HttpStatus.BAD_REQUEST
    ALREADY_VERIFIED(HttpStatus.BAD_REQUEST, "이미 인증이 완료되었습니다."),
    ALREADY_REGISTER_USER(HttpStatus.BAD_REQUEST, "이미 가입된 회원입니다."),
    ALREADY_EXIST_EMAIL(HttpStatus.BAD_REQUEST, "이미 사용중인 이메일입니다."),
    ALREADY_EXIST_STORE(HttpStatus.BAD_REQUEST, "이미 사용중인 매장이름입니다."),
    NOT_FOUND_STORE(HttpStatus.BAD_REQUEST, "매장을 찾을 수 없습니다."),
    INVALID_MAX_RESERVATION_PEOPLE_COUNT(HttpStatus.BAD_REQUEST, "예약가능인원은 1명 이상이여합니다."),
    INVALID_STORE_OPERATION_TIME(HttpStatus.BAD_REQUEST, "매장 운영시간이 아닙니다"),
    NOT_AVAILABLE_RESERVATION_STORE(HttpStatus.BAD_REQUEST, "예약가능한 매장이 아닙니다."),
    TOO_MANY_RESERVATION_PEOPLE(HttpStatus.BAD_REQUEST, "예약가능 인원을 초과합니다."),
    TOO_LOW_RESERVATION_PEOPLE(HttpStatus.BAD_REQUEST, "예약 인원은 1명이여야합니다."),

    // HttpStatus.UNAUTHORIZED
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "잘못된 비밀번호입니다."),
    INVALID_USER(HttpStatus.UNAUTHORIZED, "사용자가 일치하지않습니다."),
    UNAUTHORIZED_USER(HttpStatus.UNAUTHORIZED, "사용자 권한이 없습니다."),

    NOT_FOUND_USER(HttpStatus.BAD_REQUEST, "회원을 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String detail;
}
