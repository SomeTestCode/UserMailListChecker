package ru.interview;

import org.apache.commons.validator.routines.EmailValidator;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * Данный класс осуществляет проверку массива строк на соответствие определенному формату
 * [имя пользователя] %s [email1]%s [email2]%s...
 * с последующей обработкой этого массива в список строк с уникальным именем пользователя и принадлежащими ему email-ами
 */
public class UserMailListChecker {

    //разделитель имени пользователя и списка email-ов
    public static final String USER_DATA_DELIMITER = "->";
    //разделитель списка email-ов
    public static final String MAILS_DELIMITER = ",";

    private final EmailValidator emailValidator = EmailValidator.getInstance();

    /**
     * Получение списка пользователей с уникальными именами и принадлежащими им email-ми
     * @param inputLines - список строк с данными о пользователях и их email-ах
     * @return обработанный массив строк заданного формата с уникальными именами
     */
    public List<String> getUniqueUserWithMailsLine(List<String> inputLines) {

        Map<String, String> userByMails = new LinkedHashMap<>();

        for (String inputLine : inputLines) {
            if(!inputLine.contains(USER_DATA_DELIMITER)){
                continue;
            }

            String[] lineParts = inputLine.split(USER_DATA_DELIMITER);

            String userName = lineParts[0].trim();
            String emailsString = lineParts[1].trim();

            if(userName.isEmpty() || emailsString.isEmpty()){
                continue;
            }

            String[] emails =  emailsString.split(MAILS_DELIMITER);

            for (String email : emails) {
                email = email.trim();
                if(!emailValidator.isValid(email)){
                    continue;
                }

                if(!userByMails.containsKey(email)){
                    userByMails.put(email, userName);
                } else {
                    userName = userByMails.get(email);
                }
            }
        }

        if(userByMails.isEmpty()){
            return new ArrayList<>();
        }

        return formatMailUserMapAsLine(userByMails);
    }

    /**
     * Конвертация списка email-ов и соответствующих им имен пользователей в список строк заданного формата
     * @param userByMails список email-ов и имен пользователей
     * @return массив строк с информацией о пользователях и и их email-ах
     */
    List<String> formatMailUserMapAsLine(Map<String, String> userByMails) {
        List<String> result = new ArrayList<>();

        Map<String, List<String>> mailByUser = getMailsByUser(userByMails);

        for (Map.Entry<String, List<String>> userMails : mailByUser.entrySet()) {
            result.add(makeFormattedLine(userMails.getKey(), userMails.getValue()));
        }
        return result;
    }

    /**
     * Конвертация списка виде email - ползователь в список вида пользователь - массив email-ов
     * @param userByMails списко email - ползователь
     * @return список пользователь - массив email-ов
     */
    private Map<String, List<String>> getMailsByUser(Map<String, String> userByMails) {
        Map<String, List<String>> mailByUser = new LinkedHashMap<>();

        for (Map.Entry<String, String> mailUserPair : userByMails.entrySet()) {
            String mail = mailUserPair.getKey();
            String user = mailUserPair.getValue();
            if(!mailByUser.containsKey(user)){
                mailByUser.put(user, new ArrayList<>());
            }
            mailByUser.get(user).add(mail);
        }

        return mailByUser;
    }

    /**
     * Форматирование данных в нужном представлении
     * @param user пользователь
     * @param userMails массив email-ов
     * @return строка данных
     */
    private String makeFormattedLine(String user, List<String> userMails) {
        return String.format("%s %s %s", user, USER_DATA_DELIMITER, String.join(MAILS_DELIMITER + " ", userMails));
    }

}
