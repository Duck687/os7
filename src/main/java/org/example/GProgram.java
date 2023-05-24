package org.example;// Клас GProgram
import static java.lang.Math.pow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GProgram {

  public static void main(String[] args) throws InterruptedException {
        int x = Integer.parseInt(args[0]);
        // Обчислюємо g(x)
        if(x == 10000){
          int i =0;
          while(true)
            i++;
        }
        boolean result = (pow(x, 0.5) % 2 == 0);
        System.out.println(result);

  }
}
