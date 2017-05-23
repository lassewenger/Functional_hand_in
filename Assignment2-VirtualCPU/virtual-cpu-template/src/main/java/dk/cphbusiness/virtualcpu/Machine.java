package dk.cphbusiness.virtualcpu;

import java.io.PrintStream;

public class Machine {

    private Cpu cpu = new Cpu();
    private Memory memory = new Memory();

    public void load(Program program) {
        int index = 0;
        for (int instr : program) {
            memory.set(index++, instr);
        }
    }

    public void tick() {
        int instr = memory.get(cpu.getIp());
        if (instr == 0b0000_0000) {
            // 0000 0000  NOP
            cpu.incIp();
            // cpu.setIp(cpu.getIp() + 1);
        } 
        
        else if (instr == 0b0000_0001) {
            System.out.println("0000 0001 ADD A B");
            cpu.setA(cpu.getA() + cpu.getB());
            cpu.incIp();
        } 
        
        else if (instr == 0b0000_0010) {
            System.out.println("0000 0010 MUL A B");
            cpu.setA(cpu.getA() * cpu.getB());
            cpu.incIp();
        } 
        
        else if (instr == 0b0000_0011) {
            System.out.println("0000 0011 DIV A B");
            if (cpu.getB() != 0) {
                cpu.setA(cpu.getA() / cpu.getB());
            }
            cpu.incIp();
        }
        
        else if (instr == 0b0000_0100) {
            System.out.println("0000 0100 ZERO");
            cpu.setFlag(cpu.getA() == 0);
                cpu.incIp();
        }
        
        else if (instr == 0b0000_0101) {
            System.out.println("0000 0101 NEG");
            cpu.setFlag(cpu.getA() < 0);
                cpu.incIp();
        }
        
        else if (instr == 0b0000_0110) {
            System.out.println("0000 0110 POS");
            cpu.setFlag(cpu.getA() > 0);
                cpu.incIp();
        }
        
        else if (instr == 0b0000_0111) {
            System.out.println("0000 0111 NZERO");
            cpu.setFlag(cpu.getA() != 0);
                cpu.incIp();
        }
        
        else if (instr == 0b0000_1000) {
            System.out.println("0000 1000 EQ");
            cpu.setFlag(cpu.getA() == cpu.getB());
                cpu.incIp();
        }
        
        else if (instr == 0b0000_1001) {
            System.out.println("0000 1001 LT");
            cpu.setFlag(cpu.getA() < cpu.getB());
                cpu.incIp();
        }
        
        else if (instr == 0b0000_1010) {
            System.out.println("0000 1010 GT");
            cpu.setFlag(cpu.getA() > cpu.getB());
                cpu.incIp();
        }
        
        else if (instr == 0b0000_1011) {
            System.out.println("0000 1011 NEQ");
            cpu.setFlag(cpu.getA() != cpu.getB());
                cpu.incIp();
        }
        
        else if (instr == 0b0000_1100) {
            System.out.println("0000 1100 ALWAYS");
            cpu.setFlag(true);
                cpu.incIp();
        }
        
        else if (instr == 0b0001_0100) {
            System.out.println("0001 0100 MOV A B");
            cpu.setB(cpu.getA());
            cpu.incIp();
        }
        
        else if (instr == 0b0001_0101) {
            System.out.println("0001 0101 MOV B A");
            cpu.setA(cpu.getB());
            cpu.incIp();
        }
        
        else if (instr == 0b0001_0110) {
            System.out.println("0001 0110 INC A");
            cpu.setA(cpu.getA() + 1);
            cpu.incIp();
        }
        
        else if (instr == 0b0001_0111) {
            System.out.println("0001 0111 DEC A");
            cpu.setA(cpu.getA() - 1);
            cpu.incIp();
        }
        
        else if (instr == 0b0000_1111) {
            System.out.println("HALT Execution");
        }
        
        else if ((instr & 0b1111_1110) == 0b0001_0000) {
            System.out.println("PUSH r");
            int r = (instr & 0b0000_0001);
            cpu.decSp();
            if( r==1 ){
                memory.set(cpu.getSp() , cpu.getB());
            } else {
                memory.set(cpu.getSp() , cpu.getA());
            }
            cpu.incIp();
        }
        
        else if ((instr & 0b1111_1110) == 0b0001_0010) {
            System.out.println("POP r");
            int r = (instr & 0b0000_0001);
            if(r == 1){
                cpu.setB(memory.get(cpu.getSp()));
                cpu.incSP();
            } else {
                cpu.setA(memory.get(cpu.getSp()));
                cpu.incSP();
            }
            cpu.incIp();
        }
        
        /*else if (instr == 0b0000_0111){
            System.out.println("NZERO");
            if(cpu.getA() != 0){
                cpu.setFlag(true);
            } else {
                cpu.setFlag(false);
            }
            cpu.incIp();
        }*/
        
        else if ((instr & 0b1100_0000) == 0b1100_0000){
            System.out.println("CALL");
            if(cpu.isFlag()){
                int a = (instr & 0b0011_1111);
                cpu.decSp();
                memory.set(cpu.getSp(), cpu.getIp());
                cpu.setIp(a);
            } else {
                cpu.incIp();
            }
        }
        /*else if (instr == 0b0001_0100) {
            System.out.println("0001 0100 MOV A B");
            cpu.setB(cpu.getA());
            cpu.setIp(cpu.getIp() + 1);
        }*/

        else if ((instr & 0b1111_0000) == 0b0010_0000) {
            System.out.println("0010 rooo MOV r o");
            // 0010 r ooo	MOV r o	   [SP + o] ← r; IP++

            // 0010 1 011 MOV B (=1) +3  [SP +3] // Move register B to memory position of SP with offset 3
            // 00101011 finding instruction
            //    and
            // 11110000
            // --------
            // 00100000
            // 00101011 finding offset
            //    and
            // 00000111
            // --------
            // 00000011 = 3
            // 00101011 finding register
            //    and
            // 00001000
            // --------
            // 00001000 = 8
            //    >> 3
            // 00000001 = 1
            int o = (instr & 0b0000_0111);
            int r = (instr & 0b0000_1000) >> 3;
            if (r == cpu.A) {
                memory.set(cpu.getSp() + o, cpu.getA());
            } else {
                memory.set(cpu.getSp() + o, cpu.getB());
            }
            cpu.setIp(cpu.getIp() + 1);
        }
        
        else if ((instr & 0b1111_0000) == 0b0011_0000) {
            System.out.println("0011 ooor MOV o r");
            
            int o = (instr & 0b0000_1110) >> 1 ;
            int r = (instr & 0b0000_0001);
            if (r == cpu.A) {
                cpu.setA(memory.get(cpu.getSp() + o));
            } else {
                cpu.setB(memory.get(cpu.getSp() + o));
            }
            cpu.setIp(cpu.getIp() + 1);
        }
        
        else if ((instr & 0b1100_0000) == 0b0100_0000) {
            System.out.println("MOV v r");
            
            int v = (instr & 0b0011_1110) >> 1;
            int r = (instr & 0b0000_0001);
            if (r == 1) {
                cpu.setB(v);
            } else {
                cpu.setA(v);
            }
            cpu.incIp();
        }
        
        else if ((instr & 0b1100_0000) == 0b1000_0000) {
            System.out.println("JMP");
            
            if (cpu.isFlag()) {
                int a = instr & 0b0011_1111;
                cpu.setIp(a);
            } else {
                cpu.incIp();
            }
        
        }
        
        else if ((instr & 0b1100_0000) == 0b1100_0000) {
            System.out.println("CALL");
            
            if (cpu.isFlag()) {
                int a = (instr & 0b0011_1111);
                cpu.decSp();
                memory.set(cpu.getSp(),cpu.getIp() );
                cpu.setIp(a);
            } else {
                cpu.incIp();
            }
        }
        
        else if((instr & 0b1111_1000) == 0b0001_1000){
            System.out.println("RTN");
            
            int o = (instr & 0b0000_0111);
            cpu.setIp(memory.get(cpu.getSp()));
            cpu.incSP();
            //memory.set(cpu.getSp() + 1, cpu.getIp());
            cpu.setSp(cpu.getSp()+o);
            
            cpu.incIp();
        }
        
    }
    
    public void print(PrintStream out) {
        memory.print(out);
        out.println("-------------");
        cpu.print(out);
    }

    public Cpu getCpu() {
        return cpu;
    }

}
