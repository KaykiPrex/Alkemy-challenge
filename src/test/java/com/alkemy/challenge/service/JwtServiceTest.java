package com.alkemy.challenge.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.alkemy.challenge.services.JwtService;
import com.auth0.jwt.JWT;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {
	@InjectMocks
	private JwtService jwtService;

	private String token;

	@BeforeEach
	void setup() {
	}
	
	@Nested
	@DisplayName("Create token")
	class CreateToken {
		private String MyUser = "juan"; 
		private List<String> UserRoles = Arrays.asList("USER");	
		
		@BeforeEach
		void setup() {
			
		}
		@Test
		@DisplayName("Token with name of user correct")
		public void tokenUserValidation() throws Exception {
			String tokenJWT =jwtService.CreateToken(MyUser,UserRoles);
			String userDecoded = JWT.decode(tokenJWT).getClaim("user").asString();
			String[] rolesDecoded = JWT.decode(tokenJWT).getClaims().get("roles").asArray(String.class);
			log.info(JWT.decode(tokenJWT).getClaim("user").asString());
				
			assertThat(userDecoded,is(equalTo(MyUser)));
		}
		@Test
		@DisplayName("Token with roles of user correct")
		public void tokenRolesValidation() throws Exception {
			String tokenJWT =jwtService.CreateToken(MyUser,UserRoles);
			String userDecoded = JWT.decode(tokenJWT).getClaim("user").asString();
			String[] rolesDecoded = JWT.decode(tokenJWT).getClaims().get("roles").asArray(String.class);
			
			log.info(Arrays.asList((JWT.decode(tokenJWT).getClaims().get("roles").asArray(String.class))).toString());
			assertThat(Arrays.asList(rolesDecoded),is(equalTo(UserRoles)));
		}
	}
	
	@Nested
	@DisplayName("Authorization Bearer")
	class AuthorizationBearer {
		@Test
		@DisplayName("Authorization have bearer")
		public void authorizationHaveBearer() throws Exception {
			String authorizationWithBearer = "Bearer TOKEN";
			assertThat(jwtService.isBearer(authorizationWithBearer), is(equalTo(true)));
		}

		@Test
		@DisplayName("Authorization dont have bearer")
		public void authorizationWithoutBearer() throws Exception {
			String authorizationWithoutBearer = "TOKEN";
			assertThat(jwtService.isBearer(authorizationWithoutBearer), is(equalTo(false)));
		}

		@Test
		@DisplayName("Authorization have bearer with space in the start")
		public void authorizationWithBearerSpaceInStart() throws Exception {
			String authorizationWithoutBearer = " Bearer TOKEN";
			assertThat(jwtService.isBearer(authorizationWithoutBearer), is(equalTo(false)));
		}

		@Test
		@DisplayName("Authorization have bearer with space in middle")
		public void authorizationWithBearerSpaceInMiddle() throws Exception {
			String authorizationWithoutBearer = "Be arer TOKEN";
			assertThat(jwtService.isBearer(authorizationWithoutBearer), is(equalTo(false)));
		}
	}

}
