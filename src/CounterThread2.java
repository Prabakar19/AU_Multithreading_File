import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

class ThreadCount implements Callable {

    private String textBook;


    public ThreadCount(String textBook){
        this.textBook = textBook;
    }


    void countWords2(){
        String book = textBook;

        String words[] = book.split("\\s+");

    }

    void countWords() throws IOException {
        HashMap<String, Integer> frequencyCount = new HashMap<>();
        int wordCount = 0;

        BufferedReader bufferedReader = new BufferedReader(new StringReader(textBook));

        String line;
        while ((line = bufferedReader.readLine()) != null) {

            String[] linewords = line.split("\\s+");
            wordCount += linewords.length;

            for (String s : linewords) {
                if (frequencyCount.containsKey(s)) {
                    frequencyCount.put(s, frequencyCount.get(s) + 1);
                } else
                    frequencyCount.put(s, 1);
            }
        }

        for (Map.Entry<String, Integer> e : frequencyCount.entrySet())
            System.out.println(e.getKey() + "\t : " + e.getValue());
    }

    int paraCount() throws IOException {
        int paraCount = 0;

        BufferedReader bufferedReader = new BufferedReader(new StringReader(textBook));

        String line;
        while ((line = bufferedReader.readLine()) != null)
            if (line.equals(""))
                paraCount++;

        return paraCount;
    }

    int sentenceCount() throws IOException {
        int sentenceCount = 0;

        BufferedReader bufferedReader = new BufferedReader(new StringReader(textBook));

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] sentence = line.split("[!?.:]+");
            sentenceCount += sentence.length;
        }
        return sentenceCount;
    }

    @Override
    public List<Integer> call() throws IOException {

        List<Integer> counterList = new ArrayList<>();
        int wordCount = 0;
       BufferedReader bufferedReader = new BufferedReader(new StringReader(textBook));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String [] linewords = line.split("\\s+");
            wordCount += linewords.length;
        }

        countWords();
        counterList.add(wordCount);
        counterList.add(sentenceCount());
        counterList.add(paraCount());

        return counterList;
    }
}


public class CounterThread2 {

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        long startTime = System.currentTimeMillis();
        String path = "C:\\Users\\praba\\IdeaProjects\\AU_Multithreading_WordCount\\src\\resource\\Book.txt";

        String book = Files.readString(Path.of(path));
        int sizeOfBook = book.length();

        int partitionInd = sizeOfBook/10;
        int startInd = 0, endInd = partitionInd;

        FutureTask[] countWords = new FutureTask[10];
        for(int i=0;i<10;i++){
            String sub = book.substring(startInd, endInd);
            startInd = endInd;
            endInd = startInd + partitionInd;

            ThreadCount threadCount = new ThreadCount(sub);
            countWords[i] = new FutureTask(threadCount);
            Thread thread = new Thread(countWords[i]);
            thread.start();
        }

        int wordCount = 0;
        int sentenceCount = 0;
        int paraCount = 0;

        for(int i=0;i<10;i++){
            List<Integer> list = (List<Integer>) countWords[i].get();
            wordCount += list.get(0);
            sentenceCount += list.get(1);
            paraCount += list.get(2);
        }

        System.out.println("word: "+ wordCount);
        System.out.println("sentence: "+ sentenceCount);
        System.out.println("Para: "+ paraCount);

        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Total time in 10 Threads: "+ totalTime);
    }
}
