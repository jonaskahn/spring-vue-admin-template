package io.github.tuyendev.msv.common.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.github.tuyendev.msv.common.CommonMessageSource;
import io.github.tuyendev.msv.common.exception.LogicException;
import io.github.tuyendev.msv.common.exception.jwt.InvalidJwtTokenException;
import jakarta.servlet.ServletException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeansException;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Slf4j
@JsonPropertyOrder({"status", "timestamp", "payload"})
public class Response<T> implements Serializable {

	private static final String EMPTY = "";

	protected static MessageSourceAccessor messageSource = CommonMessageSource.getAccessor();

	private int status;

	private long timestamp;

	private String message;

	private T payload;

	public static Response ok() {
		return Response.builder()
				.status(HttpStatus.OK.value())
				.message(eval("app.common.message.success"))
				.timestamp(now())
				.build();
	}

	private static long now() {
		return Instant.now().getEpochSecond();
	}

	private static String eval(String key) {
		try {
			return messageSource.getMessage(key);
		}catch (Exception e){
			return key;
		}
	}

	private static String eval(String key, Object... args) {
		return messageSource.getMessage(key, args);
	}

	public static <T> Response ok(T payload) {
		return Response.builder()
				.status(HttpStatus.OK.value())
				.message(eval("app.common.message.success"))
				.timestamp(now())
				.payload(payload)
				.build();
	}

	public static Response ok(String message) {
		return Response.builder()
				.status(HttpStatus.OK.value())
				.message(eval(message))
				.timestamp(now())
				.build();
	}

	public static <T> Response ok(T payload, String message) {
		return Response.builder()
				.status(HttpStatus.OK.value())
				.message(eval(message))
				.timestamp(now())
				.payload(payload)
				.build();
	}


	public static Response ok(String message, Object... args) {
		return Response.builder()
				.status(HttpStatus.OK.value())
				.message(eval(message, args))
				.timestamp(now())
				.build();
	}

	public static Response failed(int status, String message) {
		final String uuid = UUID.randomUUID().toString();
		log(uuid, null);
		return Response.builder()
				.status(status)
				.timestamp(now())
				.message(eval(message))
				.payload(new ErrorContent(uuid, null))
				.build();
	}

	public static Response failed(LogicException e) {
		return failed(HttpStatus.BAD_REQUEST.value(), e.getMessage(), e);
	}

	private static Response failed(int httpCode, String message, Throwable e) {
		return failed(httpCode, message, null, e);
	}

	private static Response failed(int httpCode, String message, Object details, Throwable e) {
		final String uuid = UUID.randomUUID().toString();
		log(uuid, e);
		return Response.builder()
				.status(httpCode)
				.timestamp(now())
				.message(message)
				.payload(new ErrorContent(uuid, details))
				.build();
	}

	private static void log(String uuid, Throwable e) {
		if(Objects.isNull(e)){
			log.error((String.format("EXCEPTION CODE: %s ", uuid)));
		} else if (e instanceof Exception) {
			log.error((String.format("EXCEPTION CODE: %s ", uuid)), e);
		}
		else if (e instanceof Error) {
			log.error((String.format("ERROR CODE: %s", uuid)), e);
		}
		else {
			log.error((String.format("THROWABLE CODE: %s ", uuid)), e);
		}

	}

	public static Response failed(MethodArgumentNotValidException e) {
		return failed(HttpStatus.BAD_REQUEST.value(), eval("app.common.exception.validation"), getFailedValidationFields(e), e);
	}

	private static Map<String, String> getFailedValidationFields(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}

	public static Response failed(AccessDeniedException e) {
		return failed(HttpStatus.FORBIDDEN.value(), eval("app.common.exception.forbidden-access"), e);
	}

	public static Response failed(HttpRequestMethodNotSupportedException e) {
		return failed(HttpStatus.NOT_ACCEPTABLE.value(), eval("app.common.exception.unsupported-method"), e);
	}

	public static Response failed(HttpMediaTypeNotSupportedException e) {
		return failed(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), eval("app.common.exception.unsupported-media-type"), e);
	}

	public static Response failed(HttpMediaTypeNotAcceptableException e) {
		return failed(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), eval("app.common.exception.unsupported-media-type"), e);
	}

	public static Response failed(ServletException e) {
		return failed(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), eval("app.common.exception.servlet"), e);
	}

	public static Response failed(MissingServletRequestPartException e) {
		return failed(HttpStatus.INTERNAL_SERVER_ERROR.value(), eval("app.common.exception.missing-servlet-request-path"), e);
	}

	public static Response failed(NoHandlerFoundException e) {
		return failed(HttpStatus.BAD_REQUEST.value(), eval("app.common.exception.http-not-found"), e);
	}

	public static Response failed(HttpMessageConversionException e) {
		return failed(HttpStatus.BAD_REQUEST.value(), eval("app.common.exception.http-message-conversion"), e);
	}

	public static Response failed(BeansException e) {
		return failed(HttpStatus.INTERNAL_SERVER_ERROR.value(), eval("app.common.exception.property-access"), e);
	}

	public static Response failed(AuthenticationException e) {
		return failed(HttpStatus.UNAUTHORIZED.value(), getAuthenticationMessage(e), e);
	}

	public static String getAuthenticationMessage(AuthenticationException e) {
		if (e instanceof InsufficientAuthenticationException) {
			return eval("app.common.exception.authentication.insufficient");
		}
		if (e instanceof AccountExpiredException) {
			return eval("app.common.exception.authentication.account-expired");
		}
		if (e instanceof CredentialsExpiredException) {
			return eval("app.common.exception.authentication.account-credential-expired");
		}
		if (e instanceof DisabledException) {
			return eval("app.common.exception.authentication.account-disabled");
		}
		if (e instanceof LockedException) {
			return eval("app.common.exception.authentication.account-locked");
		}
		if (e instanceof AccountStatusException) {
			return eval("app.common.exception.authentication.account-inaccessible");
		}
		if (e instanceof BadCredentialsException) {
			return eval("app.common.exception.authentication.bad-credential");
		}
		if (e instanceof InvalidJwtTokenException) {
			return eval("app.common.exception.authentication.invalid-jwt");
		}
		return eval("app.common.exception.authentication");
	}

	public static Response failed(DataAccessException e) {
		return failed(HttpStatus.BAD_REQUEST.value(), eval("app.common.exception.cannot-access-data"), e);
	}


	public static Response failed(RuntimeException e) {
		return failed(HttpStatus.INTERNAL_SERVER_ERROR.value(), eval("app.common.exception.runtime-unhandled"), e);
	}

	public static Response failed(AsyncRequestTimeoutException e) {
		return failed(HttpStatus.INTERNAL_SERVER_ERROR.value(), eval("app.common.exception.aysn-request-timeout"), e);
	}

	public static Response unexpected(Exception e) {
		return failed(HttpStatus.INTERNAL_SERVER_ERROR.value(), eval("app.common.exception.unhandled"), e);
	}

	public static Response error(Error e) {
		return failed(HttpStatus.INTERNAL_SERVER_ERROR.value(), eval("app.common.exception.system"), e);
	}

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor(access = AccessLevel.PRIVATE)
	final static class ErrorContent implements Serializable {
		private String code;

		private Object details;
	}
}
