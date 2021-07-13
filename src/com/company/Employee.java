package com.company;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;



public class Employee {
//declare the hash map tp fit the data of the employee into a file
    static HashMap<String, List<Double>> Employee = new HashMap<>();
    public static void main(String[] args) throws IOException {
        double emp_no = 0, salary = 0;
        String emp_name = "";
        boolean is_true = true;
        // declaring variables inside console
        Scanner in = new Scanner(System.in);
        while(!emp_name.equals("ex"))
        {
            System.out.println("Enter the employee name, type ex to exit");
            emp_name = in.next();
            while(IntegerParse(emp_name))
            {
                System.out.println("Invalid input! Please enter employee name");
                emp_name = in.next();
            }
            if(emp_name.equals("ex"))
                break;
            String s = "";
                    System.out.println("Enter the employee number");
            s = in.next();
            while(!IntegerParse(s))
            {
                System.out.println("Invalid input! Please enter employee name");
                s = in.next();
            }
            emp_no = Double.parseDouble(s);
            is_true = true;
            while(is_true)
            {
                try{
                    System.out.println("Enter the employee salary");
                    salary = in.nextDouble();
                    if(salary <= 0)
                        throw new MyError("salary should be greater than zero");
                    is_true = false;
                    Employee.put(emp_name, new ArrayList<>(Arrays.asList(emp_no,salary)));
                }
                catch(InputMismatchException ex){
                    System.out.println("You've entered incorrect input!");
                    in.next();
                }
                catch(MyError ex){
                    System.out.println(ex.getMessage());
                }
            }
        }
        Display();
        SaveFile();
        TransferData();
        System.out.println("Data is saved in the text file");
    }
    public static void TransferData(){
        File f1 = new File("my_employee_lists.txt"); //creating a file object with relative pathname
        boolean exists = f1.exists(); //true input to the variable
        String input[]; // empty list
        if (exists)
        {
            try{
                Scanner scan = new Scanner(f1);
                while(scan.hasNextLine())
                {
                    String data = scan.nextLine();
                    input = data.split("//s");
                    double emp_no = Double.parseDouble(input[1]);
                    double salary = Double.parseDouble(input[2]);
                    Employee.put(input[0],new ArrayList<>(Arrays.asList(emp_no,salary)));
                }
            }
            catch(FileNotFoundException ex)
            {
                System.out.println("File does not exist");
            }
        }
    }
    public static void Display(){
        int count = Employee.size();
        System.out.println("there are currently " + count + " of employees in your records \n");
        for(Map.Entry<String,List<Double>> entry:Employee.entrySet()){
            System.out.println("Employee name " + "  \t" + entry.getKey());
            System.out.println("employee number " + "\t" + Employee.get(entry.getKey()).get(0));
            System.out.println("employee salary " + "\t" + Employee.get(entry.getKey()).get(1));
        }
    }
    public static void SaveFile() throws IOException {
        Path file = Paths.get("MyEmployeeList.txt");
        OutputStream output = new BufferedOutputStream(Files.newOutputStream(file,APPEND,CREATE));
        BufferedWriter Writer = new BufferedWriter(new OutputStreamWriter(output));
        String data;
        try {
            for(Map.Entry<String,List<Double>>entry:Employee.entrySet()){
                data = entry.getKey() + " " + Employee.get(entry.getKey()).get(0) + " " + Employee.get(entry.getKey()).get(1) + "\n";
                Writer.write(data);
            }
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        Writer.close();
    }
    static boolean IntegerParse(String s)
    {
        try{
            Integer.parseInt(s);
        }
        catch(NumberFormatException ex)
        {
            return false;
        }
        return true;
    }
}
