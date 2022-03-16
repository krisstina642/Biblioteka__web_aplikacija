/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Kristina
 */
@Entity(name = "zanrovi")
public class Zanrovi implements Serializable{

    @Id
    @Column(name = "naziv")
    private String naziv;

    public Zanrovi() {
    }
     
    public Zanrovi(Zanrovi z) {
        this.naziv = z.naziv;
    }

    public Zanrovi(String naziv) {
        this.naziv = naziv;
    }

    public Zanrovi(int i, String naziv) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

}
