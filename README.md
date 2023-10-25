# Configuration de Classes pour GLIA-Airlines

Ce projet a été effectué dans le cadre de l'UE Intelligence Artificielle pour le Génie Logiciel [HAI916I] en Master 2 d'Informatique parcours Génie Logiciel à l'Université de Montpellier en Octobre 2023. Il a été réalisé par GUEYE El Hadji Ahmadou, BOURET Maxime et HENRY Nathan.

## Instructions pour Exécuter le Projet

## Liste des Options

### Option `-h` ou `--help`
- **Description** : Affiche le message d'aide.
- **Argument** : Aucun

### Option `-i` ou `--instance`
- **Description** : Définit l'instance d'avion à résoudre (comprend le nombre de séparateurs, la capacité et les portes de sortie).
- **Argument** : `aircraft instance` - de `inst1` à `inst9`
- **Exemple** : `-i inst3`

### Option `-a` ou `--all`
- **Description** : Trouve toutes les solutions possibles pour l'instance spécifiée.
- **Argument** : Aucun

### Option `-t` ou `--timeout`
- **Description** : Définit une limite de temps pour la résolution.
- **Argument** : `timeout in ms`
- **Exemple** : `-t 5000`

### Option `-c` ou `--with-constraint`
- **Description** : Exécute le résolveur en utilisant le modèle de contrainte Choco.
- **Argument** : Aucun

### Option `-d` ou `--default`
- **Description** : Exécute le résolveur pour toutes les instances sans utiliser le modèle de contrainte.
- **Argument** : Aucun

## Contraintes liées aux Options

- **`-d`** : Cette option doit être utilisé seule
- **`-a`** : Cette option doit être utilisé seulement si l'option **-c** est présente

## Exemples de Lignes de Commande

1. **Afficher le message d'aide**:  
`$ java -jar GLIA-Airlines.jar -h`
2. **Résoudre une instance spécifique (par exemple `inst3`) sans Choco-Solver**:  
`$ java -jar GLIA-Airlines.jar -i inst3`
3. **Trouver toutes les solutions pour une instance spécifique avec Choco-Solver (par exemple `inst5`)**:
`$ java -jar GLIA-Airlines.jar -c -i inst5 -a`
4. **Définir une limite de temps (par exemple 5 secondes) pour résoudre une instance spécifique (par exemple `inst4`)**:
`$ java -jar GLIA-Airlines.jar -i inst4 -t 5000`
5. **Résoudre une instance spécifique avec le modèle de contrainte (par exemple `inst2`)**:
`$ java -jar GLIA-Airlines.jar -c -i inst2`
6. **Résoudre toutes les instances sans utiliser le modèle de contrainte**:
`$ java -jar GLIA-Airlines.jar -d`
