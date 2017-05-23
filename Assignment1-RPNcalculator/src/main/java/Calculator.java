
import java.text.DecimalFormat;
import java.util.Scanner;
import org.apache.commons.lang3.StringUtils;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Jmach
 */
public class Calculator {

    static ArrayStack as = new ArrayStack();
    private Scanner scan = new Scanner(System.in);

    public void start() {

        System.out.println("Indtast: ");
        while (true) {

            String input = scan.next();
            System.out.printf("> ");

            String[] arr = input.split(" ");

            for (int i = 0; i < arr.length; i++) {

                String data = arr[i];

                if (!data.isEmpty()) {

                    if (isOperator(data)) {
                        as.push(data);
                    }

                    if (isNumeric(data)) {
                        as.push(data);
                    }

                    rpnCalc();
                }
            }

            System.out.println(as.toString());

        }

    }

    public void rpnCalc() {
        int top;
        int a;
        int b;

        if (as.getTop() >= 2) {
            if (isOperator(as.getData()[as.getTop() - 1].toString())) {
                if (isNumeric(as.getData()[as.getTop() - 3].toString()) && isNumeric(as.getData()[as.getTop() - 2].toString())) {
                    if (as.getData()[as.getTop() - 1].equals("+")) {
                        a = Integer.parseInt(as.getData()[as.getTop() - 3].toString());
                        b = Integer.parseInt(as.getData()[as.getTop() - 2].toString());

                        top = as.getTop() - 3;
                        as.setTop(top);
                        as.push(addition(a, b));
                    }

                    if (as.getData()[as.getTop() - 1].equals("-")) {
                        a = Integer.parseInt(as.getData()[as.getTop() - 3].toString());
                        b = Integer.parseInt(as.getData()[as.getTop() - 2].toString());

                        top = as.getTop() - 3;
                        as.setTop(top);
                        as.push(substraction(a, b));
                    }

                    if (as.getData()[as.getTop() - 1].equals("*")) {
                        a = Integer.parseInt(as.getData()[as.getTop() - 3].toString());
                        b = Integer.parseInt(as.getData()[as.getTop() - 2].toString());

                        top = as.getTop() - 3;
                        as.setTop(top);
                        as.push(multiply(a, b));
                    }

                    if (as.getData()[as.getTop() - 1].equals("/")) {
                        a = Integer.parseInt(as.getData()[as.getTop() - 3].toString());
                        b = Integer.parseInt(as.getData()[as.getTop() - 2].toString());
                        if (a == 0 || b == 0) {
                            as.setTop(as.getTop()-1);
                            as.push(0);
                        }else{
                        top = as.getTop() - 3;
                        as.setTop(top);
                        as.push(divide(a, b));
                        }
                    }
                }
            }

        }

    }

    private String addition(int a, int b) {
        int val = a + b;
        return Integer.toString(val);
    }

    private String substraction(int a, int b) {
        int val = a - b;
        return Integer.toString(val);
    }

    private String multiply(int a, int b) {
        int val = a * b;
        return Integer.toString(val);
    }

    private String divide(int a, int b) {
        int val = a / b;
        return Integer.toString(val);
    }

    boolean isOperator(String stringCheck) {
        return (stringCheck.equals("+")) || (stringCheck.equals("-"))
                || (stringCheck.equals("*")) || (stringCheck.equals("/"));
    }

    boolean isNumeric(String num) {
        return StringUtils.isNumeric(num);
    }

}
