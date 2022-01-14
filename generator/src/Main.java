import java.io.*;
import java.util.Random;

public class Main {

    public static final String src = "/Users/iverson3/Desktop/conceptnet-assertions-5.7.0.csv";
    public static final String partsDir="/Users/iverson3/Desktop/data/part";
    public static final String englishLines="/Users/iverson3/Desktop/data/english";
    public static final String wordlist="/Users/iverson3/Desktop/words.txt";

    public static final String[] template = {
            " is related to ", " is a synoym of ", " is a ", " is the opposite of "," is etymologically related to ",
            " is part of the ", " is made out of ", " is similar to ", "You are likely to find a "," is etymologically derived from ",
            " is used in the context of ", "Something you find in a ", " is a type of "," is capable of ",
            " is a way to ", " is for ", " is used in ", " forms of ", " is located at ", " will cause ",
            " is a antonym of ", " is located near ", " has a ", " has subevent of ", " has first subevent of ",
            " has last subevent of ", " has property of ", " is motivated by ", " desires "," receives action ",
            " is distinct from ", "is created by "," is derived from ", " is the symbol of "

    };

    public static void main(String[] args) throws IOException{
        //separateIntoParts();
        //selectEnglishLines();
        //mergeData();
        generateAntiCommon();

    }


    public static void separateIntoParts() throws IOException {
        int i;
        int count=1;
        BufferedReader bufferedReader=new BufferedReader(new FileReader(src));
        String line;
        while(true) {
            i=0;
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(partsDir+"/part" + count + ".csv"));

            while (i < 3000000&&(line = bufferedReader.readLine())!=null) {
                bufferedWriter.write(line + "\r\n");
                i++;
            }

            System.out.println(i);
            if(i<3000000)
                break;
            bufferedWriter.flush();
            bufferedWriter.close();
            System.out.println("Part "+count+" finished！");
            count++;
        }
        bufferedReader.close();
    }

    public static void selectEnglishLines() throws IOException{
        int count=1;
        int total=0;
        String line;
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(englishLines+"/english.csv"));
        while (count<13){
            BufferedReader bufferedReader = new BufferedReader(new FileReader(partsDir+"/part" + count + ".csv"));


            while((line = bufferedReader.readLine())!=null){
                if(line.indexOf("/c/en")>=0) {
                    bufferedWriter.write(line + "\r\n");
                    total++;
                }
            }
            System.out.println("part"+count+" finished!");
            count++;
            bufferedReader.close();

        }
        System.out.println("total:"+total);
        bufferedWriter.flush();
        bufferedWriter.close();

    }

    public static void mergeData() throws IOException{
        String line;
        BufferedReader bufferedReader1 = new BufferedReader(new FileReader("/Users/iverson3/Desktop/1-500.txt"));
        BufferedReader bufferedReader2 = new BufferedReader(new FileReader("/Users/iverson3/Desktop/1496-8000.txt"));
        BufferedReader bufferedReader3 = new BufferedReader(new FileReader("/Users/iverson3/Desktop/8001-13137.txt"));

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("/Users/iverson3/Desktop/merge.txt"));

        while((line = bufferedReader1.readLine())!=null){
            bufferedWriter.write(line+"\r\n");
        }

        System.out.println("========1=========");
        while((line = bufferedReader2.readLine())!=null){
            bufferedWriter.write(line+"\r\n");
        }

        System.out.println("========2=========");
        while((line = bufferedReader3.readLine())!=null){
            bufferedWriter.write(line+"\r\n");
        }

        System.out.println("========3=========");
    }



    public static void generateAntiCommon() throws IOException {
        String[] word_list = new String[13137];

        BufferedReader bufferedReader=new BufferedReader(new FileReader(wordlist));
        for(int i=0;i<13137;i++){
            word_list[i]= bufferedReader.readLine();
        }

        Random random_start = new Random();
        Random random_end = new Random();
        Random random_temp = new Random();

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("counter.txt"));

        int count=0;
        while(count<2000000) {


            int temp_index = random_temp.nextInt(14);
            int start_index = random_start.nextInt(13137);
            int end_index = random_end.nextInt(13137);

            String line;
            switch (temp_index) {
                case 7:
                    line = "You are likely to find a " + word_list[start_index] + " in " + word_list[end_index]+"\r\n";
                    break;
                case 9:
                    line = "Something you find in a " + word_list[start_index] + " is " + word_list[end_index]+"\r\n";
                    break;
                default:
                    line = word_list[start_index] + template[temp_index] + word_list[end_index]+"\r\n";
            }

            bufferedWriter.write(line);
            count++;
        }
        bufferedWriter.flush();
        bufferedWriter.close();
        bufferedReader.close();

    }
}
