# README - Implémentation de l'interface Collection basée sur les Arbres Rouge-Noir

Ce projet propose une implémentation de l'interface Collection en utilisant des arbres rouge-noir. L'objectif principal est de garantir le bon fonctionnement de toutes les méthodes dans divers scénarios et conditions, en assurant une couverture exhaustive des cas possibles.

## Implémentation de l'Arbre Rouge-Noir

L'implémentation de cette structure de données repose sur les principes des arbres rouge-noir, offrant des opérations de collection telles que l'ajout, la suppression et la recherche optimisée.

### Tests exhaustifs

Pour assurer la robustesse de l'implémentation, des tests complets et variés ont été effectués. Ces tests couvrent différents scénarios d'insertion, de suppression et de recherche, garantissant ainsi le bon fonctionnement de chaque méthode dans toutes les situations imaginables.

## Étude expérimentale de performances

Cette étude compare les performances des arbres binaires de recherche classiques (ABR) avec celles des arbres rouge-noir (ARN) pour différentes valeurs de `n`, représentant le nombre de clés.

### Construction des arbres

Pour chaque valeur de `n`, un ABR et un ARN ont été construits, contenant des clés comprises entre 0 et `n-1`. Deux scénarios ont été testés : 
- Cas moyen : insertion des clés dans un ordre aléatoire
- Cas défavorable pour les ABR : insertion des clés de la plus petite à la plus grande

### Mesures de performances

1. **Temps de construction des arbres** : Mesure du temps nécessaire pour construire les arbres pour différentes tailles `n`.
2. **Temps de recherche des clés** : Mesure du temps nécessaire pour rechercher les clés de 0 à `2n-1` (avec `n` clés présentes dans l'arbre et `n` clés absentes).

### Résultats et visualisation

Les résultats des mesures ont été consignés et utilisés pour tracer des courbes illustrant les temps d'exécution en fonction de la taille de l'arbre `n`. Ces courbes permettent de visualiser et de comparer les performances relatives des ABR et des ARN dans différents scénarios.


---

**Note :** Les détails spécifiques sur les résultats obtenus seront disponibles dans les documents associés, tels que les rapports de performances ou les fichiers de résultats pour une analyse plus approfondie.
