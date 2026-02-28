# Al-Mizan - Application Mobile Android

Application mobile Android pour la plateforme nationale algérienne des marchés publics Al-Mizan (الميزان).

## 📱 À propos

Al-Mizan est une plateforme conforme à la Loi n° 23-12 relative aux marchés publics et à la Loi n° 18-07 sur la protection des données en Algérie. Cette application mobile permet aux soumissionnaires et services contractants d'accéder facilement aux appels d'offres nationaux.

## 🎨 Design

L'application utilise une charte graphique professionnelle avec:
- **Palette Principale**: Navy Sovereign (#0A1628), Blue Royal (#1E3A5F), Gold Al-Mizan (#C5A572)
- **Palette Pastel**: Bleu Principal (#6B8CAE), Beige Doré (#E8D5B7), Bleu Clair (#A8C5E0)
- **UI Framework**: Jetpack Compose avec Material Design 3
- **Typography**: Sans Serif système (peut être remplacé par Inter pour le français et IBM Plex Sans Arabic pour l'arabe)

## 🏗️ Architecture

```
app/
├── src/main/java/dz/gov/almizan/
│   ├── MainActivity.kt
│   ├── navigation/
│   │   └── Navigation.kt
│   ├── ui/
│   │   ├── theme/
│   │   │   ├── Color.kt
│   │   │   ├── Type.kt
│   │   │   └── Theme.kt
│   │   ├── components/
│   │   │   └── Components.kt
│   │   └── screens/
│   │       ├── LoginScreen.kt
│   │       ├── HomeScreen.kt
│   │       ├── SearchScreen.kt
│   │       ├── TenderDetailScreen.kt
│   │       └── DashboardScreen.kt
│   └── data/
│       └── (à ajouter: models, repositories, API clients)
```

## 🚀 Fonctionnalités

### Écrans implémentés:

1. **Écran de Connexion** (`LoginScreen`)
   - Authentification par email/NIF et mot de passe
   - Option "Se souvenir de moi"
   - Récupération de mot de passe
   - Design avec gradient navy/bleu

2. **Écran d'Accueil** (`HomeScreen`)
   - Barre de recherche avec filtres
   - Statistiques: AO ouverts, soumissions en cours
   - Actions rapides (Recherche, Favoris, Documents, Aide)
   - Liste des appels d'offres récents
   - Navigation vers le profil et notifications

3. **Écran de Recherche** (`SearchScreen`)
   - Recherche avancée avec mots-clés
   - Filtres: Catégorie (Travaux, Services, Fourniture, Études)
   - Filtres: Statut (OUVERT, EN_COURS, FERMÉ)
   - Tri des résultats
   - Affichage liste avec cartes détaillées

4. **Écran Détail AO** (`TenderDetailScreen`)
   - Onglets: Détails, Documents, Contact
   - Informations complètes: budget, échéance, secteur
   - Description et spécifications techniques
   - Conditions de participation
   - Téléchargement de documents (PDF, XLSX)
   - Informations du service contractant
   - Bouton "Soumettre une offre"
   - Partage et mise en favoris

5. **Tableau de Bord** (`DashboardScreen`)
   - Profil utilisateur
   - Statistiques personnelles
   - Onglets: Mes soumissions, Favoris, Notifications
   - Suivi des soumissions avec statuts
   - Gestion des favoris
   - Centre de notifications

## 🛠️ Technologies

- **Langage**: Kotlin
- **UI**: Jetpack Compose
- **Architecture**: Material Design 3
- **Navigation**: Navigation Compose
- **Dépendances principales**:
  - `androidx.compose.material3` - Material Design 3
  - `androidx.navigation:navigation-compose` - Navigation
  - `retrofit2` - API REST (à configurer)
  - `coil-compose` - Chargement d'images
  - `datastore-preferences` - Stockage local

## 📋 Prérequis

- Android Studio Hedgehog | 2023.1.1 ou plus récent
- JDK 17
- Android SDK API 26+ (Android 8.0 Oreo minimum)
- Gradle 8.0+

## 🔧 Installation

1. Cloner le projet:
```bash
git clone [url-du-repo]
cd app_mobile_android
```

2. Ouvrir avec Android Studio:
   - File > Open
   - Sélectionner le dossier du projet
   - Attendre la synchronisation Gradle

3. Configurer le SDK:
   - File > Project Structure
   - SDK Location: Vérifier le chemin Android SDK

4. Lancer l'application:
   - Run > Run 'app'
   - Ou cliquer sur le bouton Play (▶️)

## 🎯 Configuration requise

### build.gradle.kts (app)
```kotlin
android {
    compileSdk = 34
    defaultConfig {
        minSdk = 26
        targetSdk = 34
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }
}
```

## 📦 Prochaines étapes

### Backend & API:
- [ ] Configurer Retrofit pour les appels API
- [ ] Implémenter les models de données (User, Tender, Submission)
- [ ] Créer les repositories pour la gestion des données
- [ ] Ajouter ViewModels pour la logique métier
- [ ] Implémenter l'authentification JWT

### Fonctionnalités:
- [ ] Système de notifications push (Firebase Cloud Messaging)
- [ ] Téléchargement et visualisation de documents PDF
- [ ] Upload de documents pour soumissions
- [ ] Signature électronique
- [ ] Mode hors-ligne avec cache local
- [ ] Support multilingue (Français/Arabe)
- [ ] Support RTL pour l'arabe

### Polices personnalisées:
1. Télécharger les polices:
   - Inter (Google Fonts)
   - IBM Plex Sans Arabic (Google Fonts)

2. Ajouter dans `res/font/`:
   - `inter_regular.ttf`
   - `inter_semibold.ttf`
   - `inter_bold.ttf`
   - `ibm_plex_sans_arabic_regular.ttf`
   - `ibm_plex_sans_arabic_semibold.ttf`
   - `ibm_plex_sans_arabic_bold.ttf`

3. Mettre à jour `Type.kt` pour utiliser les polices personnalisées

### Sécurité:
- [ ] Chiffrement des données sensibles
- [ ] Certificate pinning pour les API
- [ ] Obfuscation du code (ProGuard/R8)
- [ ] Validation des entrées utilisateur
- [ ] Protection contre les injections

## 📱 Captures d'écran

(À ajouter: screenshots de l'application)

## 🤝 Contribution

Projet académique - 2SIL (4ème année Informatique)

## 📄 Licence

Conforme aux lois algériennes:
- Loi n° 23-12 relative aux marchés publics
- Loi n° 18-07 relative à la protection des personnes physiques dans le traitement des données à caractère personnel

## 👥 Équipe

Projet développé dans le cadre du cursus 2SIL

---

**Al-Mizan** - الميزان
*Plateforme Nationale des Marchés Publics - Algérie*
