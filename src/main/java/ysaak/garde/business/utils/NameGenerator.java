package ysaak.garde.business.utils;

import org.hibernate.validator.constraints.Range;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class NameGenerator {
  private final Random random = new Random();
  private List<String> vocals = new ArrayList<>();
  private List<String> startConsonants = new ArrayList<>();
  private List<String> endConsonants = new ArrayList<>();
  private List<String> nameInstructions = new ArrayList<>();

  public NameGenerator() {
    String demoVocals[] = { "a", "e", "i", "o", "u", "ei", "ai", "ou", "j", "ji", "y", "oi", "au", "oo" };

    String demoStartConsonants[] = { "b", "c", "d", "f", "g", "h", "k", "l", "m", "n", "p", "q", "r", "s", "t", "v",
        "w", "x", "z", "ch", "bl", "br", "fl", "gl", "gr", "kl", "pr", "st", "sh", "th" };

    String demoEndConsonants[] = { "b", "d", "f", "g", "h", "k", "l", "m", "n", "p", "r", "s", "t", "v", "w", "z", "ch",
        "gh", "nn", "st", "sh", "th", "tt", "ss", "pf", "nt" };

    String nameInstructions[] = { "vd", "cvdvd", "cvdvdvdvd", "cvd", "vdvd" };

    this.vocals.addAll(Arrays.asList(demoVocals));
    this.startConsonants.addAll(Arrays.asList(demoStartConsonants));
    this.endConsonants.addAll(Arrays.asList(demoEndConsonants));
    this.nameInstructions.addAll(Arrays.asList(nameInstructions));
  }

  public String getName() {
    return firstCharUppercase(getNameByInstructions(getRandomElementFrom(nameInstructions)));
  }

  private String getNameByInstructions(String nameInstructions) {
    String name = "";
    int l = nameInstructions.length();

    for (int i = 0; i < l; i++) {
      char x = nameInstructions.charAt(0);
      switch (x) {
        case 'v':
          name += getRandomElementFrom(vocals);
          break;
        case 'c':
          name += getRandomElementFrom(startConsonants);
          break;
        case 'd':
          name += getRandomElementFrom(endConsonants);
          break;
      }
      nameInstructions = nameInstructions.substring(1);
    }
    return name;
  }

  private String firstCharUppercase(String name) {
    return Character.toString(name.charAt(0)).toUpperCase() + name.substring(1);
  }

  private String getRandomElementFrom(List<String> v) {
    return v.get(random.nextInt(v.size()));
  }
}
