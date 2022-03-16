/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import hibernate.book;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Kristina
 */
@Entity(name = "citaonica")
public class Citaonica implements Serializable {

    
    @Id
    @Column(name = "idknjige")
    private Integer idknjige;
    
    @Column(name = "ocena")
    private Integer ocena;
    
    @Column(name = "max")
    private Integer max;
    
    @Column(name = "trenutno")
    private Integer trenutno;
    
    @Id
    @Column(name = "korisnik")
    private String korisnik;
    
     @Column(name = "komentar")
    private String komentar;
     
     @Column(name = "status")
    private String status;

    public Integer getIdknjige() {
        return idknjige;
    }

    public void setIdknjige(Integer idknjige) {
        this.idknjige = idknjige;
    }

    public Integer getOcena() {
        return ocena;
    }

    public void setOcena(Integer ocena) {
        this.ocena = ocena;
    }

    public String getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(String korisnik) {
        this.korisnik = korisnik;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public Integer getTrenutno() {
        return trenutno;
    }

    public void setTrenutno(Integer trenutno) {
        this.trenutno = trenutno;
    }
    
    public Knjiga knjiga(int i){
        return book.nadjiknjiguId(i);
    }
    
    public String nazivKnjige(int i){
        return book.nadjiknjiguId(i).getNaziv();
    }
    
    

   
    
}
