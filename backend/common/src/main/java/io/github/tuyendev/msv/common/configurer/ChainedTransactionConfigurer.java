package io.github.tuyendev.msv.common.configurer;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class ChainedTransactionConfigurer {

	@Bean
	@Primary
	public PlatformTransactionManager transactionManager(
			ObjectProvider<TransactionManagerCustomizers> transactionManagerCustomizers) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManagerCustomizers.ifAvailable(customizers -> customizers.customize(transactionManager));
		return transactionManager;
	}

	@Bean(value = Mode.MONGO)
	public MongoTransactionManager mongoTransactionManager(MongoDatabaseFactory dbFactory) {
		return new MongoTransactionManager(dbFactory);
	}

	@Bean(value = Mode.CHAINED)
	public PlatformTransactionManager chainedTransactionConfigurer(PlatformTransactionManager transactionManager,
			@Qualifier(Mode.MONGO) MongoTransactionManager mongoTransactionManager) {
		return new ChainedTransactionManager(transactionManager, mongoTransactionManager);
	}

	public static final class Mode {

		public static final String MONGO = "mongoTrans";

		public static final String CHAINED = "chainedTrans";

		private Mode() {
		}
	}
}
