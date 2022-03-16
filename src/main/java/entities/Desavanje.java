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
@Entity(name = "desavanje")
public class Desavanje implements Serializable {
    
     @Id
    @Column(name = "id")
    private Integer id;
     
     @Column(name = "zavrseno")
    private Integer zavrseno;
     
    @Column(name = "korisnik")
    private String korisnik;
    
    @Column(name = "naziv")
    private String naziv;
    
    @Column(name = "opis")
    private String opis;
    
    @Column(name = "tip")
    private String tip;
    
    @Column(name = "datumOd")
    private Date datumOd;
    
    @Column(name = "datumDo")
    private Date datumDo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(String korisnik) {
        this.korisnik = korisnik;
    }

    public String getNaziv() {
        return naziv;
    }

    public Integer getZavrseno() {
        return zavrseno;
    }

    public void setZavrseno(Integer zavrseno) {
        this.zavrseno = zavrseno;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public Date getDatumOd() {
        return datumOd;
    }

    public void setDatumOd(Date datumOd) {
        this.datumOd = datumOd;
    }

    public Date getDatumDo() {
        return datumDo;
    }

    public void setDatumDo(Date datumDo) {
        this.datumDo = datumDo;
    }
    
    
    
    
    
}
