package org.koreait.controller;

import org.koreait.dto.Article;
import org.koreait.dto.Member;
import org.koreait.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.koreait.controller.MemberController.currentUser;

public class ArticleController extends Controller {
    static int lastArticleId;
    static Scanner sc;
    static List<Article> articles;
    private String cmd;

    public ArticleController(Scanner sc) {
        this.sc = sc;
        articles = new ArrayList<>();
        this.lastArticleId = 3;
    }

    public void doAction(String cmd, String actionMethodName) {
        this.cmd = cmd;

        switch (actionMethodName) {
            case "write":
                doWrite();
                break;
            case "list":
                showList();
                break;
            case "detail":
                showDetail();
                break;
            case "modify":
                doModify();
                break;
            case "delete":
                doDelete();
                break;
            default:
                System.out.println("명령어 확인 (actionMethodName) 오류");
                break;
        }
    }


    private void doWrite() {

        if (currentUser == null) {
            System.out.println("로그인 하지 않으면 글 못써.");
            return;
        }

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
    }

    private void showList() {

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
    }

    private void showDetail() {

        System.out.print("명령어) ");
        String cmd = sc.nextLine().trim();
        System.out.println("==게시글 상세보기==");

        int id = Integer.parseInt(cmd.split(" ")[2]);

        Article foundArticle = getArticleById(id);

        if (foundArticle == null) {
            System.out.println("해당 게시글은 없습니다");
            return;
        }
        System.out.println("번호 : " + foundArticle.getId());
        System.out.println("작성날짜 : " + foundArticle.getRegDate());
        System.out.println("수정날짜 : " + foundArticle.getUpdateDate());
        System.out.println("제목 : " + foundArticle.getTitle());
        System.out.println("내용 : " + foundArticle.getBody());
    }

    private void doDelete() {

        System.out.println("==게시글 삭제==");

        int id = Integer.parseInt(cmd.split(" ")[2]);

        Article foundArticle = getArticleById(id);

        if (foundArticle == null) {
            System.out.println("해당 게시글은 없습니다");
            return;
        }
        articles.remove(foundArticle);
        System.out.println(id + "번 게시글이 삭제되었습니다");
    }

    private void doModify() {
        System.out.println("==게시글 수정==");

        int id = Integer.parseInt(cmd.split(" ")[2]);

        Article foundArticle = getArticleById(id);

        if (foundArticle == null) {
            System.out.println("해당 게시글은 없습니다");
            return;
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
    }

    private Article getArticleById(int id) {
        for (Article article : articles) {
            if (article.getId() == id) {
                return article;
            }
        }
        return null;
    }

    public void makeTestData() {
        System.out.println("테스트 데이터 생성");
        articles.add(new Article(1, "2023-12-12 12:12:12", "2023-12-12 12:12:12", "제목1", "내용1"));
        articles.add(new Article(2, Util.getNow(), Util.getNow(), "제목2", "내용2"));
        articles.add(new Article(3, Util.getNow(), Util.getNow(), "제목3", "내용3"));
    }
}
