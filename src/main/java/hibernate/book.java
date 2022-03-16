/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hibernate;

import entities.Autor;
import entities.Knjiga;
import entities.Citaonica;
import entities.Korisnik;
import entities.Zanrovi;
import entities.ZanroviKnjiga;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

/**
 *
 * @author Kristina
 */
public class book {

    
    public static ArrayList<Knjiga> pronadjiknjigu(String naziv, String autor, String zanr) {
        Session s = DBSession.openSession();
        
        ArrayList<Knjiga> k;
        
        if (zanr.equals("Svi zanrovi") != true) {
            
            Criteria c1 = s.createCriteria(ZanroviKnjiga.class).add(Restrictions.like("naziv", zanr));            
            ArrayList<ZanroviKnjiga> z = (ArrayList<ZanroviKnjiga>) c1.list();
            
            ArrayList<Integer> ai = new ArrayList<>();
            
            z.forEach((t) -> {
                ai.add(t.getIdknjige());                
            });
            if (ai.isEmpty()) {
                
                DBSession.closeSession(s);
                return null;
            }
            
           ArrayList<Autor> a;
           
                
                    Criteria c2 = s.createCriteria(Autor.class).add(Restrictions.like("ime", "%" + autor + "%"));
                    a = (ArrayList<Autor>) c2.list();
                    if (a==null || a.isEmpty()) {
                        DBSession.closeSession(s);
                        return null;
                    }                                           
            
            Criteria c = s.createCriteria(Knjiga.class).add(Restrictions.like("naziv", "%" + naziv + "%")).add(Restrictions.in("id", ai));
            
            ai.clear();
            a.forEach((t) -> {
                ai.add(t.getId());
            });
            if (ai.isEmpty()) {
                DBSession.closeSession(s);
                return null;
            }
            
            k = (ArrayList<Knjiga>) c.add(Restrictions.in("id", ai)).list();
            
        } else {
            Criteria c2 = s.createCriteria(Autor.class).add(Restrictions.like("ime", "%" + autor + "%"));            
            ArrayList<Autor> a = (ArrayList<Autor>) c2.list();
            
            ArrayList<Integer> ai = new ArrayList<>();
            
            a.forEach((t) -> {                
                ai.add(t.getId());
            });
            if (ai.isEmpty()) {
                DBSession.closeSession(s);
                return null;
            }
            
            Criteria c = s.createCriteria(Knjiga.class).add(Restrictions.like("naziv", "%" + naziv + "%"));
            k = (ArrayList<Knjiga>) c.add(Restrictions.in("id", ai)).list();            
        }
        
        DBSession.closeSession(s);
          
        return k;
    }
    
    public static ArrayList<Zanrovi> zanrovi() {
        
        Session s = DBSession.openSession();
        
        ArrayList<Zanrovi> z = (ArrayList<Zanrovi>) s.createCriteria(Zanrovi.class).list();
        
        DBSession.closeSession(s);
        
        return z;
    }
    
    public static ArrayList<String> zanrknjige(Knjiga k) {
        
        Session s = DBSession.openSession();       
        Query query = s.createQuery("select naziv from zanroviknjiga where idknjige=" + k.getId());       
        ArrayList<String> z = (ArrayList<String>) query.list();
        
        DBSession.closeSession(s);
        return z;
    }
    
    public static ArrayList<Citaonica> getkomentarisaneknjige(Korisnik k) {
        
        Session s = DBSession.openSession();    
        ArrayList<Citaonica> z = (ArrayList<Citaonica>) s.createCriteria(Citaonica.class).add(Restrictions.eq("korisnik", k.getUsername())).add(Restrictions.between("ocena", 1, 10)).list();     
        DBSession.closeSession(s);
        
        return z;
    }
    
    public static ArrayList<Autor> autori(Knjiga k) {
        
        Session s = DBSession.openSession();
        
        ArrayList<Autor> a = (ArrayList<Autor>) s.createCriteria(Autor.class).add(Restrictions.eq("idknjige", k.getId())).list();
        
        return a;
    }
    
      public static ArrayList<Autor> autori(int i) {
        
        Session s = DBSession.openSession();
        
        ArrayList<Autor> a = (ArrayList<Autor>) s.createCriteria(Autor.class).add(Restrictions.eq("idknjige", i)).list();
        
        return a;
    }
    
    public static ArrayList<Citaonica> komentariKnjige(Knjiga k) {
        
        Session s = DBSession.openSession();
        
        ArrayList<Citaonica> c = (ArrayList<Citaonica>) s.createCriteria(Citaonica.class).add(Restrictions.eq("idknjige", k.getId())).add(Restrictions.between("ocena", 1, 10)).list();
        DBSession.closeSession(s);
        return c;
    }
    
    public static ArrayList<Knjiga> procitaneKnjige(Korisnik k) {
        
        Session s = DBSession.openSession();
        
        Criteria cr = s.createCriteria(Citaonica.class).add(Restrictions.eq("korisnik", k.getUsername())).add(Restrictions.eq("status", "procitana"));
        ArrayList<Citaonica> c = (ArrayList<Citaonica>) cr.list();
        if (c == null) {
            DBSession.closeSession(s);
            return null;
        }
        
        ArrayList<Integer> ai = new ArrayList<>();
        c.forEach((t) -> {            
            ai.add(t.getIdknjige());
        });
        
        if (ai.isEmpty()) {
            DBSession.closeSession(s);
            return null;
        }
        
        ArrayList<Knjiga> lista = (ArrayList<Knjiga>) s.createCriteria(Knjiga.class).add(Restrictions.in("id", ai)).list();
        
        DBSession.closeSession(s);
        
        return lista;
    }
    
    public static ArrayList<Knjiga> trenutnoCitam(Korisnik k) {
        
        Session s = DBSession.openSession();
        
        ArrayList<Citaonica> c = (ArrayList<Citaonica>) s.createCriteria(Citaonica.class).add(Restrictions.eq("korisnik", k.getUsername())).add(Restrictions.eq("status", "cita")).list();
        
        ArrayList<Integer> ai = new ArrayList<>();
        c.forEach((t) -> {            
            ai.add(t.getIdknjige());
        });
        
        if (ai.isEmpty()) {
            DBSession.closeSession(s);
            return null;
        }
        
        ArrayList<Knjiga> m = (ArrayList<Knjiga>) s.createCriteria(Knjiga.class).add(Restrictions.in("id", ai)).list();
        
        DBSession.closeSession(s);
        return m;
    }
    
    public static ArrayList<Knjiga> uListiZaCitanje(Korisnik k) {
        
        Session s = DBSession.openSession();
        
        ArrayList<Citaonica> c = (ArrayList<Citaonica>) s.createCriteria(Citaonica.class).add(Restrictions.eq("korisnik", k.getUsername())).add(Restrictions.eq("status", "lista")).list();
        
        ArrayList<Integer> ai = new ArrayList<>();
        c.forEach((t) -> {            
            ai.add(t.getIdknjige());
        });
        
        if (ai.isEmpty()) {
            DBSession.closeSession(s);
            return null;
        }
        
        ArrayList<Knjiga> m = (ArrayList<Knjiga>) s.createCriteria(Knjiga.class).add(Restrictions.in("id", ai)).list();
        
        DBSession.closeSession(s);
        return m;
    }
    
    public static Citaonica nadjiCitaonicu(Korisnik korisnik, Knjiga knjiga) {
        
        Session s = DBSession.openSession();        
        Citaonica c = (Citaonica) s.createCriteria(Citaonica.class).add(Restrictions.eq("korisnik", korisnik.getUsername())).add(Restrictions.eq("idknjige", knjiga.getId())).uniqueResult();
        DBSession.closeSession(s);
        
        return c;        
        
    }
    
    public static void updateCitaonica(Citaonica c1, Citaonica c2) {
        
        Session s = DBSession.openSession();        
        
        s.delete(c1);
        s.save(c2);
        
        DBSession.closeSession(s);
        
    }
    
    public static void azurirajKnjigu(Knjiga k) {
        
        Session s = DBSession.openSession();        
        s.update(k);        
        DBSession.closeSession(s);
        
    }
    
    public static void dodajCitaonicu(Citaonica c1) {
        
        Session s = DBSession.openSession();        
        s.save(c1);        
        DBSession.closeSession(s);
        
    }
    
    public static void obrisiCitaonicu(Citaonica c1) {
        
        Session s = DBSession.openSession();        
        s.delete(c1);        
        DBSession.closeSession(s);
        
    }
    
    
    public static void napraviKnjigu(boolean b,Knjiga k, ArrayList<String> z, List<String> a){
        
        Session s=DBSession.openSession();
        
       int i=1;
       if(b){
       
           if((Knjiga)s.createSQLQuery("SELECT * FROM knjiga WHERE id = 1").setResultTransformer(Transformers.aliasToBean(Knjiga.class)).uniqueResult()!=null)
           { BigInteger min = (BigInteger)s.createSQLQuery("SELECT * FROM ( SELECT MIN(t1.id)+1 as unused FROM knjiga AS t1 WHERE NOT EXISTS (SELECT * FROM knjiga AS t2 WHERE t2.id = t1.id+1)) AS subquery").uniqueResult();
            i=min.intValue();
           }         
       }
       else  i=k.getId();
        
        k.setId(i);
        
        if(b) s.save(k);
        else s.update(k);
        
        DBSession.closeSession(s);
  
        Iterator<String> iter = z.iterator();    
        
        while (iter.hasNext()) {        
            s=DBSession.openSession();
            int minn=1;
           
           if((ZanroviKnjiga)s.createSQLQuery("SELECT * FROM zanroviknjiga WHERE id = 1").setResultTransformer(Transformers.aliasToBean(ZanroviKnjiga.class)).uniqueResult()!=null)
           { BigInteger min = (BigInteger)s.createSQLQuery("SELECT * FROM ( SELECT MIN(t1.id)+1 as unused FROM zanroviknjiga AS t1 WHERE NOT EXISTS (SELECT * FROM zanroviknjiga AS t2 WHERE t2.id = t1.id+1) UNION SELECT 1 FROM DUAL WHERE NOT EXISTS (SELECT * FROM zanroviknjiga WHERE id = 1)) AS subquery").uniqueResult();
             minn=min.intValue();
           }  
         
             System.out.println(minn);                     
            ZanroviKnjiga zanrovi = new ZanroviKnjiga();
            zanrovi.setIdknjige(i);
            zanrovi.setId(minn);
            zanrovi.setNaziv(iter.next());          
            s.save(zanrovi); 
            DBSession.closeSession(s);
         
        }        
       
        
        iter = a.iterator();        
        while (iter.hasNext()) {
           Session s2=DBSession.openSession();  
          
           int minn=1;
           
           if((Autor)s2.createSQLQuery("SELECT * FROM autor WHERE id = 1").setResultTransformer(Transformers.aliasToBean(Autor.class)).uniqueResult()!=null)
           { BigInteger min = (BigInteger)s2.createSQLQuery("SELECT * FROM ( SELECT MIN(t1.id)+1 as unused FROM autor AS t1 WHERE NOT EXISTS (SELECT * FROM autor AS t2 WHERE t2.id = t1.id+1) UNION SELECT 1 FROM DUAL WHERE NOT EXISTS (SELECT * FROM autor WHERE id = 1)) AS subquery").uniqueResult();
            minn=min.intValue();
           }          
           
            Autor aut = new Autor();
            aut.setId(minn);
            aut.setIdknjige(i);
            aut.setIme(iter.next());
            s2.save(aut);  
            DBSession.closeSession(s2);
            
        }
    }
    
    public static void dodajKnjigu(Knjiga k, ArrayList<String> z, List<String> a) {
                
        napraviKnjigu(true, k, z, a);
                
    }
    
    public static void izmeniKnjigu(Knjiga k, ArrayList<String> z, List<String> a) {
        
        izbrisiKnjigu(k);
        azurirajKnjigu(k);     
        napraviKnjigu(false, k, z, a);

    }
    
    public static void izbrisiKnjigu(Knjiga k) {
        
        Session s = DBSession.openSession();
        
        Query query = s.createQuery("DELETE FROM zanroviknjiga WHERE (idknjige = ?)");
        query.setInteger(0, k.getId());
        query.executeUpdate();
        
        query = s.createQuery("DELETE FROM autor WHERE (idknjige = ?)");
        query.setInteger(0, k.getId());
        query.executeUpdate();
          
        DBSession.closeSession(s);        
    }
    
    public static Knjiga nadjiknjiguId(int i) {
        Session s = DBSession.openSession();
        
        Knjiga c = (Knjiga) s.createCriteria(Knjiga.class).add(Restrictions.eq("id", i)).uniqueResult();
        
        DBSession.closeSession(s);
        
        return c;        
    }
    
     public static boolean obrisizanr(Zanrovi z) {
        Session s = DBSession.openSession();
        boolean b=false;        
        ArrayList<ZanroviKnjiga> zz= (ArrayList<ZanroviKnjiga>) s.createCriteria(ZanroviKnjiga.class).add(Restrictions.eq("naziv", z.getNaziv())).list();
        if (zz==null || zz.isEmpty()){
            s.delete(z);
            b=true;
        }
                DBSession.closeSession(s);
        
        return b;        
    }
     
     public static boolean dodajZanr(String z) {
        Session s = DBSession.openSession();
        boolean b=false;        
        Zanrovi zz=(Zanrovi) s.createCriteria(Zanrovi.class).add(Restrictions.eq("naziv", z)).uniqueResult();
        if (zz==null){
            zz=new Zanrovi();
            zz.setNaziv(z);
            s.save(zz);
            b=true;
        }
                DBSession.closeSession(s);
        
        return b;        
    }
    
}
