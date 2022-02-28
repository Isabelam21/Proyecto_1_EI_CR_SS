package grafos;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Roman Chacín
 */
public class NameUtil {
    private static NameUtil instance;

    /**
     * @author Roman Chacín
     * @return instance
     */
    public static NameUtil getInstance() {
        if (instance == null) {
            instance = new NameUtil();
        }
        return instance;
    }

    private List<Character> characters = new ArrayList<>();

    private String lastName = "";

    /**
     * @author Roman Chacín
     */
    public NameUtil() {
        initCharacters();
    }

    /**
     * @author Roman Chacín
     */
    private void initCharacters() {
        characters.add('a');
        characters.add('b');
        characters.add('c');
        characters.add('d');
        characters.add('e');
        characters.add('f');
        characters.add('g');
        characters.add('h');
        characters.add('i');
        characters.add('j');
        characters.add('k');
        characters.add('l');
        characters.add('m');
        characters.add('n');
        characters.add('o');
        characters.add('p');
        characters.add('q');
        characters.add('r');
        characters.add('s');
        characters.add('t');
        characters.add('u');
        characters.add('v');
        characters.add('w');
        characters.add('x');
        characters.add('y');
        characters.add('z');
    }

    /**
     * @author Roman Chacín
     * @return deliverAndUpdateName(ch)
     */
    public String getName() {
        if (lastName.isEmpty()) {
            return deliverAndUpdateName('a');
        }
        char ch = getNextChar();
        if (ch == 'a') {
            lastName += ch;
        }
        return deliverAndUpdateName(ch);
    }

    /**
     * @author Roman Chacín
     * @return characters.get(index + 1)
     */
    private char getNextChar() {
        char ch = lastName.charAt(lastName.length() - 1);
        if (ch == 'z') return 'a';
        int index = characters.indexOf(ch);
        return characters.get(index + 1);
    }

    /**
     * @author Roman Chacín
     * @param ch
     * @return lastName
     */
    private String deliverAndUpdateName(char ch) {
        if (this.lastName.isEmpty()) {
            lastName = String.valueOf(ch);
        } else {
            lastName = lastName.substring(0, lastName.length() - 1) + ch;
        }
        return lastName;
    }
}
