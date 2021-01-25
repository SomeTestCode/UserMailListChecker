package ru.interview;

import java.util.*;
import java.util.stream.Collectors;

public class App {

    public static void main(String[] args) {

        System.out.println(
                String.format(
                        "Введите данные по формату [имя пользователя] %s [email1]%s [email2]%s...\n",
                        UserMailListChecker.USER_DATA_DELIMITER, UserMailListChecker.MAILS_DELIMITER, UserMailListChecker.MAILS_DELIMITER)
        );

        List<String> lines = readDataFromSystemIn();

        UserMailListChecker userMailListChecker = new UserMailListChecker();
        List<String> uniqueUserWithMailsLines = userMailListChecker.getUniqueUserWithMailsLine(lines);

        if (uniqueUserWithMailsLines.isEmpty()) {
            System.out.println("В исходном списке нет корректных строк");
        }

        System.out.println(
                uniqueUserWithMailsLines.stream()
                        .collect(Collectors.joining(System.lineSeparator()))
        );
    }

    private static List<String> readDataFromSystemIn() {
        Scanner sc = new Scanner(System.in);

        List<String> input = new ArrayList<>();
        while (true) {
            String nextLine = sc.nextLine();
            if (nextLine.equals("")) {
                break;
            }
            input.add(nextLine);
        }
        return input;
    }

}
