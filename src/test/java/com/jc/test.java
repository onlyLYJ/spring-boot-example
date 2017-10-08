package com.jc;

import lombok.Data;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Calendar;
import java.util.Date;

/**
 * test
 * Create by onlyLYJ on 2017/9/28
 **/

public class test {
    @Test
    public void testToString() {
        Point a = new Point(1, 2);
        Point b = new Point(2, 3);
        System.out.println("a = " + a);
        System.out.println("b = " + b);
        System.out.println(a.equals(b));
    }

    @Test
    public void testDay() {
        Date endTime = DateUtils.ceiling(new Date(), Calendar.DATE);
        System.out.println(endTime);

    }


    @Test
    public void testPass() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String pass = passwordEncoder.encode("123");
        System.out.println(pass);
    }

    @Data
    class Point {
        private int x;
        private int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "true";
        }
    }

}
