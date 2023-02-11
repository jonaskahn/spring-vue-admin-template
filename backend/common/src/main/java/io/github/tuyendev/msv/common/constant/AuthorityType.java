package io.github.tuyendev.msv.common.constant;

import java.util.Map;

public enum AuthorityType {
    ADMIN("admin", "app.common.authority.label.admin"),
    EDITOR("editor", "app.common.authority.label.editor"),
    USER("user", "app.common.authority.label.user");

    private static final Map<String, AuthorityType> data = Map.of(
            ADMIN.value(), ADMIN,
            EDITOR.value, EDITOR,
            USER.value, USER);

    final String value;
    final String desc;

    AuthorityType(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static AuthorityType typeOf(String value) {
        return data.get(value);
    }

    public String value() {
        return value;
    }

    public String desc() {
        return desc;
    }
}
