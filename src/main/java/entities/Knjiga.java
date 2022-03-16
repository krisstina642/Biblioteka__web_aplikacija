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
@Entity(name = "knjiga")
public class Knjiga implements Serializable {
    
    public Knjiga() { }
     
    @Id
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "naziv")
    private String naziv;
    
    @Column(name = "opis")
    private String opis;
    
    @Column(name = "status")
    private String status;
     
    @Column(name = "ocena")
    private double ocena;
    
    @Column(name = "datum")
    private Date datum;
 
    @Column(name= "slika")
    private String slika;

    public Knjiga(Knjiga knjiga) {
        this.datum=new Date(knjiga.datum.getTime());
        this.id=knjiga.id;
        this.naziv=knjiga.naziv;
        this.ocena=knjiga.ocena;
        this.opis=knjiga.opis;
        this.slika=knjiga.slika;
        this.status=knjiga.status;       
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
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

    public double getOcena() {
        return ocena;
    }

    public void setOcena(double ocena) {
        this.ocena = ocena;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }
   
    public String getSlika() {
        return slika;
    }

    public void setSlika(String slika) {
        this.slika = slika;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
       
}
