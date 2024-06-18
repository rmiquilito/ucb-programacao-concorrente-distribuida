package com.example;

import java.io.File;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Library {
    private List<Book> books;
    private ReentrantLock lock = new ReentrantLock(); // Bloqueio para sincronização de concorrência

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    // Método para atualizar o arquivo JSON a fim de corresponder a coleção atualizada
    public void refreshLibrary() throws Exception {
        new ObjectMapper().writeValue(new File("src/main/resources/books.json"), Server.library);
    }

    // Método para buscar um livro com base no título do livro
    public Book fetchBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equals(title)) {
                return book;
            }
        }
        return null;
    }

    // Método para gravar no fluxo de saída os livros da coleção
    public void listBooks(ObjectOutputStream oos) throws Exception {
        if (lock.tryLock(5, TimeUnit.SECONDS)) { // Tenta adquirir o uso exclusivo da lógica crítica do método por 5 segundos a fim de não bloquear indefinidamente
            try {
                for (Book book : books) {
                    oos.writeObject(book.toString());
                    oos.flush();
                }
            } finally {
                lock.unlock();
            }
        }
    }

    // Método para gravar no fluxo de saída os membros da biblioteca da coleção
    public void listMembers(ObjectOutputStream oos) throws Exception {
        if (lock.tryLock(5, TimeUnit.SECONDS)) {
            try {
                if (Server.members.getMembers().isEmpty()) {
                    oos.writeObject("Empty.");
                }
                for (Member member : Server.members.getMembers()) {
                    oos.writeObject(member.toString());
                    oos.flush();
                }
            } finally {
                lock.unlock();
            }
        }
    }

    // Método para alguar um livro para um membro específico com base no nome do membro e no título do livro
    public Boolean rentBook(String title, String name) throws Exception {
        if (lock.tryLock(5, TimeUnit.SECONDS)) {
            try {
                // Resgata o membro com o nome especificado
                Member member = Server.members.fetchMember(name);
                if (member == null) {
                    member = Server.members.registerMember(name); // Registra o membro se não houver resgate
                    if (member == null) {
                        return false; // Retorna se houver exceção
                    } else {
                        Server.members.getMembers().add(member); // Atualiza a coleção se não houver exceção
                    }
                }

                // Atualiza as coleções se resgatar o livro com o título especificado e se houver disponibilidade
                Book book = fetchBook(title);
                if (book != null ? book.getAmount() > 0 : false) {
                    book.setAmount(book.getAmount() - 1);

                    ObjectMapper objectMapper = new ObjectMapper();
                    String object = objectMapper.writeValueAsString(book);
                    Book newCopy = objectMapper.readValue(object, Book.class); // Cria uma cópia do objeto do livro a fim de adequá-lo às particularidades da lista que guardam os membros
                    newCopy.setAmount(1);
                    Book copy = Server.members.fetchBook(title, name);

                    if (copy == null) {
                        member.getBooks().add(newCopy);
                    } else {
                        copy.setAmount(copy.getAmount() + 1);
                    }

                    refreshLibrary();
                    Server.members.refreshMembers();
                    return true;
                }
            } finally {
                lock.unlock();
            }
        }
        return false;
    }

    // Método para registrar um livro com base no objeto a ser traduzido em livro
    public Boolean registerBook(String object) throws Exception {
        if (lock.tryLock(5, TimeUnit.SECONDS)) {
            // Registra um novo livro se o objeto for válido
            try {
                Book newBook;
                try {
                    newBook = new ObjectMapper().readValue(object, Book.class);
                } catch (Exception e) {
                    return false;
                }

                // Se o registro do novo livro já existir na coleção, soma as quantidades do registro existente e do novo registro
                Book book = fetchBook(newBook.getTitle());
                if (book == null) {
                    books.add(newBook);
                } else {
                    book.setAmount(book.getAmount() + newBook.getAmount());
                }
                refreshLibrary();
            } finally {
                lock.unlock();
            }
        }
        return true;
    }

    // Método para devolver um livro de um membro específico com base no nome do membro e no título do livro
    public Boolean returnBook(String title, String name) throws Exception {
        if (lock.tryLock(5, TimeUnit.SECONDS)) {
            try {
                // Resgata o membro com o nome especificado
                Member member = Server.members.fetchMember(name);
                if (member == null) {
                    member = Server.members.registerMember(name); // Registra o membro se não houver resgate
                    if (member != null) {
                        Server.members.getMembers().add(member); // Atualiza a coleção se não houver exceção
                    }
                    return false;
                }

                // Resgata o livro da lista que guarda o membro com base no título especificado
                Book copy = Server.members.fetchBook(title, name);
                if (copy == null) {
                    return false; // Retorna se o livo não existir na lista
                } else {
                    // À essa altura, o livro existe na lista pois só se é adicionado caso haja aluguel
                    Book book = fetchBook(title);
                    copy.setAmount(copy.getAmount() - 1);
                    if (copy.getAmount() == 0) {
                        member.getBooks().remove(copy);
                    }
                    book.setAmount(book.getAmount() + 1);

                    refreshLibrary();
                    Server.members.refreshMembers();
                }
            } finally {
                lock.unlock();
            }
        }
        return true;
    }
}
