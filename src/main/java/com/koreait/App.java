package com.koreait;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class App {

    int lastArticleId;
    private final Scanner sc;
    static List<Article> articles;
    static List<Member> members = new ArrayList<>();


    public App(Scanner sc) {
        this.sc = sc;
        articles = new ArrayList<>();
        this.lastArticleId = 0;

    }


    public void run() {

        System.out.println("==프로그램 시작==");

        makeTestData();
        makeTestUserData();

        int lastArticleId = 3;
        int lastMemberId = 0;

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
            } else if (cmd.equals("member login")) {

                while (true) {
                    String loginId = sc.nextLine().trim();
                    System.out.print("로그인 아이디 : ");
                    String loginPw = sc.nextLine().trim();
                    System.out.print("로그인 비밀번호 : ");
                    if (isLoginIdExist(loginId)) {
                        if (loginPw.equals(loginPw) == false) {
                            System.out.println("비밀번호가 틀렸습니다.");
                            continue;
                        }
                        break;
                    }
                    System.out.print("로그인 성공");
                }
            } else if (cmd.equals("article write")) {
                System.out.println("==게시글 작성==");
                int id = lastArticleId + 1;
                String regDate = Util.getNow();
                String updateDate = regDate;
                System.out.print("제목 : ");
                String title = sc.nextLine();
                System.out.print("내용 : ");
                String body = sc.nextLine();

                Article article = new Article(id, regDate, updateDate, title, body);
                articles.add(article);

                System.out.println(id + "번 글이 생성되었습니다");
                lastArticleId++;
            } else if (cmd.startsWith("article list")) {
                System.out.println("==게시글 목록==");

                String[] cmdParts = cmd.split(" ");
                String keyword = cmdParts.length == 3 ? cmdParts[2] : "";

                List<Article> filteredArticles;
                if (keyword.isEmpty()) {
                    System.out.println("검색어 : " + keyword);
                    filteredArticles = articles;
                } else {
                    filteredArticles = new ArrayList<>();
                    for (Article article : articles) {
                        if (article.getTitle().contains(keyword)) {
                            filteredArticles.add(article);
                        }
                    }
                }

                if (filteredArticles.isEmpty()) {
                    System.out.println("아무것도 없어");
                } else {
                    System.out.println("  번호   /    날짜   /   제목   /   내용   ");
                    for (int i = filteredArticles.size() - 1; i >= 0; i--) {
                        Article article = filteredArticles.get(i);
                        if (Util.getNow().split(" ")[0].equals(article.getRegDate().split(" ")[0])) {
                            System.out.printf("  %d   /   %s      /   %s   /   %s  \n", article.getId(), article.getRegDate().split(" ")[1], article.getTitle(), article.getBody());
                        } else {
                            System.out.printf("  %d   /   %s      /   %s   /   %s  \n", article.getId(), article.getRegDate().split(" ")[0], article.getTitle(), article.getBody());
                        }
                    }
                }
            } else if (cmd.startsWith("article detail")) {
                System.out.println("==게시글 상세보기==");

                int id = Integer.parseInt(cmd.split(" ")[2]);

                Article foundArticle = getArticleById(id);

                if (foundArticle == null) {
                    System.out.println("해당 게시글은 없습니다");
                    continue;
                }
                System.out.println("번호 : " + foundArticle.getId());
                System.out.println("작성날짜 : " + foundArticle.getRegDate());
                System.out.println("수정날짜 : " + foundArticle.getUpdateDate());
                System.out.println("제목 : " + foundArticle.getTitle());
                System.out.println("내용 : " + foundArticle.getBody());
            } else if (cmd.startsWith("article delete")) {
                System.out.println("==게시글 삭제==");

                int id = Integer.parseInt(cmd.split(" ")[2]);

                Article foundArticle = getArticleById(id);

                if (foundArticle == null) {
                    System.out.println("해당 게시글은 없습니다");
                    continue;
                }
                articles.remove(foundArticle);
                System.out.println(id + "번 게시글이 삭제되었습니다");
            } else if (cmd.startsWith("article modify")) {
                System.out.println("==게시글 수정==");

                int id = Integer.parseInt(cmd.split(" ")[2]);

                Article foundArticle = getArticleById(id);

                if (foundArticle == null) {
                    System.out.println("해당 게시글은 없습니다");
                    continue;
                }
                System.out.println("기존 제목 : " + foundArticle.getTitle());
                System.out.println("기존 내용 : " + foundArticle.getBody());
                System.out.print("새 제목 : ");
                String newTitle = sc.nextLine();
                System.out.print("새 내용 : ");
                String newBody = sc.nextLine();

                foundArticle.setTitle(newTitle);
                foundArticle.setBody(newBody);
                foundArticle.setUpdateDate(Util.getNow());

                System.out.println(id + "번 게시글이 수정되었습니다");
            } else {
                System.out.println("사용할 수 없는 명령어입니다");
            }

        }
        System.out.println("==프로그램 종료==");
        sc.close();

    }

    private static Article getArticleById(int id) {
//        for (int i = 0; i < articles.size(); i++) {
//            Article article = articles.get(i);
//            if (article.getId() == id) {
//                return article;
//            }
//        }
        for (Article article : articles) {
            if (article.getId() == id) {
                return article;
            }
        }
        return null;
    }

    private static boolean isJoinableLoginId(String loginId) {
        for (Member member : members) {
            if (member.getLoginId().equals(loginId)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isLoginIdExist(String loginId) {
        for (Member member : members) {
            if (member.getLoginId().equals(loginId)) {
                return true;
            }
        }
        return false;
    }

    private static void makeTestData() {
        System.out.println("테스트 데이터 생성");
        articles.add(new Article(1, "2023-12-12 12:12:12", "2023-12-12 12:12:12", "제목1", "내용1"));
        articles.add(new Article(2, Util.getNow(), Util.getNow(), "제목2", "내용2"));
        articles.add(new Article(3, Util.getNow(), Util.getNow(), "제목3", "내용3"));
    }

    private static void makeTestUserData() {
        System.out.println("테스트 유저 데이터 생성");
        members.add(new Member(1, "2023-12-12 12:12:12", "test1", "test1", "내용1"));
        members.add(new Member(2, Util.getNow(), Util.getNow(), "제목2", "내용2"));
        members.add(new Member(3, Util.getNow(), Util.getNow(), "제목3", "내용3"));
    }
}
