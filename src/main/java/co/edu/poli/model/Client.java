package co.edu.poli.model;

import java.util.Objects;

public class Client {
    private String id;
    private String name;
    private String email;

    public Client(String id, String name, String email){
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
    public String getName() {
        return name;
    }
    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object one){
        if (this == one) return true;
        if (one == null || getClass() != one.getClass()) return false;
        Client client = (Client) one;
        return Objects.equals(id, client.id);
    }

    //@Override
    //public int hasCode(){
    //    return Objects.hash(id);
    //}

    @Override
    public String toString(){
        return String.format("""
                Cliente{
                id: %s
                nombre: %s
                email: %s
                """,id, name, email);
    }
}
