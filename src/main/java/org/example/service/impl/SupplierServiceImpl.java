package org.example.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.example.dto.Supplier;
import org.example.entity.SupplierEntity;
import org.example.repository.SupplierRepository;
import org.example.service.SupplierService;
import org.modelmapper.ModelMapper;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;
    private final JavaMailSender mailSender;
    public static final String SENDER_EMAIL = "taskinahned774@gmail.com";
    @Override
    public void saveSupplier(Supplier supplier) {
        try{
            SupplierEntity supplierEntity = supplierRepository.save(modelMapper.map(supplier, SupplierEntity.class));
            sendEmail(supplierEntity);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void sendEmail(SupplierEntity supplierEntity) {
        String subject = "Registration Successful";
        String body = "Hello " + supplierEntity.getSupplierName() + ",\n\n" +
                "Welcome to our platform! Your registration is complete, and you can now access your account to explore our services.\n\n" +
                "Here’s your Supplier ID: " + supplierEntity.getSupplierId() + "\n\n" +
                "Thank you for joining us.\n\n" +
                "Best regards,\n" +
                "Taskin Ahmed";
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(SENDER_EMAIL);
            helper.setTo(supplierEntity.getSupplierEmail());
            helper.setSubject(subject);
            helper.setText(body, true);

            mailSender.send(message);

        } catch (MessagingException e) {

            throw new RuntimeException("Failed to send email", e);
        }
    }
}
