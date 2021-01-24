import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class CounterWithThread {

    static void countWords2(String pathOfFile) throws IOException {
        String book = Files.readString(Path.of(pathOfFile));

        String words[] = book.split("\\s+");

        System.out.println("Word Count: " + words.length);
    }

    static void countWords(String pathOfFile) throws IOException {
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

        System.out.println("Word Count : "+ wordCount);
    }

    static void paraCount(String pathOfFile) throws IOException {
        int paraCount = 0;

        FileReader fileReader = new FileReader(new File(pathOfFile));
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line;
        while((line = bufferedReader.readLine()) != null)
            if(line.equals(""))
                paraCount++;

        System.out.println("Para Count: "+ paraCount);
    }

    static void sentenceCount(String pathOfFile) throws IOException {
        int sentenceCount = 0;

        FileReader fileReader = new FileReader(new File(pathOfFile));
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line;
        while((line = bufferedReader.readLine()) != null){
            String [] sentence = line.split("[!?.:]+");
            sentenceCount += sentence.length;
        }
        System.out.println("sentence Count : "+ sentenceCount);
    }

    public static void main(String[] args) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        String path = "C:\\Users\\praba\\IdeaProjects\\AU_Multithreading_WordCount\\src\\resource\\Book.txt";

        Thread wordCountThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    countWords(path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        wordCountThread.start();


        Thread sentenceCountThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sentenceCount(path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        sentenceCountThread.start();


        Thread paraCountThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    paraCount(path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        paraCountThread.start();

        wordCountThread.join();
        sentenceCountThread.join();
        paraCountThread.join();

        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Total time in 4 Threads: "+ totalTime);
    }
}
