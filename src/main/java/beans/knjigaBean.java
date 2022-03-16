/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.Autor;
import entities.Knjiga;
import entities.Citaonica;
import entities.Korisnik;
import hibernate.book;
import hibernate.login;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.FacesConfig;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import util.SendEmail;
import util.Util;

/**
 *
 * @author Kristina
 */
@Named
@SessionScoped
@FacesConfig(version = FacesConfig.Version.JSF_2_3)
public class knjigaBean implements Serializable {

    private Knjiga knjiga;
    private ArrayList<Citaonica> komentari;
    private ArrayList<Autor> autori;
    private ArrayList<String> zanrovi;
    private Korisnik korisnik;
    private Citaonica citaonica;
    private Citaonica citaonica2;
    private String from;
    private boolean promenipodatke;
    private Date datum;
    private String izmenjeniautori;
  
    
    public void podacioknjizi() {
        korisnik = Util.getKorisnik();
        knjiga = Util.getKnjiga();
        komentari = book.komentariKnjige(knjiga);
        autori = book.autori(knjiga);
        if (korisnik != null) {
            citaonica = book.nadjiCitaonicu(korisnik, knjiga);
            citaonica2 = citaonica;
        }
        if (korisnik!=null && korisnik.getTip().equals("admin")) {
            datum = new java.util.Date(knjiga.getDatum().getTime());
            StringBuilder builder = new StringBuilder();
            Iterator<Autor> iterator = autori.iterator();
            while (iterator.hasNext()) {
                builder.append(iterator.next().getIme());
                builder.append(System.getProperty("line.separator"));
            }
            izmenjeniautori = builder.toString();
        }
    }

    public knjigaBean() {
    }

    public void izmenipodatkeoknjizi() {
        knjiga.setDatum(new java.sql.Date(datum.getTime()));
        String[] tokens = izmenjeniautori.split("\n");
        List<String> list = Arrays.asList(tokens);
        book.izmeniKnjigu(knjiga, zanrovi, list);
        promenipodatke = false;
    }

    public String getIzmenjeniautori() {
        return izmenjeniautori;
    }
    
    public ArrayList<String> getZanrovi() {
        return zanrovi;
    }

    public void setZanrovi(ArrayList<String> zanrovi) {
        this.zanrovi = zanrovi;
    }

    public void setIzmenjeniautori(String izmenjeniautori) {
        this.izmenjeniautori = izmenjeniautori;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public boolean isPromenipodatke() {
        return promenipodatke;
    }

    public void setPromenipodatke(boolean promeni) {
        this.promenipodatke = promeni;
    }

    public boolean renderdodajzacitanje() {
        return korisnik != null && citaonica == null;
    }

    public Knjiga getKnjiga() {
        return knjiga;
    }

    public void setKnjiga(Knjiga knjiga) {
        this.knjiga = knjiga;
    }

    public String imekorisnika(String s) {
        return login.user(s).getIme();
    }

    public String pogledajprofil(String s) {
        if (s.equals(citaonica.getKorisnik())) {
            return "korisnik";
        }
        Korisnik kk = nadjikorisnika(s);
        util.Util.setKorisnik2(nadjikorisnika(s));
        return "profil";
    }

    public Korisnik nadjikorisnika(String s) {
        return login.user(s);
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public String nazad() {
        //  if (korisnik==null) return "gost";
        // return "korisnik"; 
        return from;
    }

    public String otvoriknjigu(Knjiga k, String f) {
        util.Util.setKnjiga(k);
        from = f;
        zanrovi= book.zanrknjige(k);
        setPromenipodatke(false);
        return "knjiga";
    }

    public Citaonica getCitaonica() {
        return citaonica;
    }

    public void setCitaonica(Citaonica citaonica) {
        this.citaonica = citaonica;
    }

    public boolean renderButton() {
        return (korisnik != null && citaonica != null && (citaonica.getStatus().equals("cita") || citaonica.getStatus().equals("procitana")));
    }

    public int progress() {
        if (citaonica == null) {
            return 0;
        }
        if (citaonica.getTrenutno() <= 0 || citaonica.getMax() <= 0 || citaonica.getTrenutno() > citaonica.getMax()) {
            return 0;
        }
        return (int) ((double) citaonica.getTrenutno() / (double) citaonica.getMax() * 100);
    }

    public void dodajzacitanje() {
        Citaonica c = new Citaonica();
        c.setIdknjige(knjiga.getId());
        c.setOcena(0);
        c.setMax(100);
        c.setStatus("lista");
        c.setKorisnik(korisnik.getUsername());
        c.setTrenutno(0);
        book.dodajCitaonicu(c);
        citaonica = c;
    }

    public void izbacizacitanje() {
        book.obrisiCitaonicu(citaonica);
        citaonica = citaonica2 = null;

    }

    public void updateprogress() {
        if (citaonica.getTrenutno() <= 0 || citaonica.getMax() <= 0 || citaonica.getTrenutno() > citaonica.getMax()) {
            FacesContext.getCurrentInstance().addMessage("msgg", new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Greska!",
                    "Neispravni podaci!"));
            return;
        }
        if (progress() >= 50) {
            FacesContext.getCurrentInstance().addMessage("msgg", new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Bravo!",
                    "Procitaola si vise od pola knjige, sad mozes da ostavis komentar!"));
        }
        book.updateCitaonica(citaonica2, citaonica);
        citaonica2 = citaonica;
    }

    public void updatecomm() {
      if(citaonica.getOcena()==0){
           FacesContext.getCurrentInstance().addMessage("msgg", new FacesMessage(FacesMessage.SEVERITY_WARN,
                "Greska!",
                "Niste uneli ocenu!"));
           return;
      }
        book.updateCitaonica(citaonica2, citaonica);
        citaonica2 = citaonica;
       ArrayList<Korisnik> a= login.followers(korisnik);
    if (a!=null && !a.isEmpty()) a.forEach((t)->{
            SendEmail.komentarisanje(korisnik.getUsername(), t.getEmail(), knjiga.getNaziv());
        });
    FacesContext.getCurrentInstance().addMessage("msgg", new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Ok!",
                "Komentar je uspesno promenjen!"));
    }

    public void odobriKnjigu() {

        knjiga.setStatus("odobrena");
        book.azurirajKnjigu(knjiga);
    }

    public void setStatus(String s) {
        if (citaonica == null) {
            citaonica = new Citaonica();
            citaonica.setStatus(s);
            citaonica.setKorisnik(korisnik.getUsername());
            citaonica.setIdknjige(knjiga.getId());
            citaonica.setMax(100);
            citaonica.setTrenutno(100);
            citaonica.setOcena(0);
            book.dodajCitaonicu(citaonica);
            return;
        }
        citaonica.setStatus(s);
        if (s.equals("procitana")) {
            citaonica.setTrenutno(citaonica.getMax());
        }
        book.updateCitaonica(citaonica2, citaonica);
        citaonica2 = citaonica;
    }

    public List<Citaonica> getKomentari() {
        return komentari;
    }

    public List<Autor> getAutori() {
        return autori;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

}
