package com.zhaopin;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * foo...Created by wgj on 2017/7/4.
 */
@ResponseStatus(value= HttpStatus.UNAUTHORIZED, reason="no valid token")
public class AuthorizationException extends RuntimeException {
}
