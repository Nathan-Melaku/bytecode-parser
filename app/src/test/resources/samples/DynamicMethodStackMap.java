public class DynamicMethodStackMap {
    private String buf = "what";

    public static void main(String[] args) {
        var test1 = "III";
        var test2 = "LVIII";
        var test3 = "MCMXCIV";
        var test4 = "MMMCMXCIX";

        System.out.println(test1 + " => " + romanToInt(test1));
        System.out.println(test2 + " => " + romanToInt(test2));
        System.out.println(test3 + " => " + romanToInt(test3));
        System.out.println(test4 + " => " + romanToInt(test4));

        try {
            var a1 = romanToInt(test1);
            var a2 = romanToInt(test2);
            var a3 = romanToInt(test3);
            var a4 = romanToInt(test4);

            assert a1 == 3;
            assert a2 == 58;
            assert a3 == 1994;
            assert a4 == 3999;

        } catch (IllegalArgumentException e) {
            System.out.println("Error Encounterd");
        }
    }

    public static int romanToInt(String s) throws IllegalArgumentException {
        var sum = 0;
        for (int i = 0; i < s.length(); i++) {
            sum += switch (s.charAt(i)) {
                case 'M' -> 1000;
                case 'D' -> 500;
                case 'L' -> 50;
                case 'V' -> 5;
                case 'C' -> {
                    if (i + 1 < s.length() && s.charAt(i + 1) == 'D') {
                        i++;
                        yield 400;
                    } else if (i + 1 < s.length() && s.charAt(i + 1) == 'M') {
                        i++;
                        yield 900;
                    } else {
                        yield 100;
                    }
                }
                case 'X' -> {
                    if (i + 1 < s.length() && s.charAt(i + 1) == 'L') {
                        i++;
                        yield 40;
                    } else if (i + 1 < s.length() && s.charAt(i + 1) == 'C') {
                        i++;
                        yield 90;
                    } else {
                        yield 10;
                    }
                }
                 case 'I'-> {
                    if (i + 1 < s.length() && s.charAt(i + 1) == 'V') {
                        i++;
                        yield 4;
                    } else if (i + 1 < s.length() && s.charAt(i + 1) == 'X') {
                        i++;
                        yield 9;
                    } else {
                        yield 1;
                    }
                 }
                 default -> throw new IllegalArgumentException();
             };
        }

        return sum;
    }
}
