package com.example.tastifybackend.misc;

public class Util {
  public static String makeFirstLetterUppercase(String source){
    if(source == null || source.isBlank())
      return source;

    String trimmed = source.trim();
    if(Character.isUpperCase(trimmed.charAt(0)))
      return trimmed;

    return trimmed.substring(0,1).toUpperCase() + trimmed.substring(1);
  }

  public static boolean isNotMatchingId(String pathVariable, String inputIdVariable){
    return !pathVariable.equals(inputIdVariable);
  }
}
