/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package constants;

/**
 *
 * @author macbook
 */
public class LmsConstants {

    public static final String VIEW_BASE_PATH = "/WEB-INF/view/";

    public static final String REDIS_IP = "localhost";

    public static class Regex {

        public static final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    }

    public static class Credentials {

        public static final String AUTHOR_EMAIL = "SWPG6";
        public static final String GMAIL_USERNAME = "swpg82289@gmail.com";
        public static final String GMAIL_PASSWORD = "jgm3nQcw0e5H9mv";
    }

    public static class Reid {
        public static final String NAMESPACE_ACCOUNT = "ACCOUNT";
        public static final String DEFAULT_REDIS_KEY="LMS.";

    }
}
