package com.iss.test;

import javax.naming.NamingException;
import javax.naming.directory.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Hashtable;

public class TestEmail {

    public static boolean isEmailValid(String email) {
        try {
            // Extract domain from email
            String domain = email.substring(email.indexOf("@") + 1);

            // Get MX records
            String mxRecord = getMXRecord(domain);
            if (mxRecord == null) {
                System.out.println("No MX records found for domain: " + domain);
                return false;
            }

            // Connect to the SMTP server
            return verifyEmailWithSMTP(mxRecord, email);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static String getMXRecord(String domain) throws NamingException {
        Hashtable<String, String> env = new Hashtable<>();
        env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
        DirContext ctx = new InitialDirContext(env);
        Attributes attributes = ctx.getAttributes(domain, new String[]{"MX"});

        Attribute attr = attributes.get("MX");
        if (attr == null || attr.size() == 0) {
            return null;
        }

        // Extract MX record (first one)
        String mxRecord = (String) attr.get(0);
        return mxRecord.split(" ")[1];  // Extract mail server address
    }

    private static boolean verifyEmailWithSMTP(String mailServer, String email) {
        try (Socket socket = new Socket(mailServer, 25);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // Read server response
            if (!reader.readLine().startsWith("220")) {
                return false;
            }

            // Send HELO command
            socket.getOutputStream().write("HELO example.com\r\n".getBytes());
            if (!reader.readLine().startsWith("250")) {
                return false;
            }

            // Send MAIL FROM
            socket.getOutputStream().write("MAIL FROM:<test@example.com>\r\n".getBytes());
            if (!reader.readLine().startsWith("250")) {
                return false;
            }

            // Send RCPT TO (Check if recipient exists)
            socket.getOutputStream().write(("RCPT TO:<" + email + ">\r\n").getBytes());
            String response = reader.readLine();

            // Close the connection
            socket.getOutputStream().write("QUIT\r\n".getBytes());

            // Response 250 means email exists
            return response.startsWith("250");

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        String email = "vinayakbhilawadi1@gmail.com";
        boolean isValid = isEmailValid(email);
        System.out.println("Email " + email + " is valid: " + isValid);
    }
}
