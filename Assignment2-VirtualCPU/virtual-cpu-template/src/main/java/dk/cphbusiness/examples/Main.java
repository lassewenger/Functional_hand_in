package dk.cphbusiness.examples;

public class Main {
  
  
  static void foo(int a, int b) {
    System.out.println("Foo called with "+a+" and "+b);
    }
  
  public static void main(String[] args) {
    System.out.println("Starting example");
    System.out.println("Hello world!");
    foo(7, 17);
    System.out.println("Printing stuff");
    System.out.println("Getting bored");
    foo(9, 20);
    System.out.println("At the end");
    }

  }
