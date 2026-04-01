# Ressources Audio - Labyrinthe Temporel

## Structure attendue

Les fichiers audio suivants doivent être placés dans ce dossier pour que le système sonore fonctionne :

### Musiques par zone (normales)
- `zaman.wav` - Musique d'ambiance pour Zaman
- `prehistoire.wav` - Musique d'ambiance pour la Préhistoire
- `egypte_antique.wav` - Musique d'ambiance pour l'Égypte antique
- `moyen_age.wav` - Musique d'ambiance pour le Moyen Âge
- `futur.wav` - Musique d'ambiance pour le Futur

### Musiques accélérées (tension)
Lorsque le temps restant passe sous 30%, ces musiques accélérées se jouent :
- `zaman_acceleree.wav` - Musique accélérée Zaman
- `prehistoire_acceleree.wav` - Musique accélérée Préhistoire
- `egypte_antique_acceleree.wav` - Musique accélérée Égypte antique
- `moyen_age_acceleree.wav` - Musique accélérée Moyen Âge
- `futur_acceleree.wav` - Musique accélérée Futur

### Effets sonores
- `transition.wav` - Jingle de transition entre les zones
- `bord_de_mort.wav` - Signal sonore quand il reste 1 vie
- `reinit_temps.wav` - Son quand le temps se réinitialise en revenant dans une zone

## Format recommandé
- Format : WAV, MP3, ou autre format supporté par Java Audio
- Qualité : 44.1 kHz, 16-bit
- Durée : 
  - Musiques : 2-5 minutes (elles bouclent en continu)
  - Jingles : 1-3 secondes
  - Effets : 0.5-2 secondes

## Intégration automatique
Une fois les fichiers placés ici, le système sonore les chargera automatiquement sans modification du code.
