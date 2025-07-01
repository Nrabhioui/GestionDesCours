# Gestion d'École

## Présentation
Cette application Java permet de gérer les ressources d'une école (professeurs, groupes, locaux, cours) à travers une interface graphique conviviale. Elle propose des fonctionnalités de gestion, d'import/export et de sauvegarde des données.

## Fonctionnalités principales
- Gestion des professeurs, groupes, salles de classe et cours
- Ajout, suppression, modification des entités
- Sérialisation/désérialisation des données (sauvegarde/chargement)
- Import/export CSV des différentes entités
- Interface graphique (Swing)
- Authentification utilisateur

## Structure du projet
- `model/entity` : Entités principales (Professor, Group, Classroom, Course, ObjetsContainer)
- `model/GestionFichiers` : Gestion de la sérialisation et import/export
- `controller` : Logique métier et gestion des actions utilisateur
- `view/GUI` : Interface graphique utilisateur (fenêtres, dialogues)

## Lancement de l'application
1. Ouvrez le projet dans votre IDE Java préféré (IntelliJ, Eclipse, NetBeans...)
2. Assurez-vous d'avoir au moins Java 17 installé
3. Lancez la classe `main` située dans `hepl.garage.view.GUI.main`

```
hepl.garage.view.GUI.main
```

## Dépendances
- Java 17 ou supérieur
- Bibliothèques standards Java (Swing, Collections, etc.)

## Utilisation
- Naviguez dans l'interface pour gérer les professeurs, groupes, locaux et cours
- Utilisez les boutons pour importer/exporter les données ou sauvegarder l'état actuel
- L'accès à certaines fonctionnalités peut nécessiter une authentification

## Auteurs
- Nassim et Salah

## Remarques
- Les données sont sauvegardées automatiquement lors de certaines actions
- En cas de problème, vérifiez les fichiers de sauvegarde générés dans le dossier du projet

