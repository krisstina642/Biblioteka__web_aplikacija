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
@Entity(name = "zanroviknjiga")
public class ZanroviKnjiga implements Serializable {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "idknjige")
    private Integer idknjige;

    @Column(name = "naziv")
    private String naziv;

    public ZanroviKnjiga(Integer id, Integer idknjige, String naziv) {
        this.id = id;
        this.idknjige = idknjige;
        this.naziv = naziv;
    }

    public ZanroviKnjiga() {
    }

    public Integer getIdknjige() {
        return idknjige;
    }

    public void setIdknjige(Integer idknjige) {
        this.idknjige = idknjige;
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

}
