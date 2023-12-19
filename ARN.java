import java.util.AbstractCollection;
import java.util.Comparator;
import java.util.Collection;
import java.awt.Color;

import java.util.Iterator;
import java.util.NoSuchElementException;
/**
 * @author Lazaar El Mahdi
 * @version 2023
 * */

public class ARN<T> extends AbstractCollection<T>{
	public Noeud racine;
	private int taille;
	private Comparator<? super T> cmp;
	// Noeud vide = la sentinelle.
	private Noeud noeudVide;
	public class Noeud{
		T cle;
		Noeud gauche;
		Noeud droit;
		Noeud pere;
		Color couleur;
		//Constructeur sans parametre
		public Noeud() {
			this.gauche = null;
			this.droit = null;
			this.pere = null;
			this.cle = null;
			this.couleur = Color.BLACK;
		}
		//Constructeur avec un seul parametre
		/*
		 * @param c
		 * 			La cle
		 * */
		public Noeud(T c) {
			cle = c;
			couleur = Color.RED;
			droit = null;
			gauche = null;
			pere = null;
		}
		/**
		 * Renvoie le noeud contenant la clé minimale du sous-arbre enraciné
		 * dans ce noeud
		 * 
		 * @return le noeud contenant la clé minimale du sous-arbre enraciné
		 *         dans ce noeud
		 */
	     public Noeud minimum() {
	        Noeud n = this;
	        while (n.gauche != noeudVide) {
	           n = n.gauche;
	        }
	        return n;
	     }
		/**
		* Renvoie le successeur de ce noeud
		* 
		* @return le noeud contenant la clé qui suit la clé de ce noeud dans
		*         l'ordre des clés, null si c'es le noeud contenant la plus
		*         grande clé
		*/
	     Noeud suivant() {
			Noeud n = this;
			if(n.droit != noeudVide) {
				return n.droit.minimum();
			} else {
				Noeud e = n.pere;
				while( e != noeudVide && n == e.droit) {
					n = e;
					e = e.pere;
				}
				return e;
			}
	     }
	}
	/*
	 * @return boolean 
	 * Si c est un noeud vide elle retourne vrai sinon elle retourne faux.
	 * */
	public boolean isNoeudVide() {
	    return this.racine == this.noeudVide;
	}
	/**
	 * Recherche une clé. Cette méthode peut être utilisée par
	 * {@link #contains(Object)} et {@link #remove(Object)}
	 * 
	 * @param o
	 *            la clé à chercher
	 * @return le noeud qui contient la clé ou null si la clé n'est pas trouvée.
	 */
	public Noeud rechercher(Object c) {
		Noeud e = racine;
		while(e != noeudVide) {
			if(c == e.cle) {
				return e;
			} else if(cmp.compare((T) c, e.cle) > 0) {
				e = e.gauche;
			} else {
				e = e.droit;
			}
		}
		return e;
	}
	// Constructeur sans parametre
	public ARN() {
		taille = 0;
		noeudVide = new Noeud();
		racine = noeudVide;
		cmp = new Comparator<T>() {
			public int compare(T n1, T n2) {
				return ((Comparable<T>) n1).compareTo(n2);
			}
		};
	}
	/**
	 * Crée un arbre vide. Les éléments sont comparés selon l'ordre imposé par
	 * le comparateur
	 * 
	 * @param cmp
	 *            le comparateur utilisé pour définir l'ordre des éléments
	 */
	public ARN(Comparator <? super T> cmp) {
		taille = 0;
		noeudVide = new Noeud();
		racine = noeudVide;
		this.cmp = cmp;
	}
	/**
	 * Constructeur par recopie. Crée un arbre qui contient les mêmes éléments
	 * que c. L'ordre des éléments est l'ordre naturel.
	 * 
	 * @param c
	 *            la collection à copier
	 */
	public ARN(Collection<? extends T> c) {
		this();
		addAll(c);
	}
    /*
    Methode pour faire une rotation des noeud sur la 
    diréction droit vers gauche
     */
    private void rotationgauche(Noeud z) {
        Noeud y = z.droit;
        z.droit = y.gauche;
        if (y.gauche != noeudVide)
            y.gauche.pere = z;
        y.pere = z.pere;
        if (z.pere == noeudVide)
            racine = y;
        else if (z.pere.gauche == z)
            z.pere.gauche = y;
        else
            z.pere.droit = y;
        y.gauche = z;
        z.pere = y;
    }
    /*
    Methode pour faire une rotation des noeud sur la
    diréction gauche vers droit
     */


    private void rotationdroite(Noeud z) {
        Noeud y = z.gauche;
        z.gauche = y.droit;
        if (y.droit != noeudVide)
            y.droit.pere = z;
        y.pere = z.pere;
        if (z.pere == noeudVide)
            racine = y;
        else if (z.pere.droit == z)
            z.pere.droit = y;
        else
            z.pere.gauche = y;
        y.droit = z;
        z.pere = y;
    }
    private void ajouterCorrection(Noeud z) {
        while (z != racine && z.pere.couleur == Color.RED) {
            if (z.pere == z.pere.pere.gauche) {
                Noeud y = z.pere.pere.droit;
                if (y.couleur == Color.RED) {
                    z.pere.couleur = Color.BLACK;
                    y.couleur = Color.BLACK;
                    z.pere.pere.couleur = Color.RED;
                    z = z.pere.pere;
                } else {
                    if (z == z.pere.droit) {
                        z = z.pere;
                        rotationgauche(z);
                    }
                    z.pere.couleur = Color.BLACK;
                    z.pere.pere.couleur = Color.RED;
                    rotationdroite(z.pere.pere);
                }
            } else {
                Noeud y = z.pere.pere.gauche;
                if (y.couleur == Color.RED) {
                    z.pere.couleur = Color.BLACK;
                    y.couleur = Color.BLACK;
                    z.pere.pere.couleur = Color.RED;
                    z = z.pere.pere;
                } else {
                    if (z == z.pere.gauche) {
                        z = z.pere;
                        rotationdroite(z);
                    }
                    z.pere.couleur = Color.BLACK;
                    z.pere.pere.couleur = Color.RED;
                    rotationgauche(z.pere.pere);
                }
            }
        }
        racine.couleur = Color.BLACK; // pour que la racine soit toujours noire
    }
	/**
	 * Ajouter un noeud
	 * @param z
	 * */
	public void ajouter(Noeud e) {
        Noeud b = noeudVide;
        Noeud a = racine;
        while (a != noeudVide) {
            b = a;
            if (cmp.compare(e.cle, a.cle) < 0) {
                a = a.gauche;
            } else
                a = a.droit;
        }
        e.pere = b;
        if (b == noeudVide) {
            racine = e;
        } else {
            if (cmp.compare(e.cle, b.cle) < 0) {
                b.gauche = e;
            } else
                b.droit = e;
        }
        e.gauche = e.droit = noeudVide;
	}
    /**
     * add pour addAll
     */

    public boolean add(T e) {
        Noeud n = new Noeud(e);
        ajouter(n);
        ajouterCorrection(n);
        return true;
    }
    public Iterator<T> iterator() {
        return new ABRIt();
    }
    public int size() {
        return taille;
    }
    public boolean contains(Object o) {
        T element = (T) o;
        Noeud n = new Noeud(element);
        return rechercher(element) != noeudVide;
    }
    /**
     * Supprime le noeud z.
     */

    private Noeud delete(Noeud z) {
        Noeud a,b;
        if (z.gauche == noeudVide || z.droit == noeudVide) {
            b = z;
        } else {
            b = z.suivant();
        }

        if (b.gauche == noeudVide) {
            a = b.droit;
        } else
        	a = b.gauche;
            a.pere = b.pere;
        if (b.pere == noeudVide)
            racine = a;
        else {
            if (b != b.pere.gauche)
            	b.pere.droit = a;
            else
            	b.pere.gauche = a;
        }
        if (cmp.compare(b.cle, z.cle) != 0)
            z.cle = b.cle;
        recycle(b);
        
        // si le nœud supprimé est noir, il faut corriger l’arbre
        if ( b.couleur == Color.BLACK) {
        	enleverCorec (a);
        }
        return a;
    }
    /*
     * reorganisation de l'abrRNre, en remontant vers la racine
     
     */

    private void enleverCorec (Noeud z) {
        while (z != racine && z.couleur == Color.BLACK) {
            if (z == z.pere.gauche) {
                Noeud y = z.pere.droit;
                if (y.couleur == Color.RED) {
                    y.couleur = Color.BLACK;
                    z.pere.couleur = Color.RED;
                    rotationgauche(z.pere);
                    y = z.pere.droit;
                }
                if (y.gauche.couleur == Color.BLACK && y.droit.couleur == Color.BLACK) {
                    y.couleur = Color.RED;
                    z = z.pere;
                } else {
                    if (y.droit.couleur == Color.BLACK) {
                        y.gauche.couleur = Color.BLACK;
                        y.couleur = Color.RED;
                        rotationdroite(y);
                        y = z.pere.droit;
                    }
                    y.couleur = z.pere.couleur;
                    z.pere.couleur = Color.BLACK;
                    y.droit.couleur = Color.BLACK;
                    rotationgauche(z.pere);
                    break;
                }
            } else {
                Noeud y = z.pere.gauche;
                if (y.couleur == Color.RED) {
                    y.couleur = Color.BLACK;
                    z.pere.couleur = Color.RED;
                    rotationdroite(z.pere);
                    y = z.pere.gauche;
                }
                if (y.droit.couleur == Color.BLACK && y.gauche.couleur == Color.BLACK) {
                    y.couleur = Color.RED;
                    z = z.pere;
                } else {
                    if (y.gauche.couleur == Color.BLACK) {
                        y.droit.couleur = Color.BLACK;
                        y.couleur = Color.RED;
                        rotationgauche(y);
                        y = z.pere.gauche;
                    }
                    y.couleur = z.pere.couleur;
                    z.pere.couleur = Color.BLACK;
                    y.gauche.couleur = Color.BLACK;
                    rotationdroite(z.pere);
                    break;
                }
            }
        }
        z.couleur = Color.BLACK;  // pour que la racine soit toujour noire
    }
    public boolean remove(Object o) {
        Noeud e = new Noeud((T) o);
        Noeud a = rechercher(e);

        if (a == noeudVide)
            return false;
        else if (delete(a) != noeudVide);
        return true;
    }
    /**
    
     * cette methode est utlisée par la méthode delete pour delete un noeud
     */

    public void recycle(Noeud x) {
        x.pere = noeudVide;
        x.gauche = noeudVide;
        x.droit = noeudVide;
    }
    private class ABRIt implements Iterator<T>{
    	private Noeud prec;
    	private Noeud pos;
    	public ABRIt(){
    		pos = racine.minimum();
    		prec = noeudVide;
    	}
    	public T next() {
    		if(pos == noeudVide) {
    			throw new NoSuchElementException(" plus d'élements ");
    		}
    		prec = pos;
    		pos = pos.suivant();
    		return prec.cle;
    	}
    	public boolean hasNext() {
    		return (pos != noeudVide);
    	}
    	public void remove() {
    		if(prec == noeudVide) {
    			throw new IllegalStateException();
    		}
    		pos = delete(prec);
    		prec = noeudVide;
    	}
    }
    /* Pour un meilleur affichage
     * @see java.util.AbstractCollection#toString()
     */

    // @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        toString(racine, buffer, "", maxStrLen(racine));
        return buffer.toString();}
    private void toString(Noeud x, StringBuffer buffer, String path, int n) {
        if (x == noeudVide)return;
        toString(x.droit, buffer, path + "D", n);
        for (int i = 0; i < path.length(); i++) {
            for (int j = 0; j < n + 6; j++)
                buffer.append(' ');
            char c = ' ';
            if (i == path.length() - 1)
                c = '+';
            else if (path.charAt(i) != path.charAt(i + 1))
                c = '|';
            buffer.append(c);
        }
        if (x.couleur == Color.BLACK) {
            buffer.append("  --- " + x.cle.toString() + "B");}
        else {
            buffer.append("  --- " + x.cle.toString() + "R");}
        if (x.gauche != noeudVide || x.droit != noeudVide) {
            buffer.append("  ---");
            for (int j = x.cle.toString().length(); j < n; j++)
                buffer.append('-');
            buffer.append('|');}
        buffer.append("\n");
        toString(x.gauche, buffer, path + "G", n);}
    private int maxStrLen(Noeud x) {
        return x == noeudVide ? 0 : Math.max(x.cle.toString().length(), Math.max(maxStrLen(x.gauche), maxStrLen(x.droit)));
    }
}
