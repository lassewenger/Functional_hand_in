package dk.cphbusiness.virtualcpu;

import java.util.Scanner;

public class Main
{

    public static void main(String[] args)
    {
        System.out.println("Welcome to the awesome CPU program");
        //Program program = new Program("01000010", "MOV A +5", "00001111", "MOV B +3");
        Program factorial = new Program("01001010", "00010000", "00001100", "11000110", "00010010","00001111","00110010",
            "00000111","10001100","01000010","00100001","00011000","00010000","00010111","00010000","00001100",
            "11000110","00010011","00010010","00000010","00100001","00011000");
        //Faculty program
        //Program program = new Program("01001010", "00010000", "00001100", "00010010", "00001111", "00110010", "00000111", "10001100", "01000010", "00100001", "00011000", "00010000", "00010111", "00010000", "00001100", "11000110", "00010011", "00010010", "00000010", "00100001", "00011000");
        //MOV 1 A
//PUSH A
//MOV 5 A
//PUSH A
//ALWAYS
//CALL #8
//POP A
//HALT
//MOV +1 A
//NZERO
//JMP #12
//RTN +1
//MOV +2 B
//MUL
//MOV A +2
//MOV +1 A
//DEC
//MOV A +1
//ALWAYS
//JMP #8
        Machine machine = new Machine();
        machine.load(factorial);
        machine.print(System.out);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Press enter to run next line");
        String input = scanner.nextLine();

        while (!input.equalsIgnoreCase("quit"))
        {
            machine.tick();

            if (machine.getCpu().isRunning() == false)
            {
                break;
            }

            machine.print(System.out);
            System.out.println("Press enter to run next line");
            input = scanner.nextLine();
        }

    }

}