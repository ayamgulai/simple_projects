package CRUD;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Utility {
    static int entranceNum(String writer, String year) throws IOException {
        int temp = 0;
        FileReader input = new FileReader("database.txt");
        BufferedReader buffInput = new BufferedReader(input);

        String data = buffInput.readLine();
        Scanner sc;
        String enter;
        while(data != null){
            sc = new Scanner(data);
            sc.useDelimiter(",");
            enter = sc.next();
            sc = new Scanner(enter);
            sc.useDelimiter("_");
            writer = writer.replace(" ","");

            if(writer.equalsIgnoreCase(sc.next())&&year.equalsIgnoreCase(sc.next())){
                temp = sc.nextInt();
            }
            data = buffInput.readLine();
        }
        input.close();
        return temp;
    }


    static boolean databaseCheck(String[] arrKeyword, boolean isDisplay,boolean isExact) throws IOException{

        FileReader inputFile;
        BufferedReader bufferedFile;
        try{
            inputFile = new FileReader("database.txt");
            bufferedFile = new BufferedReader(inputFile);
        }catch (Exception e){
            System.err.println("database.txt not found");
            return false;
        }
        String data = bufferedFile.readLine();
        boolean isOneExist = false;
        int count = 0;
        if (isDisplay) {
            System.out.println("| NO  | YEAR\t|\tWRITER\t\t\t\t\t|\tPUBLISHER\t\t\t\t|\tTITLE");
            System.out.println("============================================================================================");
        }
        while(data != null){
            isOneExist = true;

            if (isExact){
                String[] arrData = data.split(",");
                for (int i = 0; i < 4;i++){
                    isOneExist = isOneExist && arrData[i+1].equalsIgnoreCase(arrKeyword[i]);
                }
                if (isOneExist) break;
            }
            else {
                for (String s : arrKeyword) {
                    isOneExist = isOneExist && data.toLowerCase().contains(s.toLowerCase());
                }
            }
            if(isOneExist){
                count++;
                if (isDisplay){
                    StringTokenizer dataToken = new StringTokenizer(data, ",");
                    dataToken.nextToken();
                    int n = dataToken.countTokens();
                    for (int i = 0; i < n + 1; i++) {
                        if (i == 0) System.out.printf("|\t%-1d ", count);
                        else if (i == 1) System.out.printf("|\t%s\t", dataToken.nextToken());
                        else if (i == 2 || i == 3) System.out.printf("|\t%-20s\t", dataToken.nextToken());
                        else System.out.printf("|\t%s\t", dataToken.nextToken());

                    }
                    System.out.println();
                }
            }
            data = bufferedFile.readLine();
        }
        if (isDisplay) System.out.println("============================================================================================");
        boolean existNum = count > 0;
        return existNum;
    }

    public static void title(String message){
        int i = 0;
        while(i < message.length()){
            System.out.print("=");
            i++;
        }
        System.out.println("\n"+message);
        i = 0;
        while(i < message.length()){
            System.out.print("=");
            i++;
        }
        System.out.println();
    }
    public static boolean getYorN(String message){
        Scanner userInput = new Scanner(System.in);
        System.out.print(message+" (y/n) \n");
        String userChoice = userInput.next();
        while(!(userChoice.equalsIgnoreCase("y")|| userChoice.equalsIgnoreCase("n"))){
            System.out.print("Choose between (y/n) ");
            userChoice = userInput.next();
        }
        return (userChoice.equalsIgnoreCase("y"));
    }
    public static void clearScreen(){
        try {
            if (System.getProperty("os.name").contains("Windows")){
                new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033\143");
            }
        } catch (Exception ex){
            System.err.println("can't clear screen");
        }
    }
}
