package com.koreait;


import java.util.ArrayList;
import java.util.Scanner;

public class App {

    int id;

    private final Scanner sc;

    public App(Scanner sc) {
        this.sc = sc;
    }

    public void run() {
        int lastId = 1;
        id = lastId;

        System.out.println("== 프로그램 시작 ==");

        while (true) {
            System.out.print("명령어) ");
            String cmd = sc.nextLine().trim();

            if (cmd.equals("exit")) {
                System.out.println("== 프로그램 종료==");
                break;
            } else if (cmd.isEmpty()) {
                System.out.println("명령어를 입력해주세요");
            }

            if (cmd.equals("article write")) {
                System.out.print("제목 : ");
                String title = sc.nextLine().trim();
                System.out.print("내용 : ");
                String body = sc.nextLine().trim();


                System.out.printf("%d번 글이 생성되었습니다.\n", id);

                id = lastId + 1;
            }
        }
    }


}
