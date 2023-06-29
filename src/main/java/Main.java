import java.util.*;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }

    public static int countChar(String str, char symbol) {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == symbol) {
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                int countRepeat = countChar(generateRoute("RLRFR", 100), 'R');
                synchronized (sizeToFreq) {
                    if (sizeToFreq.containsKey(countRepeat)) {
                        sizeToFreq.put(countRepeat, sizeToFreq.get(countRepeat) + 1);
                    } else {
                        sizeToFreq.putIfAbsent(countRepeat, 1);
                    }
                }
            }).start();
        }
        int maxValue = 0;
        int maxKey = 0;
        synchronized (sizeToFreq) {
            for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
                int key = entry.getKey();
                int value = entry.getValue();

                if (value > maxValue) {
                    maxValue = value;
                    maxKey = key;
                }
            }
        }
        System.out.println("Самое частое количество повторений " + maxKey + " встретилось " + maxValue + " раз)");
        System.out.println("Другие размеры:");
        synchronized (sizeToFreq) {
            for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
                if (entry.getKey() != maxKey) {
                    System.out.println("- " + entry.getKey() + " (" + entry.getValue() + " раз)");
                }
            }
        }
    }
}
