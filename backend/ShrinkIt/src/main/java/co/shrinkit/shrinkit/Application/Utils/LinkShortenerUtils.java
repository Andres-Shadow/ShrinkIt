package co.shrinkit.shrinkit.Application.Utils;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class LinkShortenerUtils {

    @Value("${app.link.prefix}")
    private String prefix;

    @Value("${app.link.server}")
    private String serverAddress;

    @Value("${server.port}")
    private String serverPort;

    public static String linkPrefix;
    public static String linkServerAddress;
    public static String linkServerPort;

    @PostConstruct
    public void init() {
        linkPrefix = prefix;
        linkServerAddress = serverAddress;
        linkServerPort = serverPort;
    }

    public static String generateShortUrl(Long linkId, String originalUrl) {
        String urlPrefix = linkServerAddress + ":" + linkServerPort + "/" + linkPrefix + "/";
        System.out.println(urlPrefix);
        String encodeLink = encodeLinkId(linkId);
        String hashFragment = generateHashFragment(originalUrl);
        return urlPrefix + encodeLink + hashFragment;
    }

    private static String encodeLinkId(Long linkId) {
        String base62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder shortUrl = new StringBuilder();

        while (linkId > 0) {
            int remainder = (int) (linkId % 62);
            shortUrl.append(base62.charAt(remainder));
            linkId /= 62;
        }

        return shortUrl.toString();
    }

    private static String generateHashFragment(String originalUrl) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(originalUrl.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.substring(0, 5);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al generar el hash SHA-256", e);
        }
    }
}
