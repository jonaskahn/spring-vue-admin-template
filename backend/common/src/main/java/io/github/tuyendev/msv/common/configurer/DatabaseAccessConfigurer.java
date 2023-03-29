package io.github.tuyendev.msv.common.configurer;

import java.util.Optional;

import io.github.tuyendev.msv.common.utils.ContextHelper;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EntityScan(value = {"io.github.tuyendev.msv.common.entity"})
@EnableJpaRepositories(value = {"io.github.tuyendev.msv.common.repository"})
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@EnableMongoRepositories(value = {"io.github.tuyendev.msv.common.repository"})
@EnableMongoAuditing(auditorAwareRef = "auditorProvider")
class DatabaseAccessConfigurer {
	@Bean
	public AuditorAware<Long> auditorProvider() {
		return new DomainAuditorAware();
	}

	static class DomainAuditorAware implements AuditorAware<Long> {
		@Override
		public Optional<Long> getCurrentAuditor() {
			return Optional.of(ContextHelper.getCurrentLoginUserId());
		}
	}

}
