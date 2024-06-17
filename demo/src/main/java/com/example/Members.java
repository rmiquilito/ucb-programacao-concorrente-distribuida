package com.example;

import java.io.File;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Members {
    private List<Member> members;

    public synchronized List<Member> getMembers() {
        return members;
    }

    public synchronized void setMembers(List<Member> members) {
        this.members = members;
    }

    public static void refreshMembers() throws Exception {
        new ObjectMapper().writeValue(new File("src/main/resources/members.json"), Server.members);
    }
}
