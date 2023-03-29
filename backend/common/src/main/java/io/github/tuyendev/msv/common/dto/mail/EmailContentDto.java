package io.github.tuyendev.msv.common.dto.mail;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmailContentDto {
	private String to;

	private String from;

	private String subject;

	private String template;

	private Map<String, Object> props;
}
