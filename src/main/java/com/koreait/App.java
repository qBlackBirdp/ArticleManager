package com.koreait;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class App {

    int id;

    private final Scanner sc;
    private Article article;

    public App(Scanner sc) {
        this.sc = sc;
    }

    public void run() {

        int lastId = 0;
        id = lastId;
        List<Article> articles = new ArrayList<>();

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
                id = lastId + 1;

                System.out.print("제목 : ");
                String title = sc.nextLine().trim();
                System.out.print("내용 : ");
                String body = sc.nextLine().trim();

                Article article = new Article(id, title, body);

                articles.add(article);

                System.out.printf("%d번 글이 생성되었습니다.\n", id);

            } else if (cmd.equals("article list")) {
                System.out.println("==게시글 목록==");
                if (articles.size() == 0) {
                    System.out.println("등록된 article 없음.");
                }
                else {
                    System.out.println("번호  /   제목  /   내용  ");
                    for (int i = articles.size()-1; i>=0; i--) {

                        System.out.printf("%d   /   %s      /   %s     \n", article.getId(), article.getTitle(), article.getBody());
                    }
                }
            }
        }
    }

    static class Article {
        private int id;
        private String title;
        private String body;

        public Article(int id, String title, String body) {
            this.id = id;
            this.title = title;
            this.body = body;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        @Override
        public String toString() {
            return "article{" +
                    "id=" + id +
                    ", title='" + title + '\'' +
                    ", body='" + body + '\'' +
                    '}';
        }
    }


}
