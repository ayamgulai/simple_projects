package CRUD;

import java.io.*;
import java.time.Year;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Operation {
    public static void read() throws IOException {
        FileReader inputFile;
        BufferedReader bufferedFile;
        try{
            inputFile = new FileReader("database.txt");
            bufferedFile = new BufferedReader(inputFile);
        }catch (Exception e){
            System.err.println("database.txt not found");
            create();
            return;
        }

        String data = bufferedFile.readLine();
        int count = 0;
        System.out.println("| NO  | YEAR\t|\tWRITER\t\t\t\t\t|\tPUBLISHER\t\t\t\t|\tTITLE");
        System.out.println("============================================================================================");
        while(data != null){
            count++;
            StringTokenizer dataToken = new StringTokenizer(data,",");
            dataToken.nextToken();
            int n = dataToken.countTokens();
            for (int i = 0; i < n+1; i++){
                if (i == 0) System.out.printf("| %-2d ",count);
                else if (i == 1) System.out.printf("|\t%s\t", dataToken.nextToken());
                else if (i == 2 || i == 3) System.out.printf("|\t%-20s\t", dataToken.nextToken());
                else System.out.printf("|\t%s\t",dataToken.nextToken());

            }
            System.out.println();
            data = bufferedFile.readLine();
        }
        System.out.println("____________________________________________________________________________________________");

    }

    public static void create() throws IOException{
        FileWriter output = new FileWriter("database.txt",true);
        BufferedWriter buffOutput = new BufferedWriter(output);

        Scanner userInput = new Scanner(System.in);
        //faqihza_2019_3,2019,faqihza,gramedia,belajar Java
        String year,writer,pub,titleBook,entrycode;
        int entryNum;
        System.out.print("Enter book's writer : ");
        writer = userInput.nextLine();
        System.out.print("Enter publisher : ");
        pub = userInput.nextLine();
        System.out.print("Enter book's title : ");
        titleBook = userInput.nextLine();
        System.out.print("Enter publication year : ");
        year = userInput.nextLine();
        boolean invalidYear = true;
        while(invalidYear){
            try {
                Year.parse(year);
                invalidYear = false;
            }catch(Exception e){
                System.out.println("Please input the valid year");
                year = userInput.nextLine();
            }
        }
        String[] arrBook = {year, writer, pub, titleBook};
        boolean isExist = Utility.databaseCheck(arrBook,false,true);
        if (!isExist){
            System.out.println("Book's detail");
            System.out.println("Year : " + year);
            System.out.println("Writer : "+writer);
            System.out.println("Publisher : "+pub);
            System.out.println("Title : "+titleBook);

            boolean isCreate = Utility.getYorN("Are you sure to create this?");
            if (isCreate){
                entryNum = Utility.entranceNum(writer,year)+1;
                entrycode = writer.replace(" ","") +"_"+year+"_"+entryNum;
                buffOutput.write(entrycode+","+year+","+writer+","+pub+","+titleBook);
                buffOutput.newLine();
                buffOutput.flush();
            }
        } else {

            Utility.databaseCheck(arrBook,true,true);
        }

        output.close();
    }
    public static void search() throws IOException{
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the keywords ");
        String key = sc.nextLine();
        while(key.length() < 3){
            System.out.print("Please enter at least 3 characters :");
            key = sc.nextLine();
        }
        String[] arrKeyword = key.split(" ");
        Utility.databaseCheck(arrKeyword,true,false);


    }
    public static void update() throws IOException{
        FileReader readInput = new FileReader("database.txt");
        BufferedReader buffInput = new BufferedReader(readInput);

        read();

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter which book's index do you want to update? ");
        String userIndexStr = sc.next();
        int userIndex = -1;
        boolean invalidValue = true;
        while(invalidValue){
            try {
                userIndex = Integer.parseInt(userIndexStr);
                invalidValue = false;
            } catch (Exception e) {
                System.out.print("Please enter the valid value!");
                userIndexStr = sc.next();
            }
        }
        String temp = "";
        int index = 0;
        String data = buffInput.readLine();
        String[] profile = {"Entry Code","Year","Writer","Publisher","Title"};
        String[] updatedProfile = new String[profile.length-1];
        while(data != null){
            index++;
            if (index == userIndex){
                //faqihza_2019_3,2019,faqihza,gramedia,belajar Java
                StringTokenizer dataToken = new StringTokenizer(data,",");
                System.out.println("Book Data");
                for ( String bookProfile: profile ) {
                    System.out.println(bookProfile + " : " + dataToken.nextToken() );
                }

                dataToken = new StringTokenizer(data,",");
                sc = new Scanner(System.in);
                dataToken.nextToken();
                for (int i = 0; i < profile.length-1 ; i++){
                    String originalData = dataToken.nextToken();
                    boolean isUpdate = Utility.getYorN("Do you want to update the " + profile[i+1] + " ?");
                    if (isUpdate){
                        if (profile[i+1].equalsIgnoreCase("year")){
                            System.out.print("Enter the new year with (YYYY) format ");
                            updatedProfile[i] = sc.nextLine();
                            boolean invalidYear = true;
                            while(invalidYear){
                                try {
                                    Year.parse(updatedProfile[i]);
                                    invalidYear = false;
                                }catch(Exception e){
                                    System.out.println("Please input the valid year");
                                    updatedProfile[i] = sc.nextLine();
                                }
                            }
                        } else{
                            System.out.print("Enter the new "+profile[i+1]+" :");
                            updatedProfile[i] = sc.nextLine();
                        }
                    }
                    else updatedProfile[i] = originalData;
                }
                boolean isExist = Utility.databaseCheck(updatedProfile,false,false);
                if (isExist){
                    System.out.println("Your data is already in our database\nUpdate process cancelled");
                    Utility.databaseCheck(updatedProfile,true,true);

                }
                else{
                    dataToken = new StringTokenizer(data,",");
                    dataToken.nextToken();
                    for (int i = 0 ; i < updatedProfile.length ; i++){
                        System.out.println(profile[i+1]+" : " + dataToken.nextToken() + "  ---->  " + updatedProfile[i]);
                    }
                    boolean goUpdate = Utility.getYorN("Do you want to update the data into this?");
                    if (goUpdate){
                        //String[] profile = {"Entry Code","Year","Writer","Publisher","Title"};
                        int entryNum = Utility.entranceNum(updatedProfile[1],updatedProfile[0])+1;
                        String entrycode = updatedProfile[1].replace(" ","") +"_"+updatedProfile[0]+"_"+entryNum;
                        data = (entrycode+","+updatedProfile[0]+","+updatedProfile[1]+","+updatedProfile[2]+","+updatedProfile[3]);
                        System.out.println("Data updated successfully!");
                    }

                }

            }
            temp += data +"\n";
            data = buffInput.readLine();
        }
        FileWriter writeOutput = new FileWriter("database.txt");
        BufferedWriter buffOutput = new BufferedWriter(writeOutput);
        writeOutput.write(temp);
        writeOutput.flush();
        System.out.println("The book has been updated successfully!");
        readInput.close();
        buffInput.close();
        writeOutput.close();
        buffOutput.close();
    }
    public static void delete() throws IOException{
        FileReader readInput = new FileReader("database.txt");
        BufferedReader buffInput = new BufferedReader(readInput);

        read();

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter which book's index do you want to delete? ");
        String userIndexStr = sc.next();
        int userIndex = -1;
        boolean invalidValue = true;
        while(invalidValue){
            try {
                userIndex = Integer.parseInt(userIndexStr);
                invalidValue = false;
            } catch (Exception e) {
                System.out.print("Please enter the valid value!");
                userIndexStr = sc.next();
            }
        }
        String temp = "";
        int index = 0;
        String data = buffInput.readLine();
        while(data != null){
            index++;
            if (index == userIndex){
                data = buffInput.readLine();
            }
            if (data == null) break;
            temp += data +"\n";
            data = buffInput.readLine();
        }
        FileWriter writeOutput = new FileWriter("database.txt");
        BufferedWriter buffOutput = new BufferedWriter(writeOutput);
        writeOutput.write(temp);
        writeOutput.flush();
        System.out.println("The book has been deleted successfully!");
        readInput.close();
        buffInput.close();
        writeOutput.close();
        buffOutput.close();
    }
}
