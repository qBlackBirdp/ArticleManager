package com.koreait;

import com.sun.source.tree.WhileLoopTree;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Member member = new Member(sc);

        member.signUp();

        while (true) {
            System.out.print("로그인 ID: ");
            String loginId = sc.nextLine().trim();
            System.out.print("로그인 비밀번호: ");
            String loginPw = sc.nextLine().trim();

            if (member.logIn(loginId, loginPw)) {
                System.out.println("로그인 성공");
            } else {
                System.out.println("로그인에 실패했습니다.");
                continue;
            }
            break;
        }

        new App(sc).run();

        sc.close();


    }
}