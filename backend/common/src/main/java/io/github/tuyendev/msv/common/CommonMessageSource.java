package io.github.tuyendev.msv.common;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ResourceBundleMessageSource;

public class CommonMessageSource extends ResourceBundleMessageSource {

    public CommonMessageSource() {
        setBasename("io.github.tuyendev.msv.common.messages");
        setDefaultEncoding("UTF-8");
    }

    public static MessageSourceAccessor getAccessor() {
        return new MessageSourceAccessor(new CommonMessageSource());
    }
}
