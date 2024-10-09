package engine;

public class Test {
//    private String x;
//
//    public String getX() {
//        return x;
//    }
//
//    @Override
//    public String toString() {
//        if (x==null) return "123";
//        else return x;
//    }


    public static void main(String[] args) {

        boolean canLoadStopWords = Word.loadStopWords("stopwords.txt");

        Doc d1 = new Doc("anh anh anh");
        System.out.println("title: "+d1.getTitle()); // [anh anh anh]
        System.out.println("body: "+d1.getBody());  //[]

        System.out.println("---------");

        Doc d2 = new Doc("");
        System.out.println(d2.getTitle()); //[]
        System.out.println(d2.getBody()); // []

        System.out.println("---------");


        Word w1 = Word.createWord(("---abc'''"));
        System.out.println( "key: "+w1.isKeyword()); // key: true
        System.out.println("prefix: "+w1.getPrefix()); // ""
        System.out.println("text: " + w1.getText()); // ---abc'''

        System.out.println("---------");



        Word w2 = Word.createWord(("---'s"));
        System.out.println( "key: "+w2.isKeyword()); // key: false
        System.out.println("text: " + w2.getText()); // ---'s

        System.out.println("---------");



        Word w3 = Word.createWord(("dd'''"));
        System.out.println( "key: "+w3.isKeyword()); // key: true
        System.out.println("text: " + w3.getText()); // dd''''


        System.out.println("---------");

        Query q = new Query("key's key");
        System.out.println(q.getKeywords()); // [key's]



    }
}

