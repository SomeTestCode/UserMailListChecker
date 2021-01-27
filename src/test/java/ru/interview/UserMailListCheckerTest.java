package ru.interview;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class UserMailListCheckerTest {

    @Test
    public void processEmptyUserEmailsLine() {
        UserMailListChecker userMailListChecker = new UserMailListChecker();
        List<String> lines = userMailListChecker.getUniqueUserWithMailsLine(new ArrayList<>());
        Assert.assertEquals(0, lines.size());
    }

    @Test
    public void processIncorrectUserEmailsLine() {
        UserMailListChecker userMailListChecker = new UserMailListChecker();
        List<String> lines = Arrays.asList(
                "",
                "user1",
                "user1 -> ",
                "-> test@example.com",
                "test@example.com"
        );
        List<String> result = userMailListChecker.getUniqueUserWithMailsLine(lines);
        Assert.assertEquals(0, result.size());
    }

    @Test
    public void processNormalMultiUserEmailsLine() {
        UserMailListChecker userMailListChecker = new UserMailListChecker();

        List<String> lines = Arrays.asList(
                "user1 -> xxx@ya.ru, lol@mail.ru",
                "user2 -> foo@gmail.com, ups@pisem.net",
                "user3 -> xyz@pisem.net, vasya@pupkin.com"
        );

        List<String> result = userMailListChecker.getUniqueUserWithMailsLine(lines);
        Assert.assertEquals(lines, result);
    }

    @Test
    public void processNormalMultiUserWithIncorrectLines() {
        UserMailListChecker userMailListChecker = new UserMailListChecker();

        List<String> lines = Arrays.asList(
                "user1",
                "user1 -> xxx@ya.ru, lol@mail.ru",
                "user2 -> foo@gmail.com, ups@pisem.net",
                "user2 -> ",
                "user3 -> asdasd",
                "user3 -> xyz@pisem.net, vasya@pupkin.com"
        );

        List<String> expected = Arrays.asList(
                "user1 -> xxx@ya.ru, lol@mail.ru",
                "user2 -> foo@gmail.com, ups@pisem.net",
                "user3 -> xyz@pisem.net, vasya@pupkin.com"
        );

        List<String> result = userMailListChecker.getUniqueUserWithMailsLine(lines);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void processMultiUserWithEqualMails() {
        UserMailListChecker userMailListChecker = new UserMailListChecker();

        List<String> lines = Arrays.asList(
                "user1 -> xxx@ya.ru, foo@gmail.com, lol@mail.ru",
                "user2 -> foo@gmail.com, ups@pisem.net",
                "user3 -> xyz@pisem.net, vasya@pupkin.com",
                "user4 -> ups@pisem.net, aaa@bbb.ru",
                "user5 -> xyz@pisem.net"
        );

        List<String> expected = Arrays.asList(
                "user1 -> xxx@ya.ru, foo@gmail.com, lol@mail.ru, ups@pisem.net, aaa@bbb.ru",
                "user3 -> xyz@pisem.net, vasya@pupkin.com"
        );

        List<String> result = userMailListChecker.getUniqueUserWithMailsLine(lines);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void processMultiUserWithEqualMailsSecondMail() {
        UserMailListChecker userMailListChecker = new UserMailListChecker();

        List<String> lines = Arrays.asList(
                "user1 -> xxx@ya.ru, foo@gmail.com, lol@mail.ru",
                "user2 -> ups@pisem.net, foo@gmail.com",
                "user3 -> xyz@pisem.net, vasya@pupkin.com",
                "user4 -> ups@pisem.net, aaa@bbb.ru",
                "user5 -> xyz@pisem.net"
        );

        List<String> expected = Arrays.asList(
                "user1 -> xxx@ya.ru, foo@gmail.com, lol@mail.ru, ups@pisem.net, aaa@bbb.ru",
                "user3 -> xyz@pisem.net, vasya@pupkin.com"
        );

        List<String> result = userMailListChecker.getUniqueUserWithMailsLine(lines);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void processNormalMultiUserWithMissedAndExtraWhitespaces() {
        UserMailListChecker userMailListChecker = new UserMailListChecker();

        List<String> lines = Arrays.asList(
                "user1 -> xxx@ya.ru,lol@mail.ru",
                "user2->foo@gmail.com,      ups@pisem.net",
                "user3 ->       xyz@pisem.net,        vasya@pupkin.com"
        );

        List<String> expected = Arrays.asList(
                "user1 -> xxx@ya.ru, lol@mail.ru",
                "user2 -> foo@gmail.com, ups@pisem.net",
                "user3 -> xyz@pisem.net, vasya@pupkin.com"
        );

        List<String> result = userMailListChecker.getUniqueUserWithMailsLine(lines);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void formatEmptyMailUserMapAsLine() {
        UserMailListChecker userMailListChecker = new UserMailListChecker();
        List<String> lines = userMailListChecker.formatMailUserMapAsLine(new LinkedHashMap<>());
        Assert.assertEquals(0, lines.size());
    }

    @Test
    public void formatSingleUserMailUserMapAsLine() {
        LinkedHashMap<String, UserMailListChecker.UserContainer> map = new LinkedHashMap<>();
        UserMailListChecker.UserContainer userContainer = new UserMailListChecker.UserContainer("user1");
        map.put("test1@example.com", userContainer);

        UserMailListChecker userMailListChecker = new UserMailListChecker();

        List<String> lines = userMailListChecker.formatMailUserMapAsLine(map);
        Assert.assertEquals(1, lines.size());
        Assert.assertEquals("user1 -> test1@example.com", lines.get(0));

        map.put("test2@example.com", userContainer);
        map.put("test3@example.com", userContainer);

        lines = userMailListChecker.formatMailUserMapAsLine(map);
        Assert.assertEquals(1, lines.size());
        Assert.assertEquals("user1 -> test1@example.com, test2@example.com, test3@example.com", lines.get(0));
    }

    @Test
    public void formatMultiUserMailUserMapAsLine() {
        LinkedHashMap<String, UserMailListChecker.UserContainer> map = new LinkedHashMap<>();
        UserMailListChecker.UserContainer userContainer1 = new UserMailListChecker.UserContainer("user1");
        UserMailListChecker.UserContainer userContainer2 = new UserMailListChecker.UserContainer("user2");


        map.put("test0@example.com", userContainer1);
        map.put("test2@example.com", userContainer2);
        map.put("test1@example.com", userContainer1);
        map.put("test3@example.com", userContainer1);

        UserMailListChecker userMailListChecker = new UserMailListChecker();

        List<String> lines = userMailListChecker.formatMailUserMapAsLine(map);
        Assert.assertEquals(2, lines.size());
        Assert.assertEquals("user1 -> test0@example.com, test1@example.com, test3@example.com", lines.get(0));
        Assert.assertEquals("user2 -> test2@example.com", lines.get(1));
    }
}