package it.unisa.control;

public class sanitizer {
	   public static String sanitize(String input) {
	        if (input == null) {
	            return null;
	        }
	        input = input.replaceAll("[<>]", "");
	        input = input.replaceAll("&lt;", "");
	        input = input.replaceAll("&gt;", "");
	        return input;
	    }
}
