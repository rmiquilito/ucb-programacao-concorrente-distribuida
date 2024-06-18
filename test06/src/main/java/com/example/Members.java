package com.example;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Members {
    private List<Member> members;

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    // Método para atualizar o arquivo JSON a fim de corresponder a coleção atualizada
    public void refreshMembers() throws Exception {
        new ObjectMapper().writeValue(new File("src/main/resources/members.json"), Server.members);
    }

    public Member fetchMember(String name) {
        for (Member member : members) {
            if (member.getName().equals(name)) {
                return member;
            }
        }
        return null;
    }

    // Resgata o livro da lista de livros alugados que guardam os membros com base no nome do membro e no título do livro
    public Book fetchBook(String title, String name) {
        List<Book> books = new ArrayList<>();
        for (Member member : members) {
            if (member.getName().equals(name)) {
                books = member.getBooks();
                break;
            }
        }
        for (Book book : books) {
            if (title.equals(book.getTitle())) {
                return book;
            }
        }
        return null;
    }

    // Registra um membro com uma lista de aluguéis vazia
    public Member registerMember(String name) {
        try {
            String object = "{\"name\": \"" + name + "\", \"books\": []}";
            Member newMember = new ObjectMapper().readValue(object, Member.class);
            return newMember;
        } catch (Exception e) {
            return null;
        }
    }
}
