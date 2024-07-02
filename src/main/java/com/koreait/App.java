package com.koreait;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class App {

    int id;
    int lastArticleId;
    private final Scanner sc;
    List<Article> articles;
    private Article foundArticle;
    private Article article;

    public App(Scanner sc) {
        this.sc = sc;
        this.articles = new ArrayList<>();
        this.lastArticleId = 0;
    }

    public void run() {

        id = lastArticleId;
        //List<Article> articles = new ArrayList<>();

        System.out.println("== 프로그램 시작 ==");

        makeTestData();

        //printArticles();

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

                doSplit(cmd);

                System.out.println("번호 : " + foundArticle.getId());
                System.out.println("작성날짜 : " + foundArticle.getRegDate());
                System.out.println("수정날짜 : " + foundArticle.getUpdateDate());
                System.out.println("제목 : " + foundArticle.getTitle());
                System.out.println("내용 : " + foundArticle.getBody());

            } else if (cmd.startsWith("article delete")) {
                System.out.println("==게시글 삭제==");

                doSplit(cmd);

                articles.remove(foundArticle);

                System.out.println(id + "번 게시글이 삭제되었습니다");
            } else if (cmd.startsWith("article modify")) {
                System.out.println("==게시글 수정==");

                doSplit(cmd);

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
    private void makeTestData() {
        System.out.println("==테스트데이터==");
        for (int i = 1; i < 4; i++) {
            articles.add(new Article (++lastArticleId, Util.getNow(), Util.getNow(),"제목 " + i, "내용 " + i));
        }

    }

    private Article doSplit(String cmd) {
        int id = Integer.parseInt(cmd.split(" ")[2]);

        for (Article article : articles) {
            if (article.getId() == id) {
                foundArticle = article;
                break;
            }
        }

        if (foundArticle == null) {
            System.out.println("해당 게시글은 없습니다");
        }
        return null;
    }

}
