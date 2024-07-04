package utils;

public class SlugUtils {
    public static String parseString(String str) {
        if (str.startsWith("/")) {
            str = str.substring(1);
        }

        String[] words = str.split("-");
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            sb.append(Character.toUpperCase(word.charAt(0)))
                    .append(word.substring(1)).append(" ");
        }

        return sb.toString().trim();
    }
}
