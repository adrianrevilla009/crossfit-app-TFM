package adrian.tfm.crossfit.classes.utils;


import jakarta.servlet.http.HttpServletRequest;

public class JwtUtils {
    public static String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }

        return null;
    }

    public static boolean hasText(String str) {
        return str != null && !str.trim().isEmpty();
    }
}
