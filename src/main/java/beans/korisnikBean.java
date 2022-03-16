/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.Autor;
import entities.Citaonica;
import entities.Knjiga;
import entities.Korisnik;
import entities.Ucesce;
import entities.Zanrovi;
import hibernate.book;
import hibernate.event;
import hibernate.login;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.FacesConfig;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.model.chart.PieChartModel;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.shaded.commons.io.FilenameUtils;
import util.Util;

/**
 *
 * @author Kristina
 */
@Named
@SessionScoped
@FacesConfig(version = FacesConfig.Version.JSF_2_3)
public class korisnikBean implements Serializable {

    private Korisnik k;
    private String oldemail;
    private boolean promena = false;
    private Date date;
    private List<Knjiga> procitane;
    private List<Knjiga> citam;
    private List<Knjiga> lista;
    private UploadedFile image;
    private String novizanr;
    private List<Zanrovi> svizanrovi;
    private PieChartModel pieModel1;
    private ArrayList<Citaonica> komentarisaneknjige;
    private Korisnik pretraga;
    private ArrayList<Korisnik> trazenikorisnici;
    private HashMap<Ucesce,String> ucesca;
    private ArrayList<Ucesce> kljuceviucesca;
    
    public void obradiZahtev(Ucesce u, String s, boolean b){
        ucesca.remove(u,s);
        kljuceviucesca.remove(u);
        if(b){
            u.setZahtev("prihvacen");
            event.azurirajUcesce(u);
            return;}
        event.obrisiUcesce(u);   
    }
    
    public String getVrednost(Ucesce u) {
   return ucesca.get(u);
}
    

    public HashMap<Ucesce, String> getUcesca() {
        return ucesca;
    }

    public void setUcesca(HashMap<Ucesce, String> ucesca) {
        this.ucesca = ucesca;
    }

    public ArrayList<Korisnik> getTrazenikorisnici() {
        return trazenikorisnici;
    }

    public void setTrazenikorisnici(ArrayList<Korisnik> trazenikorisnici) {
        this.trazenikorisnici = trazenikorisnici;
    }

    public Korisnik getPretraga() {
        return pretraga;
    }

    public void setPretraga(Korisnik pretraga) {
        this.pretraga = pretraga;
    }

    public ArrayList<Korisnik> ucitajzahteve() {
        return login.ucitajzahteve();
    }

    public void dodajzanr() {
        if(book.dodajZanr(novizanr)){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Gotovo!",
                    "Zanr je dodat!"));
            svizanrovi = book.zanrovi();
           novizanr="";
            return;
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Greska!",
                    "Vec postoji!"));
         
    }
    
    public ArrayList<Autor> autori(int i){
        return book.autori(i);
    }

    public String getNovizanr() {
        return novizanr;
    }

    public void setNovizanr(String novizanr) {
        this.novizanr = novizanr;
    }

    public UploadedFile getImage() {
        return image;
    }

    public void setImage(UploadedFile image) {
        this.image = image;
    }

    public List<Zanrovi> getSvizanrovi() {
        return svizanrovi;
    }

    public PieChartModel getPieModel1() {
        return pieModel1;
    }

    public void setSvizanrovi(List<Zanrovi> svizanrovi) {
        this.svizanrovi = svizanrovi;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void initialize() {  
        if(k==null) init1();
        komentarisaneknjige = book.getkomentarisaneknjige(k);      
        procitane = book.procitaneKnjige(k);
        citam = book.trenutnoCitam(k);
        lista = book.uListiZaCitanje(k);
        createPieModel1();   
    }

    public ArrayList<Ucesce> getKljuceviucesca() {
        return kljuceviucesca;
    }

    public void setKljuceviucesca(ArrayList<Ucesce> kljuceviucesca) {
        this.kljuceviucesca = kljuceviucesca;
    }

    public ArrayList<Citaonica> getKomentarisaneknjige() {
        return komentarisaneknjige;
    }

    public void setKomentarisaneknjige(ArrayList<Citaonica> komentarisaneknjige) {
        this.komentarisaneknjige = komentarisaneknjige;
    }

    public korisnikBean() {
       init1();
    }
    
    private void init1() {
       k = Util.getKorisnik();
       
        if(k!=null) {
            this.date = new java.util.Date(k.getDatumrodjenja().getTime());  
        oldemail=k.getEmail();
        pretraga=new Korisnik();
        ucesca=event.ucescaKorisnikaSaImenimaUcesnika(k);
        kljuceviucesca=new ArrayList<>(ucesca.keySet());
        }
        svizanrovi = book.zanrovi();
    }
    
     public void nadjiKorisnika() {
        trazenikorisnici=login.nadjiKorisnika(pretraga,k);
        if (trazenikorisnici.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Hmmmmm!",
                    "Ne postoji korisnik sa trazenim parametrima!"));
        }
    }
     
    private void createPieModel1() {

        if (procitane != null && procitane.size() > 0) {
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
            for (Map.Entry<String, Integer> entry : pie.entrySet()) {

                pieModel1.set(entry.getKey(), entry.getValue());
            }
            pieModel1.setTitle("Grafik procitanih zanrova");
            pieModel1.setLegendPosition("w");
            pieModel1.setShadow(false);
            return;
        }
        pieModel1 = null;
    }

    public boolean isPromena() {
        return promena;
    }

    public void obrisiZanr(Zanrovi z) {
        if (book.obrisizanr(z)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Gotovo!",
                    "Zanr je obrisan!"));
            svizanrovi.remove(z);
            return;
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Nemoguce!",
                "Postoje knjige sa datim zanrom!"));

    }

    public void setPromena(boolean promena) {
        this.promena = promena;
    }

    public Korisnik getK() {
        return k;
    }

    public void setK(Korisnik k) {
        this.k = k;
    }

    public void savefile() throws IOException {    
        if (image == null || image.getSize() == 0) {
            return;
        }

        Path folder = Paths.get("C:\\Users\\Kristina\\Documents\\NetBeansProjects\\mavenproject3\\src\\main\\webapp\\resources\\images");
        System.out.println("path");
        String extension = FilenameUtils.getExtension(image.getFileName());
        Path file = Files.createTempFile(folder, k.getUsername(), "." + extension);

        try (InputStream input = image.getInputStream()) {
            Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);
            k.setSlika(file.toString().substring(92));
        } catch (IOException ex) {
            Logger.getLogger(registerBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Uploaded file successfully saved in " + file);

    }

    public void promenipodatke() throws IOException {
        if (!oldemail.equals(k.getEmail()) && login.checkEmail(k.getEmail()) == true) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Greska",
                    "Postoji drugi nalog sa datom e-mail adresom"));
            return;
        }
        k.setDatumrodjenja(new java.sql.Date(date.getTime()));
        savefile();
        login.change(k);
        util.Util.setKorisnik(k);
        oldemail=k.getEmail();

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Gotovo!",
                "Izmene su uspesno sacuvane!"));

        this.promena = false;

    }

    public void dozvolipromenu() {
        this.promena = true;
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
