import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class Counter {

    static int countWords2(String pathOfFile) throws IOException {
        String book = Files.readString(Path.of(pathOfFile));

        String words[] = book.split("\\s+");

        return words.length;
    }


    static int countWords(String pathOfFile) throws IOException {
        HashMap<String, Integer> frequencyCount = new HashMap<>();
        int wordCount = 0;

        FileReader fileReader = new FileReader(new File(pathOfFile));
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line;
        while((line = bufferedReader.readLine()) != null){

            String [] linewords = line.split("\\s+");
            wordCount += linewords.length;

            for(String s:linewords){
                if(frequencyCount.containsKey(s)){
                    frequencyCount.put(s, frequencyCount.get(s)+1);
                }else
                    frequencyCount.put(s, 1);
            }
        }

        for (Map.Entry<String, Integer> e : frequencyCount.entrySet())
            System.out.println(e.getKey() + "\t : " + e.getValue());

        return wordCount;
    }

    static int paraCount(String pathOfFile) throws IOException {
        int paraCount = 0;

        FileReader fileReader = new FileReader(new File(pathOfFile));
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line;
        while((line = bufferedReader.readLine()) != null)
            if(line.equals(""))
                paraCount++;
        return paraCount;
    }

    static int sentenceCount(String pathOfFile) throws IOException {
        int sentenceCount = 0;

        FileReader fileReader = new FileReader(new File(pathOfFile));
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line;
        while((line = bufferedReader.readLine()) != null){
            String [] sentence = line.split("[!?.:]+");
            sentenceCount += sentence.length;
        }
        return sentenceCount;
    }


    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();

        String path = "C:\\Users\\praba\\IdeaProjects\\AU_Multithreading_WordCount\\src\\resource\\Book.txt";

        System.out.println("word: "+ countWords(path));
//        System.out.println("word: "+ countWords2(path));
        System.out.println("sentence: "+ sentenceCount(path));
        System.out.println("Para: "+ paraCount(path));

        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Total time in single Thread: "+ totalTime);
    }

}