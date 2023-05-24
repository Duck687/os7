package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
  public static Boolean fOutput1 = false;
  public static Boolean gOutput1 = false;
  public static void main(String[] args) {
    boolean res = true;
    while (res) {
      try {
        res = false;
        // Отримуємо вхідні дані від користувача
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Введіть значення x: ");
        String input = reader.readLine();
        int x = Integer.parseInt(input);
        if (x == -99) {
          return;
        }

        // Створюємо потік для обчислення f(x)
        Thread fThread = new Thread(() -> {
          try {
              long fStartTime = System.currentTimeMillis();
              // Створюємо процес для обчислення f(x)
              ProcessBuilder fProcessBuilder = new ProcessBuilder("java", "-jar",
                  "C:\\Users\\bogda\\untitled2\\out\\artifacts\\untitled2_jar\\untitled2.jar", x + "");
              Process fProcess = fProcessBuilder.start();

              // Отримуємо результат обчислення f(x) з stdout процесу f
              BufferedReader fOutputReader = new BufferedReader(
                  new InputStreamReader(fProcess.getInputStream()));
              String fOutput = fOutputReader.readLine();
              fOutput1 = Boolean.parseBoolean(fOutput);
              System.out.println(fOutput1);
              if(System.currentTimeMillis()-fStartTime > 10000l)
              {
                System.out.println("XUI");
              }
              // Завершуємо процес f
              fProcess.waitFor();


          } catch (IOException | InterruptedException e) {
            e.printStackTrace();
          }
        });

        // Створюємо потік для обчислення g(x)
        Thread gThread = new Thread(() -> {
          try {
              long gStartTime = System.currentTimeMillis();
              // Створюємо процес для обчислення g(x)
              ProcessBuilder gProcessBuilder = new ProcessBuilder("java", "-jar",
                  "C:\\Users\\bogda\\untitled2\\out\\artifacts\\untitled2_jar2\\untitled2.jar", x + "");
              Process gProcess = gProcessBuilder.start();

              // Отримуємо результат обчислення g(x) з stdout процесу g
              BufferedReader gOutputReader = new BufferedReader(
                  new InputStreamReader(gProcess.getInputStream()));
              String gOutput = gOutputReader.readLine();
              gOutput1 = Boolean.parseBoolean(gOutput);
            if(System.currentTimeMillis() - gStartTime > 10000l)
            {
              System.out.println("XUI");
            }
              System.out.println(gOutput1);

              gProcess.waitFor();

          } catch (IOException | InterruptedException e) {
            e.printStackTrace();
          }
        });

        // Запускаємо потоки для обчислення f та g
        fThread.start();
        gThread.start();

        // Очікуємо завершення обчислень f та g


        try {
          fThread.join(10000);
          gThread.join(10000); // Обмеження по часу виконання
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        if (fThread.isAlive() ) {
          if (check()) {
            continue;
          } else {
            fThread.interrupt();
            gThread.interrupt();
          }
        }

        if (gThread.isAlive() ) {
          if(check())
          {
            continue;
          }
          else
          {
            fThread.interrupt();
            gThread.interrupt();
          }
        }

        // Виконуємо операцію &&
        boolean result = (fOutput1) && (gOutput1);
        res = true;

        // Виводимо результат
        System.out.println("f(x) && g(x) = " + result);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
  public static boolean check() throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    System.out.println("Оберіть дію:");
    System.out.println("1) Продовжити обчислення");
    System.out.println("2) Припинити");
    String input = reader.readLine();
    int choice = Integer.parseInt(input);
    while (choice < 1 || choice > 2) {
      System.out.println("Некоректний вибір. Оберіть дію знову:");
      input = reader.readLine();
      choice = Integer.parseInt(input);
    }
    return choice == 1;
  }


}
