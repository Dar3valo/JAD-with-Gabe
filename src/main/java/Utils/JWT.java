package Utils;

import java.util.Base64;
import java.util.Map;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JWT {
    // Create JWT with booking ID and time range
    public static String createJWT(String secretKey, String bookingId, 
                                 LocalDateTime allowedStartTime, LocalDateTime allowedEndTime) {
        try {
            System.out.println("Creating JWT for booking: " + bookingId);
            
            // Create header
            String header = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
            String encodedHeader = Base64.getUrlEncoder().withoutPadding().encodeToString(header.getBytes());
            
            // Format dates for JSON
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
            String startTime = allowedStartTime.format(formatter);
            String endTime = allowedEndTime.format(formatter);

            System.out.println("Time window: " + startTime + " to " + endTime);

            // Create payload with booking ID and time range only
            String payload = String.format(
                "{\"bookingId\":\"%s\",\"allowedStart\":\"%s\",\"allowedEnd\":\"%s\"}",
                bookingId,
                startTime,
                endTime
            );
            
            System.out.println("Payload: " + payload);
            
            String encodedPayload = Base64.getUrlEncoder().withoutPadding().encodeToString(payload.getBytes());

            // Create signature
            String signatureInput = encodedHeader + "." + encodedPayload;
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secretKeySpec);
            String signature = Base64.getUrlEncoder().withoutPadding()
                .encodeToString(sha256_HMAC.doFinal(signatureInput.getBytes()));

            String token = signatureInput + "." + signature;
            System.out.println("Generated token: " + token);
            return token;

        } catch (Exception e) {
            System.out.println("Error creating JWT: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error creating JWT", e);
        }
    }

    // Decode JWT without time validation
    public static Map<String, Object> decodeJWT(String jwt, String secretKey) throws Exception {
        try {
            System.out.println("Decoding JWT");
            
            // Split JWT
            String[] parts = jwt.split("\\.");
            if (parts.length != 3) {
                System.out.println("Invalid token format - wrong number of parts: " + parts.length);
                throw new IllegalArgumentException("Invalid JWT format");
            }

            // Decode payload first for logging
            String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]));
            System.out.println("Decoded payload: " + payloadJson);

            // Verify signature
            String signatureInput = parts[0] + "." + parts[1];
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secretKeySpec);
            String expectedSignature = Base64.getUrlEncoder().withoutPadding()
                .encodeToString(sha256_HMAC.doFinal(signatureInput.getBytes()));

            System.out.println("Expected signature: " + expectedSignature);
            System.out.println("Received signature: " + parts[2]);

            if (!expectedSignature.equals(parts[2])) {
                System.out.println("Signature verification failed");
                throw new SecurityException("JWT signature verification failed");
            }

            // Parse payload
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> claims = mapper.readValue(payloadJson, Map.class);
            System.out.println("Successfully decoded claims: " + claims);
            return claims;

        } catch (Exception e) {
            System.out.println("Error decoding JWT: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}