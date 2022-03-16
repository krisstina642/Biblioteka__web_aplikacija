/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import entities.Knjiga;
import entities.Korisnik;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author kristina
 */
public class Util {
    public static HttpSession getSession() {
		return (HttpSession)
				FacesContext.
				getCurrentInstance().
				getExternalContext().
				getSession(false);
	}

	public static HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.
				getCurrentInstance().
				getExternalContext().getRequest();
	}      
        public static void setKorisnik(Korisnik k)
	{
            HttpSession session = Util.getSession();
            session.setAttribute("korisnik", k);
	}

        public static Korisnik getKorisnik()
	{
		HttpSession session = getSession();
		if ( session != null )
			return (Korisnik) session.getAttribute("korisnik");
		else
			return null;
	}
        
        public static void setKorisnik2(Korisnik k)
	{
            HttpSession session = Util.getSession();
            session.setAttribute("korisnik2", k);
	}
        
         public static Korisnik getKorisnik2()
	{
		HttpSession session = getSession();
		if ( session != null )
			return (Korisnik) session.getAttribute("korisnik2");
		else
			return null;
	}
        
         public static void setKnjiga(Knjiga k)
	{
            HttpSession session = Util.getSession();
            session.setAttribute("knjiga", k);
	}
         
          public static Knjiga getKnjiga()
	{
		HttpSession session = getSession();
		if ( session != null )
			return (Knjiga) session.getAttribute("knjiga");
		else
			return null;
	}
          
           public static void setPath(String s)
	{
		HttpSession session = getSession();
		 session.setAttribute("novaknjiga", s);
	}
}
