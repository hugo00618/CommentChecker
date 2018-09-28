public class Test {

    public static void main(String[] args) {
        String str = "class student {";

        String pattern = "(.*?)(;\\s*)(//.*)*";


//        System.out.println(new LexerJava().commentPart("String s = \"shshs//\"; // 123"));
System.out.println(str.matches(pattern));
        System.out.println(str.replaceAll(pattern, "$3"));
    }

}
