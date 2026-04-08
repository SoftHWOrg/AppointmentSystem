package org.example.domain.entity;

public class Administrator extends User {

    public Administrator() {
        super();
        setRole("ADMIN");
    }

    public Administrator(int id, String name, String email, String password) {
        super(id, name, email, password, "ADMIN");
    }

    
    public boolean canManage(int userId) {
        return true;
    }
}
