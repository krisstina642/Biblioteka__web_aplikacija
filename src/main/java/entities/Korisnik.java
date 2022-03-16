/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Kristina
 */
@Entity(name = "korisnik")
public class Korisnik implements Serializable{
    

    public Korisnik(Korisnik k) {
        this.username = k.username;
        this.lozinka = k.lozinka;
        this.ime = k.ime;
        this.prezime = k.prezime;
        this.tip = k.tip;
        this.potvrda = k.potvrda;
        this.datumrodjenja = new Date(k.datumrodjenja.getTime());
        this.grad = k.grad;
        this.drzava = k.drzava;
        this.email = k.email;
        this.slika = k.slika;
    }
     

     
     public Korisnik() { }
     
    @Id
    @Column(name = "username")
    private String username;
    
    @Column(name = "lozinka")
    private String lozinka;

    @Column(name = "ime")
    private String ime;
     
    @Column(name = "prezime")
    private String prezime;
    
    @Column(name = "tip")
    private String tip;
     
    @Column(name = "potvrda")
    private String potvrda;
       
    @Column(name = "datumrodjenja")
    private Date datumrodjenja;
        
    @Column(name = "grad")
    private String grad;
          
    @Column(name = "drzava")
    private String drzava;
    
    @Column(name = "email")
    private String email;
    
    @Column(name= "slika")
    private String slika;

    public String getSlika() {
        return slika;
    }

    public void setSlika(String slika) {
        this.slika = slika;
    }

    public String getPotvrda() {
        return potvrda;
    }

    public void setPotvrda(String potvrda) {
        this.potvrda = potvrda;
    }

    public Date getDatumrodjenja() {
        return datumrodjenja;
    }

    public void setDatumrodjenja(Date datumrodjenja) {
        this.datumrodjenja = datumrodjenja;
    }

    public String getGrad() {
        return grad;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }

    public String getDrzava() {
        return drzava;
    }

    public void setDrzava(String drzava) {
        this.drzava = drzava;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
}
