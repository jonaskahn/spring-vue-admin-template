package io.github.tuyendev.msv.common.entity.extras;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentityGenerator;

public class UseExistingIdOtherwiseGenerateId extends IdentityGenerator {

	@Override
	public Object generate(SharedSessionContractImplementor session, Object object) {
		Object id = session.getEntityPersister(null, object).getIdentifier(object, session);
		return id != null ? id : super.generate(session, object);
	}
}
