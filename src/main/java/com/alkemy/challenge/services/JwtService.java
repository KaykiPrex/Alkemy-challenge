package com.alkemy.challenge.services;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service
public class JwtService {
	private static final Log logger = LogFactory.getLog(JwtService.class);
	public static final String BEARER = "Bearer";

	private final String USER = "user";
	private final String ROLES = "roles";
	private final String ISSUER = "Alkemy";
	private final int EXPIRES_MILLIS = 1 * 1 * 60 * 1000;
	private static final String SECRET_KEY = "onlyfordemo"; // Advertencia ! Utilizar en variables de entorno

	public String CreateToken(String user, List<String> roles) {
		return JWT.create().withIssuer(ISSUER).withIssuedAt(new Date()).withNotBefore(new Date())
				.withExpiresAt(new Date(System.currentTimeMillis() + EXPIRES_MILLIS)).withClaim(USER, user)
				.withArrayClaim(ROLES, roles.toArray(new String[0])).sign(Algorithm.HMAC256(SECRET_KEY));
	}

	private DecodedJWT verify(String authorization) {
		DecodedJWT tokenDecoded= this.getTokenDecoded(authorization);
		if (!this.isTokenExpired(tokenDecoded)) {
			return JWT.require(Algorithm.HMAC256(SECRET_KEY)).withIssuer(ISSUER).build()
					.verify(this.getTokenAuth(authorization));
		} else {
			throw new TokenExpiredException("Token is expired "+this.getExpiredDateToken(tokenDecoded));
		}
	}

	private String getTokenAuth(String authorization) {
		if (authorization.split(" ").length==2) {
			return authorization.split(" ")[1].trim();
		}
		else throw new JWTVerificationException("Token is not valid");
		
	}

	private DecodedJWT getTokenDecoded(String authorization) {
		String tokenString = this.getTokenAuth(authorization);
		return JWT.decode(tokenString);
	}

	private boolean isTokenExpired(DecodedJWT tokenDecoded) {
		Date expiredDate = this.getExpiredDateToken(tokenDecoded);
		return expiredDate.before(new Date());
	}

	private Date getExpiredDateToken(DecodedJWT tokenDecoded) {
		return tokenDecoded.getExpiresAt();
	}

	public boolean isBearer(String authorization) {
		return authorization != null && authorization.startsWith(BEARER);
	}

	public Boolean TokenExpired(String authorization) {
		Boolean expiredBoolean = this.isTokenExpired(this.getTokenDecoded(authorization));
		return expiredBoolean;
	}

	public String user(String authorization) {
		return this.verify(authorization).getClaim(USER).asString();
	}

	public List<String> roles(String authorization) {
		return Arrays.asList(this.verify(authorization).getClaim(ROLES).asArray(String.class));
	}
}
