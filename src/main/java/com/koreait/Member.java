package com.koreait;

import java.util.Scanner;

public class Member {
    private final Scanner sc;

    private int id;
    private String loginId;
    private String loginPw;
    private String name;


    public Member(Scanner sc) {
        this.sc = sc;
    }
    public void signUp() {

        System.out.println("회원가입 ");
        System.out.print("id 입력) ");
        loginId = sc.nextLine().trim();
        System.out.print("pw 입력) ");
        loginPw = sc.nextLine().trim();
    }
    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }
    public void setLoginPw(String loginPw) {
        this.loginPw = loginPw;
    }
    public boolean logIn(String loginId, String loginPw) {
        if (this.loginId.equals(loginId) && this.loginPw.equals(loginPw)) {
            System.out.println("로그인 성공!");
            return true;
        } else {
            System.out.println("로그인 실패. ID 또는 비밀번호가 틀렸습니다.");
            return false;
        }
    }
}
