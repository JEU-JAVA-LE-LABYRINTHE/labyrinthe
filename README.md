# Labyrinthe - Jeu d'Aventure Temporelle

Jeu d'aventure textuel en Java où le joueur voyages à travers les âges pour résoudre des énigmes et récupérer des lettres formant un mot secret.

## 🎮 Caractéristiques

- **Zone Zaman**: Hub central où le joueur gère son inventaire et récupère les 4 artefacts nécessaires
- **Zones Temporelles**: Préhistoire, Égypte Antique, Moyen Âge, Futur (à développer)
- **Système de Commandes**: Interface textuelle intuitive
- **Gestion d'Inventaire**: Capacité limitée avec gestion des objets
- **Architecture MVC**: Séparation claire entre Modèle, Vue et Contrôleur

## 📁 Structure du Projet

```
src/
├── launcher/          # Point d'entrée (Launcher.java)
├── controller/        # Logique de contrôle (JeuController.java)
├── jeu/              # Cœur du jeu (Partie.java, GestionnaireCommandes.java, etc.)
├── zones/            # Implémentation des zones (Zaman.java, Zones.java, CarteZones.java)
├── personnes/        # Entités (Joueur.java, Personne.java, PNJ.java)
├── items/            # Système d'objets (Item.java, Objet.java, Lettre.java)
├── inventaire/       # Gestion de l'inventaire (Inventaire.java)
├── view/             # Interface graphique Swing
├── helper/           # Utilitaires (CommandeHelper.java)
└── utils/            # Constantes (Constants.java)

resources/
├── audio/            # Fichiers audio du jeu
└── images/           # Images des zones
```

## 🚀 Démarrage Rapide

### Prérequis
- Java 16+
- IDE compatible (IntelliJ, Eclipse, VS Code)

### Compilation
```bash
javac -d out src/**/*.java
```

### Exécution
```bash
java -cp out launcher.Launcher
```

## 🎮 Commandes Disponibles

| Commande | Alias | Description |
|----------|-------|-------------|
| OUEST / O | - | Avancer dans le temps |
| EST / E | - | Reculer dans le temps |
| Z | - | Retour direct à Zaman |
| REGARDER / L | - | Décrire la zone actuelle |
| SAC / INVENTAIRE | INV | Afficher le contenu du sac |
| PRENDRE | P | Prendre un objet (ex: PRENDRE Marteau) |
| STATUS / ETAT | STAT | Afficher l'état du jeu |
| TEMPS / T | - | Afficher le temps restant |
| AIDE | H, ? | Afficher l'aide |

## 🏗️ Architecture

### MVC Pattern
- **Model**: `Partie.java`, `Zones.java`, `Joueur.java`, `Inventaire.java`
- **View**: `LabyrinthePanel.java`, `PanneauControle.java`, `AideDialog.java`
- **Controller**: `JeuController.java`

### Packages Clés

#### `zones`
- `Zaman.java` - Zone centrale avec gestion des objets
- `Zones.java` - Classe abstraite pour les zones
- `Prehistoire.java`, `EgypteAntique.java`, `MoyenAge.java`, `Futur.java` - Stubs pour développement

#### `jeu`
- `Partie.java` - Moteur principal du jeu
- `GestionnaireCommandes.java` - Routeur de commandes
- `CarteZones.java` - Navigation entre zones
- `GestionnaireSauvegarde.java` - Sérialisation (stub)
- `GestionnaireSon.java` - Gestion audio (stub)

#### `personnes`
- `Joueur.java` - État du joueur (vies, zone, inventaire)
- `Personne.java` - Classe abstraite pour entités
- `PNJ.java` - Personnages non-joueurs

#### `items`
- `Item.java` - Classe abstraite pour objets
- `Objet.java` - Artefacts à récupérer
- `Lettre.java` - Lettres formant le mot secret

## 📝 État du Développement

✅ **Implémenté**
- Zone Zaman complète
- Système de commandes basique
- Gestion d'inventaire
- Navigation entre zones
- Architecture MVC

🟡 **En Développement**
- Zones temporelles (Préhistoire, Égypte, Moyen Âge, Futur)
- Système de sauvegarde/chargement
- Gestion audio

❌ **Non Implémenté**
- Énigmes des zones
- Coffres et lettres
- Combat/Défis

## 👥 Développement en Équipe

Le projet est structuré pour permettre le développement parallèle:

1. **Zone Préhistoire** - À assigner
2. **Zone Égypte Antique** - À assigner
3. **Zone Moyen Âge** - À assigner
4. **Zone Futur** - À assigner
5. **Système de Sauvegarde** - À assigner
6. **Gestion Audio** - À assigner

Chaque zone hérite de `Zones.java` et implement ses propres énigmes et coffres.

## 📋 Normes de Code

- Langue: Méthodes et variables en français
- Encoding: UTF-8 sans BOM
- Style: Conventions Java standard
- Suppression: Méthodes inutilisées supprimées régulièrement

## 🔐 Git Workflow

```bash
# Créer une branche pour votre zone
git checkout -b feat/zone-nom

# Commit avec messages clairs
git commit -m "Implémentation zone X"

# Push et créer PR
git push origin feat/zone-nom
```

## 📞 Contact & Support

Pour des questions sur l'architecture ou le développement, consultez la structure du projet et les commentaires de code.

---

**Dernière mise à jour**: Avril 2026  
**Version**: 0.1 - Minimaliste Zaman