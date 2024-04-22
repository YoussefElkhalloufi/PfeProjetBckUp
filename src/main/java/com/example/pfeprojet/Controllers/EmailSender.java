package com.example.pfeprojet.Controllers;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

public class EmailSender {

    public static void sendEmail(String receiptEmail, String subject, String body) {
        final String senderEmail = "facturEasePFE@gmail.com";
        final String senderPassword = "vkdm olse tfbq ibgj";

        // Host
        String host = "smtp.gmail.com";

        // Setup properties for the SMTP server
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        // Define a custom Authenticator class
        class MyAuthenticator extends javax.mail.Authenticator {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        }

        // Create an instance of the custom Authenticator class
        Authenticator authenticator = new MyAuthenticator();

        // Use the authenticator when creating the session
        Session session = Session.getInstance(props, authenticator);

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(senderEmail));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiptEmail));
            msg.setSubject(subject);
            msg.setText(body);
            // Send the message
            Transport.send(msg);
            System.out.println("Email sent successfully to " + receiptEmail);
        } catch (MessagingException e) {
            System.err.println("Failed to send email. Error message: " + e.getMessage());
        }
    }
    public static boolean isValidEmailAddress(String email) {
        // Basic email validation
        return email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
    }
    public static boolean check() {
        try {
            InetAddress address = InetAddress.getByName("www.google.com");
            boolean isConnected = address.isReachable(5000);

            if(isConnected) {
                System.out.println("Connected to the internet !!!");
            }else {
                System.out.println("Not connected to the internet !!!!");
            }
            return isConnected;
        }catch(UnknownHostException e) {
            System.err.println("Unknown host: www.google.com");
        }catch (java.io.IOException e) {
            System.err.println("Error checking internet connection : " +e.getMessage());
        }
        return false;
    }
}
