import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
/**
 * @author Lazaar El Mahdi
 * @version 2023
 * */
/**
 * <p>
 * Implantation de l'interface Collection basée sur les arbres binaires de
 * recherche. Les éléments sont ordonnés soit en utilisant l'ordre naturel (cf
 * Comparable) soit avec un Comparator fourni à la création.
 * </p>
 * 
 * <p>
 * Certaines méthodes de AbstractCollection doivent être surchargées pour plus
 * d'efficacité.
 * </p>
 * 
 * @param <E>
 *            le type des clés stockées dans l'arbre
 */
public class ABR<E> extends AbstractCollection<E> {
	private Noeud racine;
	private int taille;
	private Comparator<? super E> cmp;

	private class Noeud {
		E cle;
		Noeud gauche;
		Noeud droit;
		Noeud pere;

		Noeud(E cle) {
			// TODO
			this.cle = cle;
		}
		public void setCle(E c) {
			this.cle = c;
		}

		/**
		 * Renvoie le noeud contenant la clé minimale du sous-arbre enraciné
		 * dans ce noeud
		 * 
		 * @return le noeud contenant la clé minimale du sous-arbre enraciné
		 *         dans ce noeud
		 */
		Noeud minimum() {
			// TODO
			Noeud e = this;
			while(e.gauche != null) {
				e = e.gauche;
			}
			return e;
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
			if(n.droit != null) {
				return n.droit.minimum();
			} else {
				Noeud e = n.pere;
				while( e != null && n == e.droit) {
					n = e;
					e = e.pere;
				}
				return e;
			}
		}
	}

	// Consructeurs

	/**
	 * Crée un arbre vide. Les éléments sont ordonnés selon l'ordre naturel
	 */
	public ABR() {
		// TODO
        this.racine = null;
        cmp = (x,y) -> ((Comparable<E>)x).compareTo(y);
	}

	/**
	 * Crée un arbre vide. Les éléments sont comparés selon l'ordre imposé par
	 * le comparateur
	 * 
	 * @param cmp
	 *            le comparateur utilisé pour définir l'ordre des éléments
	 */
	public ABR(Comparator<? super E> cmp) {
		// TODO
		this.cmp=cmp;
	}

	/**
	 * Constructeur par recopie. Crée un arbre qui contient les mêmes éléments
	 * que c. L'ordre des éléments est l'ordre naturel.
	 * 
	 * @param c
	 *            la collection à copier
	 */
	public ABR(Collection<? extends E> c) {
		// TODO
		this();
		addAll(c);
	}

	@Override
	public Iterator<E> iterator() {
		return new ABRIterator();
	}

	@Override
	public int size() {
		return taille;
	}

	// Quelques méthodes utiles
	public boolean addAll(Collection<? extends E> c) {
		for(E element : c) {
			add(element);
		}
		return true;
	}

	/**
	 * Recherche une clé. Cette méthode peut être utilisée par
	 * {@link #contains(Object)} et {@link #remove(Object)}
	 * 
	 * @param o
	 *            la clé à chercher
	 * @return le noeud qui contient la clé ou null si la clé n'est pas trouvée.
	 */
	Noeud rechercher(Object o) {
		// TODO
		Noeud a = racine;
		while(a != null && cmp.compare(a.cle,(E)o) != 0) {
			a = cmp.compare(a.cle,(E)o) > 0 ? a.gauche : a.droit;
		}
		return a;
	}

	/**
	 * Supprime le noeud z. Cette méthode peut être utilisée dans
	 * {@link #remove(Object)} et {@link Iterator#remove()}
	 * 
	 * @param z
	 *            le noeud à supprimer
	 * @return le noeud contenant la clé qui suit celle de z dans l'ordre des
	 *         clés. Cette valeur de retour peut être utile dans
	 *         {@link Iterator#remove()}
	 */
    public boolean supprimer(Object o) {
        Noeud a = rechercher(o);
        if (a == null) {
            return false;
        } else {
            supprimer(a);
            return true;
        }
    }
    private Noeud supprimer(Noeud z) {
        Noeud a, b, c;
        if (z.gauche == null || z.droit == null) {
            b = z;
        } else {
            b = z.suivant();
        }
        if (b.gauche != null) {
            a = b.gauche;
        } else {
            a = b.droit;
        }
        if (a != null) {
            a.pere = b.pere;
        }
        if (b.pere == null) {
            racine = a;
        } else {
            if (b == b.pere.gauche) {
                b.pere.gauche = a;
            } else {
                b.pere.droit = a;
            }
        }
        if (b != z) {
            z.cle = b.cle;
        }
     // Renvoie le nœud suivant après la suppression
        return b.suivant();
    }
	/**
	 * Les itérateurs doivent parcourir les éléments dans l'ordre ! Ceci peut se
	 * faire facilement en utilisant {@link Noeud#minimum()} et
	 * {@link Noeud#suivant()}
	 */
	private class ABRIterator implements Iterator<E> {
		private Noeud prec, suiv;
		public ABRIterator() {
			prec = null;
			if(racine == null) {
				suiv = null;
			} else {
				suiv = racine.minimum();
			}
		}
		public boolean hasNext() {
			// TODO
			return suiv != null;
		}
		public E next() {
			// TODO
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
           prec = suiv;
           suiv = suiv.suivant();
           return prec.cle;
		}

		public void remove() {
			// TODO
			if(prec == null) {
				throw new IllegalStateException();
			}
			suiv = supprimer(prec);
			prec = null;
		}
	}

	// Pour un "joli" affichage

	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		toString(racine, buf, "", maxStrLen(racine));
		return buf.toString();
	}

	private void toString(Noeud x, StringBuffer buf, String path, int len) {
		if (x == null)
			return;
		toString(x.droit, buf, path + "D", len);
		for (int i = 0; i < path.length(); i++) {
			for (int j = 0; j < len + 6; j++)
				buf.append(' ');
			char c = ' ';
			if (i == path.length() - 1)
				c = '+';
			else if (path.charAt(i) != path.charAt(i + 1))
				c = '|';
			buf.append(c);
		}
		buf.append("-- " + x.cle.toString());
		if (x.gauche != null || x.droit != null) {
			buf.append(" --");
			for (int j = x.cle.toString().length(); j < len; j++)
				buf.append('-');
			buf.append('|');
		}
		buf.append("\n");
		toString(x.gauche, buf, path + "G", len);
	}

	private int maxStrLen(Noeud x) {
		return x == null ? 0 : Math.max(x.cle.toString().length(),
				Math.max(maxStrLen(x.gauche), maxStrLen(x.droit)));
	}
	// TODO : voir quelles autres méthodes il faut surcharger
    @Override
    public boolean add(E z) {

        Noeud a,b, c = new Noeud(z);
        b = null;
        a = racine;
        while (a != null) {
            b = a;
            a = cmp.compare(c.cle, a.cle) < 0 ? a.gauche : a.droit;
        }
        c.pere = b;
        // Arbre vide
        if (b == null) {
            racine = c;
        } else {
            if (cmp.compare(c.cle, b.cle) < 0)
                b.gauche = c;
            else
                b.droit = c;
        }
        c.gauche = c.droit = null;

        return true;
    }
}