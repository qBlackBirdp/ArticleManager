package com.koreait;


import java.util.Scanner;


public class App {

    final Scanner sc;
    public App(Scanner sc) {
        this.sc = sc;
    }


    public void run() {

        System.out.println("==프로그램 시작==");

        MemberController memberController = new MemberController(sc);
        ArticleController articleController = new ArticleController(sc);

        ArticleController.makeTestData();

        MemberController.makeTestUserData();

        while (true) {
            System.out.print("명령어) ");
            String cmd = sc.nextLine().trim();

            if (cmd.length() == 0) {
                System.out.println("명령어를 입력하세요");
                continue;
            }
            if (cmd.equals("exit")) {
                break;
            }

            if (cmd.equals("member join")) {

                MemberController.doJoin();
            } else if (cmd.equals("member login")) {

                MemberController.doLogin();
            } else if (cmd.equals("article write")) {

                ArticleController.doWrite();

            } else if (cmd.startsWith("article list")) {

                ArticleController.showList(cmd);

            } else if (cmd.startsWith("article detail")) {

                ArticleController.showDetail();

            } else if (cmd.startsWith("article delete")) {

                ArticleController.doDelete(cmd);

            } else if (cmd.startsWith("article modify")) {

                ArticleController.doModify(cmd);

            } else {
                System.out.println("사용할 수 없는 명령어입니다");
            }

        }
        System.out.println("==프로그램 종료==");
        sc.close();

    }
}
