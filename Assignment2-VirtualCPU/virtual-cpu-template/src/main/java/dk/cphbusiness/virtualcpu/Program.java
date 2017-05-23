package dk.cphbusiness.virtualcpu;

import java.util.Iterator;

public class Program implements Iterable<Integer> {
  private String[] lines;
  
  public Program(String... lines) {
    this.lines = lines;
    }
  
  public int get(int index) {
    String line = lines[index];
    if (line.charAt(0) == '0' || line.charAt(0) == '1') {
      return Integer.parseInt(line, 2);
      }
    else if (line.startsWith("MOV ")) {
      String[] parts = line.split(" ");
      int r = parts[1].equals("B") ? 1 : 0;
      int o = Integer.parseInt(parts[2]);
      
      return 0b0010_0000 | (r << 3) | o; 
      }
    else throw new UnsupportedOperationException("Don't know "+line);
    }

  @Override
  public Iterator<Integer> iterator() {
    return new ProgramIterator();
    }
  
  private class ProgramIterator implements Iterator<Integer> {
    private int current = 0;

    
    @Override
    public boolean hasNext() {
      return current < lines.length;
      }

    @Override
    public Integer next() {
      return get(current++);
      }
    
    }
  
  

  }
