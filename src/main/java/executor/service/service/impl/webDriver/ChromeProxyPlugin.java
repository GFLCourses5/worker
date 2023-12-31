package executor.service.service.impl.webDriver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * The {@code ChromeProxyPlugin} is a utility class for creating proxy plugin for Google Chrome browser.
 * Used by {@code WebDriverInitializerImpl} class
 *
 * @author Oleksii Bondarenko
 * @version 01
 */
public class ChromeProxyPlugin {
    public static File generate(String host, int port, String username, String password) {
        String manifestJson = generateManifestJson();
        String backgroundJs = generateBackgroundJs(host, port, username, password);

        try {
            File zipFile = new File("proxy_auth_plugin.zip");
            try (FileOutputStream fos = new FileOutputStream(zipFile);
                 ZipOutputStream zipOS = new ZipOutputStream(fos)) {
                writeToZipFile(zipOS, "manifest.json", manifestJson);
                writeToZipFile(zipOS, "background.js", backgroundJs);
            }
            return zipFile;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String generateManifestJson() {
        return """
        {
          "version": "1.0.0",
          "manifest_version": 2,
          "name": "Chrome Proxy",
          "permissions": [
            "proxy",
            "tabs",
            "unlimitedStorage",
            "storage",
            "<all_urls>",
            "webRequest",
            "webRequestBlocking"
          ],
          "background": {
            "scripts": ["background.js"]
          },
          "minimum_chrome_version": "22.0.0"
        }
        """;
    }

    private static String generateBackgroundJs(String host, int port, String username, String password) {
        return String.format("""
        var config = {
          mode: "fixed_servers",
          rules: {
            singleProxy: {
              scheme: "http",
              host: "%s",
              port: %d
            },
            bypassList: ["localhost"]
          }
        };

        chrome.proxy.settings.set({value: config, scope: "regular"}, function() {});

        function callbackFn(details) {
          return {
            authCredentials: {
              username: "%s",
              password: "%s"
            }
          };
        }

        chrome.webRequest.onAuthRequired.addListener(
          callbackFn,
          {urls: ["<all_urls>"]},
          ['blocking']
        );
        """, host, port, username, password);
    }

    private static void writeToZipFile(ZipOutputStream zipStream, String entryName, String content) throws IOException {
        zipStream.putNextEntry(new ZipEntry(entryName));
        zipStream.write(content.getBytes());
        zipStream.closeEntry();
    }
}

