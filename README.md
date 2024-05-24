# Scrapeur pour BOURSE DIRECT :bank:

## Contexte
Le site [Bourse Direct](https://www.boursedirect.fr/) est un broker français 
qui permet d'investir en actions sur différents marchés financiers.  

Le site possède une interface très correcte, l'appli mobile est pratique aussi.
Mais le site ne propose pas d'API pour "juste savoir combien".

Mon but d'obtenir cette information est de pouvoir l'utiliser sur un affichage de mon choix, 
comme un afficheur LCD (Arduino, ESP32 etc ..), ou une appli web. 

Ainsi, vous pouvez suivre votre patrimoine de la même manière que vous suivez (peut-être) 
la température dans votre habitat. Inutile donc indispensable.

Ce projet a pour fonction de scraper les informations de VOTRE compte ("Portefeuille Temps Réel").
D'où la nécessité de fournir SES identifiants. 
Vérifiez le code, rien ne m'est envoyé.

(Un seul p à scraper -- voir https://welovedevs.com/fr/articles/web-scraping-definition/)


## Technologies
J'ai choisi d'utiliser la librairie [Playwright](https://playwright.dev) qui utilise un navigateur en sous-main. 
Mon code utilise Firefox. Le mot-clef est #autoformation (sans compte CPF :grin: )

## Mon délire / Usage perso
Cette librairie sera utilisée par une appli web java (quarkus, ou spring boot) qui servira le résultat en JSON.
L'appel se fera dans une application python avec Streamlit 
et un formulaire pour la saisie de l'identification
et qui affichera de jolis charts.

Eventuellement, dans un joli PDF qui historisera les résultats des demandes successives.
Et puis, pourquoi pas une historisation en base de données

## Disclaimer
L'aspect *performance* n'est pas **du tout** pris en compte.

De même que l'occupation d'espace disque car à la première utilisation, les navigateurs 
sont téléchargés.

Ai-je parlé de la consommation de RAM ? Non, normal, une faible consommation n'est pas 
un des buts de cette librairie. 

Quoi ? (En 2024,) tu n'as pas 16 :heart: et 32Go de RAM ? ¯\_(ツ)_/¯ 

Message à mes chers étudiants à qui j'ai rabâché l'importance des tests unitaires 
pendant tout le module de Java, je vous félicite si vous avez remarqué combien mes TU sont faibles.

L'explication est simple, les cours évoluent en continu, je m'assure juste que
* le portefeuille a au moins un compte
* les positions, s'il y en a, ne doivent pas avoir de champs à null 

Pour exécuter le TU, il faut mettre à jour le fichier de *bdc.properties* avec un code de double authentification valide.
Le fichier *bdc.properties_exemple* vous servira de modèle.

Pendant le développement, j'ai effectué de nombreuses connexions. 
Mon compte n'a pas été bloqué.

## Licence
Pas d'utilisation commerciale sans accord et paiement préalables. Générosité appréciée.
:gb: :us: No commercial use without agreement and payment. Generosity appreciated.


# Disclaimer :gb: :us: 
As BourseDirect is (afaik) mostly used by :fr: French users, 
I believe only French will use this library. 
Consequently, even the code is in French. Oh la la.  

# Disclaimer :fr:
Pas de garanties, utilisez à vos risques et périls :smile:
Lors du développement, mes nombreux appels n'ont pas causé 
de blocage de mon compte car ils ont tous abouti.
Est-ce que BD ne détecte pas de nombreuses connexions successives ? Etais-je sous le seuil ?

Si vous exécutez l'API avec un mauvais passcode, un blocage pourrait être possible. 

Si le design du site change, tous les xpath seront à revoir.

## TODO-liste
* détecter si le compte est bloqué
* "faire confiance à cet appareil"
* quid des appels répétés selon une fréquence au choix ?
* mettre les xpaths en annotation des champs, pour réduire le code

## Playwright 
Une expression anglaise sympathique est "the shit writes itself".
Playwright propose en enregistreur de navigation qui permet d'établir la base de son scrapping.

Exécuter dans le répertoire du projet

``
mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="codegen https://www.boursedirect.fr"
``

Et vous verrez le code s'écrire au rythme de vos clics.

Dans ce projet, cela m'aura aidé mais ... les iframes avec des sources php, tu connais !
(la liste des comptes était absente quel que soit le xpath -- les joies du code) ... les iframes avec des ID qui changent ...
Bref.

Jetez un oeil à  https://playwright.dev/java/docs/codegen pour découvrir cette fonction.

## Support 
Ce projet ayant nécessité (trop de) mon temps personnel, si vous êtes un riche utilisateur  
alors soyez généreux avec un bon gars.