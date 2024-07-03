package com.koreait;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Member {
    private final Scanner sc;

    private int id;
    private String loginId;
    private String loginPw;
    private String name;
    static List<Member> memberList = new ArrayList<>();

    public Member(Scanner sc) {
        this.sc = sc;
    }

    public void signUp() {

        System.out.println("회원가입 ");

        while (true) {
            System.out.print("id 입력) ");
            loginId = sc.nextLine().trim();

            if (isDuplicateLoginId(loginId)) {
                System.out.println("이미 사용 중인 ID입니다. 다른 ID를 입력해주세요.");
            } else {
                break;
            }
        }
        System.out.print("pw 입력) ");
        String loginPw = sc.nextLine().trim();

        this.loginId = loginId;
        this.loginPw = loginPw;

        memberList.add(this);

        System.out.println("회원가입이 완료되었습니다.");
    }


    private boolean isDuplicateLoginId(String loginId) {
        for (Member member : Member.memberList) {
            if (member.loginId.equals(loginId)) return true;
        }
        return false;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public void setLoginPw(String loginPw) {
        this.loginPw = loginPw;
    }

    public boolean logIn(String loginId, String loginPw) {
        if (this.loginId != null && this.loginPw != null) {
            if (this.loginId.equals(loginId) && this.loginPw.equals(loginPw)) {
                System.out.println("로그인 성공!");
                return true;
            } else {
                System.out.println("로그인 실패. ID 또는 비밀번호가 틀렸습니다.");
                return false;
            }
        } else {
            System.out.println("회원 정보가 없습니다. 먼저 회원가입을 해주세요.");
            return false;
        }
    }
}


