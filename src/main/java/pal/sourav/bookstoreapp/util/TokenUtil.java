package pal.sourav.bookstoreapp.util;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.auth0.jwt.interfaces.Verification;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TokenUtil {

	private static final String TOKEN_SECRET = "Security";

	public String createToken(int id) {
		try {
			// Setting up the algorithm
			Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
			String token = JWT.create().withClaim("id", id).sign(algorithm);
			return token;

		} catch (JWTCreationException execption) {
			execption.printStackTrace();

		} catch (IllegalArgumentException execption) {
			execption.printStackTrace();
		}

		return null;
	}

	
	
	public int decodeToken(String token) {
		int userId;

		Verification verification = null;    // for verifying the algo we'll use this object later below

		try {
			verification = JWT.require(Algorithm.HMAC256(TOKEN_SECRET));
			log.info("Verification successful with TOKEN_SECRET");
			
		} catch (IllegalArgumentException e) {
			log.info("Token verification failed");
			e.printStackTrace();
		}

		JWTVerifier jwtVerifier = verification.build();
		log.info("jwt verifier : " + jwtVerifier);

		// decoding the token & verifying that with the given token
		DecodedJWT decodedJWT = jwtVerifier.verify(token);
		Claim claim = decodedJWT.getClaim("id");
		userId = claim.asInt();
		return userId;
	}
	
}
