/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.Autor;
import entities.Desavanje;
import entities.Knjiga;
import entities.Zanrovi;
import hibernate.book;
import hibernate.event;
import java.io.Serializable;
import java.util.ArrayList;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.FacesConfig;
import javax.inject.Named;
import util.Util;

/**
 *
 * @author Kristina
 */
@Named
@SessionScoped
@FacesConfig(version = FacesConfig.Version.JSF_2_3)
public class gostBean implements Serializable {

    private String autor;
    private String naziv;
    private String zanr = "Svi zanrovi";
    private ArrayList<Zanrovi> zanrovi;
    private ArrayList<Knjiga> knjige;
    private ArrayList<Desavanje> desavanja;

    public gostBean() {      
        this.zanrovi = book.zanrovi();
        this.desavanja = event.getDesavanja();
    }
    
    public ArrayList<Desavanje> desavanjaRender(){
    return event.getDesavanja();
    }

    public void pretrazi() {
        this.knjige = book.pronadjiknjigu(naziv, autor, zanr);
        
    }

    public ArrayList<Autor> autorKnjige(Knjiga k) {
        return book.autori(k);
    }

    public String otvoriknjigu(Knjiga k) {
        Util.setKnjiga(k);
        return "knjiga";
    }

   public ArrayList<Knjiga> getKnjige() {
        return knjige;
    }

    public void setKnjige(ArrayList<Knjiga> knjige) {
        this.knjige = knjige;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getZanr() {
        return zanr;
    }

    public void setZanr(String zanr) {
        this.zanr = zanr;
    }

    public ArrayList<Zanrovi> getZanrovi() {
        return zanrovi;
    }

    public void setZanrovi(ArrayList<Zanrovi> zanrovi) {
        this.zanrovi = zanrovi;
    }

    public ArrayList<Desavanje> getDesavanja() {
        return desavanja;
    }

    public void setDesavanja(ArrayList<Desavanje> desavanja) {
        this.desavanja = desavanja;
    }

}
