package com.koreait;

import com.sun.source.tree.WhileLoopTree;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {


        Scanner sc = new Scanner(System.in);

        new Member(sc).signUp();

        System.out.print("로그인 ID: ");
        String loginId = sc.nextLine();
        System.out.print("로그인 비밀번호: ");
        String loginPw = sc.nextLine();

        new Member(sc).logIn(loginId, loginPw);

        new App(sc).run();

        sc.close();


    }
}