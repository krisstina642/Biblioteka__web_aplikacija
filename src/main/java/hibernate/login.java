/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hibernate;

import entities.Korisnik;
import entities.Pracenje;
import java.util.ArrayList;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Kristina
 */
public class login {

    public static Korisnik user(String uname) {
        Session s=DBSession.openSession();

        Criteria c = s.createCriteria(Korisnik.class);
        Korisnik k = (Korisnik) c.add(Restrictions.eq("username", uname)).uniqueResult();

        DBSession.closeSession(s);

        return k;
    }

    public static boolean checkEmail(String uname) {
        Session s=DBSession.openSession();

        Criteria c = s.createCriteria(Korisnik.class);
        ArrayList<Korisnik> k = (ArrayList<Korisnik>) c.add(Restrictions.eq("email", uname)).list();

        DBSession.closeSession(s);
    
        return k != null && !k.isEmpty();

    }

    public static boolean register(Korisnik k) {
        Session s=DBSession.openSession();

        s.save(k);

        DBSession.closeSession(s);

        return k != null;

    }

    public static void change(Korisnik k) {
        Session s=DBSession.openSession();

        s.update(k);

        DBSession.closeSession(s);

    }

    public static void obrisizahtevkorisnika(Korisnik k) {
        Session s=DBSession.openSession();
        s.delete(k);
        DBSession.closeSession(s);
    }
    
     public static Pracenje pracenje(String s1, String s2 ) {
        Session s=DBSession.openSession();

         Pracenje p=(Pracenje) s.createCriteria(Pracenje.class).add(Restrictions.eq("kojiprati", s1)).add(Restrictions.eq("kogaprati", s2)).uniqueResult();

        DBSession.closeSession(s);
        return (p);
    }
     
      public static void prati(String s1, String s2) {
        Session s=DBSession.openSession();
        
        int max= (int) s.createQuery("select max(id) from pracenje").setMaxResults(1).uniqueResult()+1;
             s.save(new Pracenje(max,s1,s2));

        DBSession.closeSession(s);
    }
      
      public static ArrayList<Pracenje> pratim(Korisnik k) {
          
        Session s=DBSession.openSession();
        ArrayList<Pracenje> a= (ArrayList<Pracenje>) s.createCriteria(Pracenje.class).add(Restrictions.eq("kojiprati", k.getUsername())).list();
        DBSession.closeSession(s);
        
        return a;
    }
      
        public static void otkazipracenje(String s1, String s2) {
            Pracenje p=pracenje(s1, s2);
        Session s=DBSession.openSession();    
             s.delete(p);
        DBSession.closeSession(s);
    }
    
     public static ArrayList<Korisnik> nadjiKorisnika(Korisnik k, Korisnik k2) {
        Session s=DBSession.openSession();
 
        ArrayList<Korisnik>ar;
        if (k2==null) ar=(ArrayList<Korisnik>) s.createCriteria(Korisnik.class).add(Restrictions.like("username", "%"+k.getUsername()+"%")).add(Restrictions.like("ime", "%"+k.getIme()+"%")).add(Restrictions.like("prezime", "%"+k.getPrezime()+"%")).add(Restrictions.like("email", "%"+k.getEmail()+"%")).add(Restrictions.like("potvrda", "potvrdjen")).list();
        else ar=(ArrayList<Korisnik>) s.createCriteria(Korisnik.class).add(Restrictions.like("username", "%"+k.getUsername()+"%")).add(Restrictions.like("ime", "%"+k.getIme()+"%")).add(Restrictions.like("prezime", "%"+k.getPrezime()+"%")).add(Restrictions.like("email", "%"+k.getEmail()+"%")).add(Restrictions.like("potvrda", "potvrdjen")).add(Restrictions.ne("username", k2.getUsername())).list();
        
        DBSession.closeSession(s);

        return ar;

    }

    public static ArrayList<Korisnik> ucitajzahteve() {
        Session s=DBSession.openSession();

        ArrayList<Korisnik> u = (ArrayList<Korisnik>) s.createCriteria(Korisnik.class).add(Restrictions.eq("potvrda", "zahtev")).list();

        DBSession.closeSession(s);

        return u;

    }
    
    public static ArrayList<Korisnik> followers(Korisnik k) {
        Session s=DBSession.openSession();
        
        ArrayList<Pracenje> p= (ArrayList<Pracenje>) s.createCriteria(Pracenje.class).add(Restrictions.eq("kogaprati", k.getUsername())).list();
        if (p==null || p.isEmpty()){
            DBSession.closeSession(s);
            return null;
        }
        
        ArrayList<String> ss= new ArrayList<>();
        
        p.forEach((t)->{
        ss.add(t.getKojiprati());});

        ArrayList<Korisnik> u = (ArrayList<Korisnik>) s.createCriteria(Korisnik.class).add(Restrictions.in("username", ss)).list();

        DBSession.closeSession(s);

        return u;

    }

}
