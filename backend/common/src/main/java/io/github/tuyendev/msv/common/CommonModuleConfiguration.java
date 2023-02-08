package io.github.tuyendev.msv.common;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@ComponentScan
@Configuration
@PropertySource({"classpath:common00.properties"})
public class CommonModuleConfiguration {
}
