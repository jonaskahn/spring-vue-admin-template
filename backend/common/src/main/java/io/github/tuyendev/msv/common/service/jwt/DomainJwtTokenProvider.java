package io.github.tuyendev.msv.common.service.jwt;

import java.security.Key;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.tuyendev.msv.common.constant.Token;
import io.github.tuyendev.msv.common.dto.jwt.JwtAccessTokenDto;
import io.github.tuyendev.msv.common.dto.jwt.JwtRefreshTokenDto;
import io.github.tuyendev.msv.common.dto.token.TokenInfoDto;
import io.github.tuyendev.msv.common.exception.ShouldNeverOccurException;
import io.github.tuyendev.msv.common.exception.UserNotExistedException;
import io.github.tuyendev.msv.common.exception.jwt.InvalidAudienceTokenException;
import io.github.tuyendev.msv.common.exception.jwt.InvalidJwtTokenException;
import io.github.tuyendev.msv.common.exception.jwt.RevokedJwtTokenException;
import io.github.tuyendev.msv.common.exception.jwt.UnknownIssuerTokenException;
import io.github.tuyendev.msv.common.security.jwt.JwtAccessToken;
import io.github.tuyendev.msv.common.security.jwt.JwtTokenProvider;
import io.github.tuyendev.msv.common.security.jwt.JwtTokenStore;
import io.github.tuyendev.msv.common.security.user.DomainUserDetailsService;
import io.github.tuyendev.msv.common.security.user.SecuredUser;
import io.github.tuyendev.msv.common.security.user.SecuredUserDetails;
import io.github.tuyendev.msv.common.utils.AppContextHelper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.PrematureJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class DomainJwtTokenProvider implements JwtTokenProvider {

	private static final ObjectMapper JACKSON_MAPPER;

	protected final AuthenticationManagerBuilder authenticationManagerBuilder;

	protected final DomainUserDetailsService userDetailsService;

	protected final JwtTokenStore tokenStore;

	private final UserDetailsChecker postCheckUserStatus = new AccountStatusUserDetailsChecker();

	@Value("${app.common.jwt.issuer}")
	private String issuer;

	@Value("${app.common.jwt.access-token-expiration-in-minutes}")
	private long accessTokenExpirationInMinutes;

	@Value("${app.common.jwt.refresh-token-expiration-in-minutes}")
	private long refreshTokenExpirationInMinutes;

	@Value("${app.common.jwt.remember-me-expiration-in-minutes}")
	private long rememberMeExpirationInMinutes;

	@Value("${app.common.jwt.secret-key}")
	private String jwtSecretKey;

	private JwtParser jwtParser;

	private Key secretKey;

	private static Claims getClaims(final JwtParser jwtParser, final String jwt) {
		try {
			return jwtParser.parseClaimsJws(jwt).getBody();
		}
		catch (ExpiredJwtException | MalformedJwtException | UnsupportedJwtException | SignatureException |
			   PrematureJwtException | IllegalArgumentException e) {
			log.trace("Invalid JWT jwt", e);
			throw new InvalidJwtTokenException();
		}
	}

	private static boolean isJwtTokenValid(final JwtParser jwtParser, final String jwt) {
		try {
			jwtParser.parseClaimsJws(jwt);
			return true;
		}
		catch (ExpiredJwtException | MalformedJwtException | UnsupportedJwtException | SignatureException |
			   PrematureJwtException | IllegalArgumentException e) {
			return false;
		}
	}

	private static Map<String, Object> getClaimsWithoutKey(final String jwt) {
		try {
			Base64.Decoder decoder = Base64.getUrlDecoder();
			var payload = decoder.decode(jwt.split("\\.")[1]);
			return JACKSON_MAPPER.readValue(payload, new TypeReference<>() {
			});
		}
		catch (Exception e) {
			log.error("Cannot parse payload in jwtToken", e);
			throw new InvalidJwtTokenException();
		}
	}

	@PostConstruct
	void afterInit() {
		byte[] keyBytes = Decoders.BASE64.decode(jwtSecretKey);
		this.secretKey = Keys.hmacShaKeyFor(keyBytes);
		this.jwtParser = Jwts.parserBuilder().setSigningKey(secretKey).build();
	}

	@Override
	public JwtAccessToken generateToken(String username, String password, boolean rememberMe) {
		Authentication authenticationToken = UsernamePasswordAuthenticationToken.unauthenticated(username, password);
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return tokenStore.generateTokenInTransaction(() -> createToken(rememberMe));
	}

	private JwtAccessToken createToken(final boolean rememberMe) {
		SecuredUser currentUser = AppContextHelper.getCurrentLoginUser()
				.orElseThrow(ShouldNeverOccurException::new);

		final JwtAccessTokenDto accessToken = createAccessToken(currentUser, rememberMe);
		tokenStore.saveAccessToken(accessToken.id(), currentUser.id(), accessToken.expiredAt());
		final JwtRefreshTokenDto refreshToken = createRefreshToken(accessToken.id(), accessToken.issuedAt(), rememberMe);
		tokenStore.saveRefreshToken(refreshToken.id(), refreshToken.accessTokenId(), currentUser.id(), refreshToken.expiredAt());

		Calendar converter = Calendar.getInstance();
		converter.setTime(accessToken.expiredAt());
		final long accessTokenExpiredAtInSeconds = converter.getTimeInMillis() / 1000;
		converter.setTime(refreshToken.expiredAt());
		final long refreshTokenExpiredAtInSeconds = converter.getTimeInMillis() / 1000;
		return JwtAccessToken.builder()
				.accessToken(accessToken.token())
				.refreshToken(refreshToken.token())
				.accessTokenExpiredAt(accessTokenExpiredAtInSeconds)
				.refreshTokenExpiredAt(refreshTokenExpiredAtInSeconds)
				.build();
	}

	private Date getExpirationDate(final Date issuedAt, final long defaultExpiration, final boolean rememberMe) {
		return new Date(issuedAt.getTime() + (rememberMe ? (defaultExpiration + rememberMeExpirationInMinutes) : defaultExpiration) * 1000 * 60);
	}

	private JwtAccessTokenDto createAccessToken(final SecuredUser user, final boolean rememberMe) {
		final String id = UUID.randomUUID().toString();
		final Date issuedAt = new Date();
		final Date expirationDate = getExpirationDate(issuedAt, accessTokenExpirationInMinutes, rememberMe);
		final String accessToken = Jwts.builder()
				.setIssuer(issuer)
				.setId(id)
				.setSubject(user.preferredUsername())
				.setIssuedAt(issuedAt)
				.setNotBefore(issuedAt)
				.setExpiration(expirationDate)
				.claim(Token.Claim.AUTHORITY, user.authorityNames())
				.claim(Token.Claim.TYPE, Token.Type.ACCESS)
				.signWith(secretKey)
				.compact();
		return JwtAccessTokenDto.builder()
				.id(id)
				.token(accessToken)
				.issuedAt(issuedAt)
				.expiredAt(expirationDate)
				.build();
	}

	private JwtRefreshTokenDto createRefreshToken(final String accessTokenId, final Date issuedAt, final boolean rememberMe) {
		final String refreshTokenId = UUID.randomUUID().toString();
		final Date expirationDate = getExpirationDate(issuedAt, refreshTokenExpirationInMinutes, rememberMe);
		final String refreshToken = Jwts.builder()
				.setIssuer(issuer)
				.setId(refreshTokenId)
				.setSubject(accessTokenId)
				.setIssuedAt(issuedAt)
				.setNotBefore(issuedAt)
				.setExpiration(expirationDate)
				.claim(Token.Claim.REMEMBER_ME, rememberMe)
				.claim(Token.Claim.TYPE, Token.Type.REFRESH)
				.signWith(secretKey)
				.compact();
		return JwtRefreshTokenDto.builder()
				.id(refreshTokenId)
				.accessTokenId(accessTokenId)
				.token(refreshToken)
				.issuedAt(issuedAt)
				.expiredAt(expirationDate)
				.build();
	}

	@Override
	public JwtAccessToken renewToken(final String jwtToken) {
		Claims claims = getClaims(jwtParser, jwtToken);
		if (!Objects.equals(claims.getIssuer(), issuer)) {
			throw new UnknownIssuerTokenException();
		}
		if (!Objects.equals(claims.get(Token.Claim.TYPE), Token.Type.REFRESH.getName())) {
			throw new InvalidAudienceTokenException();
		}
		final Long userId = tokenStore.getUserIdByRefreshTokenId(claims.getId());
		tokenStore.inactiveAccessTokenById(claims.getSubject());
		tokenStore.inactiveRefreshTokenById(claims.getId());
		setAuthenticationAfterSuccess(userId);
		return createToken(isPreviousRefreshTokenRememberMe(claims));
	}

	private boolean isPreviousRefreshTokenRememberMe(final Claims claims) {
		return claims.get(Token.Claim.REMEMBER_ME, Boolean.class);
	}

	@Override
	public void authorizeToken(final String jwtToken) {
		Claims claims = getClaims(jwtParser, jwtToken);
		if (!Objects.equals(claims.get(Token.Claim.TYPE), Token.Type.ACCESS.getName())) {
			throw new InvalidAudienceTokenException();
		}
		if (!tokenStore.isAccessTokenExisted(claims.getId())) {
			throw new RevokedJwtTokenException();
		}
		setAuthenticationAfterSuccess(claims.getSubject());
	}

	private void setAuthenticationAfterSuccess(final Object userRef) {
		UserDetails userDetails = buildPrincipalForRefreshTokenFromUser(userRef);
		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(authentication);
		SecurityContextHolder.setContext(context);
	}

	private UserDetails buildPrincipalForRefreshTokenFromUser(final Object userRef) {
		SecuredUserDetails userDetails;
		if (userRef instanceof Long userId) {
			userDetails = userDetailsService.loadUserByUserId(userId);
		}
		else if (userRef instanceof String username) {
			userDetails = userDetailsService.loadUserByPreferredUsername(username);
		}
		else throw new UserNotExistedException();
		postCheckUserStatus.check(userDetails);
		return userDetails;
	}

	@Override
	public boolean isSelfIssuer(final String jwtToken) {
		try {
			Map<String, Object> claims = getClaimsWithoutKey(jwtToken);
			return Objects.equals(claims.get((Claims.ISSUER)), issuer);
		}
		catch (Exception e) {
			log.error("Cannot parse payload in jwtToken", e);
			throw new InvalidJwtTokenException();
		}
	}

	@Override
	public TokenInfoDto getTokenInfo(String jwt) {
		final boolean isValid = isJwtTokenValid(jwtParser, jwt);
		if (isValid) {
			Map<String, Object> claims = getClaimsWithoutKey(jwt);
			final String type = (String) claims.get(Token.Claim.TYPE);
			boolean existed;
			if (Objects.equals(type, Token.Type.ACCESS.getName())) {
				existed = tokenStore.isAccessTokenExisted((String) claims.get(Claims.ID));
			}
			else if (Objects.equals(type, Token.Type.REFRESH.getName())) {
				existed = tokenStore.isRefreshTokenExisted((String) claims.get(Claims.ID));
			}
			else throw new ShouldNeverOccurException();
			return TokenInfoDto.builder()
					.issuer((String) claims.get(Claims.ISSUER))
					.type(Token.Type.typeOf(type).getDesc())
					.revoked(!existed)
					.expired(isJwtExpired((Long) claims.get(Claims.EXPIRATION)))
					.valid(true)
					.build();
		}
		return TokenInfoDto.builder()
				.valid(false)
				.build();
	}

	private boolean isJwtExpired(Long expiredDate) {
		final long nowAsSecond = new Date().getTime() / 1000;
		return nowAsSecond > expiredDate;
	}

	static {
		JACKSON_MAPPER = new ObjectMapper();
		JACKSON_MAPPER.enable(DeserializationFeature.USE_LONG_FOR_INTS);
	}

}
