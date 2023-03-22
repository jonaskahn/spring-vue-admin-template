package io.github.tuyendev.msv.common.exception;

import io.github.tuyendev.msv.common.CommonMessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserNotExistedException extends UsernameNotFoundException {

    private static final MessageSourceAccessor messageSource = CommonMessageSource.getAccessor();

    public UserNotExistedException() {
        super(messageSource.getMessage("app.user.exception.not-found"));
    }
}
