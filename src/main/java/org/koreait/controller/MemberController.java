package org.koreait.controller;

import org.koreait.util.Util;
import org.koreait.dto.Member;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MemberController extends Controller {

    static Scanner sc;
    static List<Member> members;

    static int lastMemberId = 3;
    static String cmd;

    static Member currentUser = null;

    public MemberController(Scanner sc) {
        this.sc = sc;
        members = new ArrayList<>();
    }

    public void doAction(String cmd, String actionMethodName) {
        this.cmd = cmd;

        switch (actionMethodName) {
            case "join":
                doJoin();
                break;
            case "login":
                doLogin();
                break;
            case "logout":
                doLogout();
                break;
            default:
                System.out.println("명령어 확인 (actionMethodName) 오류");
                break;
        }
    }

    private void doJoin() {
        System.out.println("==회원가입==");
        int id = lastMemberId + 1;
        String regDate = Util.getNow();
        String loginId = null;
        while (true) {
            System.out.print("로그인 아이디 : ");
            loginId = sc.nextLine().trim();
            if (isJoinableLoginId(loginId) == false) {
                System.out.println("이미 사용중이야");
                continue;
            }
            break;
        }
        String loginPw = null;
        while (true) {
            System.out.print("비밀번호 : ");
            loginPw = sc.nextLine();
            System.out.print("비밀번호 확인 : ");
            String loginPwConfirm = sc.nextLine();

            if (loginPw.equals(loginPwConfirm) == false) {
                System.out.println("비번 다시 확인해");
                continue;
            }
            break;
        }

        System.out.print("이름 : ");
        String name = sc.nextLine();

        Member member = new Member(id, regDate, loginId, loginPw, name);
        members.add(member);

        System.out.println(id + "번 회원이 가입되었습니다");
        lastMemberId++;
    }

    private void doLogin() {
        if (currentUser != null) {
            System.out.println("이미 로그인 되어있음.");
            return;
        }
        System.out.println("==로그인==");

            System.out.print("로그인 아이디 : ");
            String loginId = sc.nextLine().trim();
            System.out.print("로그인 비밀번호 : ");
            String loginPw = sc.nextLine().trim();

            Member member = findMemberByLoginId(loginId);
            if (member == null) {
                System.out.println("아이디 확인");
                return;
            } else if (!member.getLoginPw().equals(loginPw)) {
                System.out.println("비밀번호 틀림");
                return;
            }
            currentUser = member;

            System.out.printf("%s님 로그인 성공", member.getName());
    }

    private void doLogout() {
        if (currentUser != null) {
            System.out.println("로그아웃.");
            currentUser = null;
        } else {
            System.out.println("로그인 하지 않음.");
        }
    }

    private boolean isJoinableLoginId(String loginId) {
        for (Member member : members) {
            if (member.getLoginId().equals(loginId)) {
                return false;
            }
        }
        return true;
    }

    public static void makeTestUserData() {
        System.out.println("테스트 유저 데이터 생성");
        members.add(new Member(1, "2023-12-12 12:12:12", "test1", "test1", "내용1"));
        members.add(new Member(2, Util.getNow(), Util.getNow(), "제목2", "내용2"));
        members.add(new Member(3, Util.getNow(), Util.getNow(), "제목3", "내용3"));
    }

    private Member findMemberByLoginId(String loginId) {
        for (Member member : members) {
            if (member.getLoginId().equals(loginId)) {
                return member;
            }
        }
        return null;
    }
}

