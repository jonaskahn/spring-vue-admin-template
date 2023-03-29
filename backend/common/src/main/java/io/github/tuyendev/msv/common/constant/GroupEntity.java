package io.github.tuyendev.msv.common.constant;

import java.util.List;
import java.util.Map;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GroupEntity {

	public enum Type {
		ADMIN(1L, "admin", ""),
		USER(2L, "user", "");

		public static final List<Long> defaultGroupIds = List.of(ADMIN.id, USER.id);

		public static final String ADMIN_VALUE = "admin";

		public static final String USER_VALUE = "users";

		private static final Map<Long, Type> data = Map.of(
				ADMIN.id, ADMIN,
				USER.id, USER);

		final Long id;

		final String name;

		final String desc;

		Type(Long id, String name, String desc) {
			this.id = id;
			this.name = name;
			this.desc = desc;
		}

		public Type typeOf(Long id) {
			return data.get(id);
		}

		public Long getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public String getDesc() {
			return desc;
		}

	}
}
