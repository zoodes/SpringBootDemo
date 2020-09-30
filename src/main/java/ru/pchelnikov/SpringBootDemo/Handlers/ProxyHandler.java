package ru.pchelnikov.SpringBootDemo.Handlers;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;

@Slf4j
public class ProxyHandler {
    /**
     * bypassing proxy that I have at work.
     * To work, needs setup VM Options as written below
     * (maybe some of them are not necessary, but it works):
     * -Dhttps.proxyHost=proxy
     * -Dhttps.proxyPort=port
     * -Dhttp.proxyHost=proxy
     * -Dhttp.proxyPort=port
     * -Dhttp.proxyUser=user
     * -Dhttp.proxyPassword=password
     * -Dhttp.nonProxyHosts="localhost|127.0.0.1|10.*.*.*"
     * -Djdk.http.auth.tunneling.disabledSchemes=
     * If not configured, doesn't influence work of rest program
     */
    public static void bypassProxy() {
        final String proxyUser = System.getProperty("http.proxyUser");
        final String proxyPassword = System.getProperty("http.proxyPassword");

        if (proxyUser != null && proxyPassword != null) {
            Authenticator.setDefault(
                    new Authenticator() {
                        @Override
                        public PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(
                                    proxyUser, proxyPassword.toCharArray()
                            );
                        }
                    }
            );
        }
        testConnection();
    }

    private static void testConnection() {
        try {

            URL url = new URL("https://google.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                //System.out.println(inputLine);
            }
            in.close();
            log.info("Connection via proxy established successfully");
        } catch (IOException e) {
            log.error("Connection via proxy failed!");
            e.printStackTrace();
        }
    }
}
