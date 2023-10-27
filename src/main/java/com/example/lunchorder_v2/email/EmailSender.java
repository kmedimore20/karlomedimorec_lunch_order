package com.example.lunchorder_v2.email;

import com.example.lunchorder_v2.model.Menu;
import com.example.lunchorder_v2.model.Order;
import com.example.lunchorder_v2.repository.MenuRepository;
import com.example.lunchorder_v2.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.Properties;
import javax.mail.Session;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Component
@Service
public class EmailSender {

    @Autowired
    private final MenuRepository menuRepository;

    @Autowired
    private final OrderRepository orderRepository;

    @Autowired
    public EmailSender(OrderRepository orderRepository, MenuRepository menuRepository){

        this.orderRepository = orderRepository;
        this.menuRepository = menuRepository;
    }

    //Getting the variables from application.properties
    @Value("${emailSender}")
    private String emailSender;

    @Value("${emailSenderPassword} ")
    private String emailSenderPassword;

    @Value("${emailReceiver}")
    private String emailReceiver;

    @Value("${smtpHost}")
    private String smtpHost;

    @Value("${smtpPort}")
    private String smtpPort;

    //Sending the email, scheduled for 10:30 AM
    //For email to be sent format of the mealId must be correct
    @Scheduled(cron = "0 30 10 * * *")
    public void sendEmail(){

        //Defining properties
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", smtpPort);
        properties.put("mail.smtp.port", smtpPort);

        Session session = Session.getInstance(properties);

        try {
            //Creating message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailReceiver));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailReceiver));
            message.setSubject("Lunch order");
            message.setText(parseOrderData());

            //Sending message
            Transport transport = session.getTransport("smtp");
            transport.connect(smtpHost, emailSender, emailSenderPassword);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    //Method for parsing the order data stored in database into string that will be used in email body
    public String parseOrderData(){

        //Getting the last order
        Optional<Order> orderData;
        String orderString = new String();
        for (long id = 1; ; id++){
            orderData = orderRepository.findById(id);
            if (orderData.isEmpty()){
                --id;
                orderData = orderRepository.findById(id);
                if (orderData.isPresent()) {
                    Order _orderData = orderData.get();
                    orderString = _orderData.getMenuItems();
                }
                break;
            }
        }

        //Storing order string into array by splitting it where the ";" character is
        String[] orderStringArray = orderString.replace(" ", "").split(";");

        //Storing quantity and mealId in separate arrays
        long[] orderLongArray = new long[orderStringArray.length];
        String[] modifiedOrderStringArray = new String[orderStringArray.length];

        for (int i = 0; i < orderStringArray.length; i++) {
            String inputString = orderStringArray[i];
            String[] parts = inputString.split("x");

            long number = Integer.parseInt(parts[1]);
            orderLongArray[i] = number;
            modifiedOrderStringArray[i] = parts[0];
        }

        //Storing the meal names based on mealId
        Optional<Menu> menuData;
        String[] menuArray = new String[orderLongArray.length];
        int menuArrayCounter = 0;
        for (int i = 0; i < menuArray.length; i++){
            menuData = menuRepository.findById(orderLongArray[i]);
            if (menuData.isPresent()) {
                Menu _menuData = menuData.get();
                menuArray[menuArrayCounter] = _menuData.getMealName();
                menuArrayCounter++;
            }
        }

        //Accumulating all the orders in one string
        String[] menuItems = new String[menuArray.length];
        String finalMessage = "Hello, we would like to order the following:\n";

        for (int i = 0; i < menuItems.length; i++){
            menuItems[i] = modifiedOrderStringArray[i] + " x " + menuArray[i];
            finalMessage += "\n" + menuItems[i];
        }

        finalMessage += "\n\nRegards, \nLeapwise team";

        return finalMessage;
    }
}
