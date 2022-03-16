/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.Citaonica;
import entities.Knjiga;
import entities.Korisnik;
import entities.Zanrovi;
import hibernate.book;
import hibernate.login;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.FacesConfig;
import javax.inject.Named;
import org.primefaces.model.chart.PieChartModel;
import util.Util;

/**
 *
 * @author Kristina
 */
@Named
@SessionScoped
@FacesConfig(version = FacesConfig.Version.JSF_2_3)
public class profilBean implements Serializable {

    private Korisnik k;
    private List<Knjiga> procitane;
    private List<Knjiga> citam;
    private List<Knjiga> lista;
    private PieChartModel pieModel1;
    private ArrayList<Citaonica> komentarisaneknjige;
    private String from;
    private boolean pracenje;

    public boolean isPracenje() {
        return pracenje;
    }

    public void setPracenje(boolean pracenje) {
        this.pracenje = pracenje;
    }

   

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String nazad() {
        return from;
    }

    public String obradizahtev(boolean p) {
        if (p == true) {
            k.setPotvrda("potvrdjen");
            login.change(k);
            return null;
        }
        login.obrisizahtevkorisnika(k);
        return nazad();
    }

    public void promenitip(String s) {
        k.setTip(s);
        login.change(k);
    }

    public String poseta(String s, String from) {
        if (s.equals(Util.getKorisnik().getUsername())) {
            return "korisnik";
        }
        this.from = from;
        k = login.user(s);
        komentarisaneknjige = book.getkomentarisaneknjige(k);
        procitane = book.procitaneKnjige(k);
        citam = book.trenutnoCitam(k);
        lista = book.uListiZaCitanje(k);
     if(procitane!=null && !procitane.isEmpty()) createPieModel1();
     pracenje = login.pracenje(Util.getKorisnik().getUsername(),k.getUsername()) != null;
        return "profil";
    }
    
    public void prati(){
        pracenje=true;
        login.prati(Util.getKorisnik().getUsername(),k.getUsername());
    }
    public void otkazipracenje(){
        pracenje=false;
        login.otkazipracenje(Util.getKorisnik().getUsername(),k.getUsername());
    }

    public String posetaK(Korisnik s, String from) {
         if (s.equals(Util.getKorisnik().getUsername())) {
            return "korisnik";
        }
        this.from = from;
        k = s;
        komentarisaneknjige = book.getkomentarisaneknjige(k);
        procitane = book.procitaneKnjige(k);
        citam = book.trenutnoCitam(k);
        lista = book.uListiZaCitanje(k);
     if(procitane!=null && !procitane.isEmpty()) createPieModel1();
     pracenje = login.pracenje(Util.getKorisnik().getUsername(),k.getUsername()) != null;
        return "profil";
    }

    public PieChartModel getPieModel1() {
        return pieModel1;
    }

    public List<Zanrovi> svizanrovi() {
        return book.zanrovi();
    }

    public ArrayList<Citaonica> getKomentarisaneknjige() {
        return komentarisaneknjige;
    }

    public void setKomentarisaneknjige(ArrayList<Citaonica> komentarisaneknjige) {
        this.komentarisaneknjige = komentarisaneknjige;
    }

    private void createPieModel1() {
        pieModel1 = new PieChartModel();
        HashMap<String, Integer> pie = new HashMap<>();
        procitane.forEach((t) -> {
            ArrayList<String> z = book.zanrknjige(t);
            z.forEach((r) -> {
                if (pie.containsKey(r)) {
                    Integer i = pie.get(r) + 1;
                    pie.replace(r, i);
                } else {
                    pie.put(r, 1);
                }
            });
        });
        pie.entrySet().forEach((entry) -> {
            pieModel1.set(entry.getKey(), entry.getValue());
        });
        pieModel1.setTitle("Grafik procitanih zanrova");
        pieModel1.setLegendPosition("w");
        pieModel1.setShadow(false);
    }

    public Korisnik getK() {
        return k;
    }

    public void setK(Korisnik k) {
        this.k = k;
    }

    public List<Knjiga> getProcitane() {
        return procitane;
    }

    public void setProcitane(List<Knjiga> procitane) {
        this.procitane = procitane;
    }

    public List<Knjiga> getCitam() {
        return citam;
    }

    public void setCitam(List<Knjiga> citam) {
        this.citam = citam;
    }

    public List<Knjiga> getLista() {
        return lista;
    }

    public void setLista(List<Knjiga> lista) {
        this.lista = lista;
    }

}
