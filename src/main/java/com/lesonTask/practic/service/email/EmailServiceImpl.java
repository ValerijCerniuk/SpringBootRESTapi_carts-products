package com.lesonTask.practic.service.email;

import com.lesonTask.practic.model.Cart;
import com.lesonTask.practic.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.List;


@Service
public class EmailServiceImpl implements EmailService {

    private static final String NOREPLY_ADDRESS = "testpracticalemail@gmail.com";

    @Autowired
    private JavaMailSender emailSender;


    @Value("classpath:/img/envelope.png")
    private Resource resourceFile;

    public void sendSimpleMessage(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(NOREPLY_ADDRESS);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            emailSender.send(message);
        } catch (MailException exception) {
            exception.printStackTrace();
        }
    }


    @Override
    public void sendMessageWithAttachment(String to,
                                          String subject,
                                          String text,
                                          String pathToAttachment) {
        try {
            MimeMessage message = emailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(NOREPLY_ADDRESS);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);

            FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
            helper.addAttachment("Invoice", file);

            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendCartViaEmail(final Cart cart) {

        String to = cart.getOwnerEmail();
        String subject = "Products for: " + cart.getOwnerName();
        String text = ("\n For: " + cart.getOwnerName() +
                "\n Email: " + cart.getOwnerEmail() +
                "\n\n");
        Double totalPrice = 0.0;

        List<Product> products = cart.getProducts();

        for (Product product : products) {
            text = text.concat("\t" + product.getName() + " : " +
                    product.getAmount() + " at price " +
                    product.getPrice() + "\n");
            totalPrice += product.getPrice();
        }

        try {

            String pathToAttachment = resourceFile.getURI().getPath();

            MimeMessage message = emailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(NOREPLY_ADDRESS);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);

            FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
            helper.addAttachment("Invoice", file);

            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
