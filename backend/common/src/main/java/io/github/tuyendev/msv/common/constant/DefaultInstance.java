package io.github.tuyendev.msv.common.constant;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rockaport.alice.Alice;
import com.rockaport.alice.AliceContext;
import com.rockaport.alice.AliceContextBuilder;

import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

public class DefaultInstance {

	public static final Alice CRYPTO;

	public static final ObjectMapper OBJECT_MAPPER;

	static {
		CRYPTO = new Alice(new AliceContextBuilder()
				.setAlgorithm(AliceContext.Algorithm.AES)
				.setMode(AliceContext.Mode.CBC)
				.setPadding(AliceContext.Padding.PKCS5_PADDING)
				.setIvLength(16)
				.build());

		OBJECT_MAPPER = Jackson2ObjectMapperBuilder.json()
				.featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
				.featuresToEnable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
				.build();
	}

}
