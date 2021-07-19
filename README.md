# JeVeuxMonVaccin

Un projet [libGDX](https://libgdx.com/) généré par [gdx-liftoff](https://github.com/tommyettinger/gdx-liftoff).

# sur PC Linux :

git clone https://github.com/torr-penn/jeveuxmonvaccin.git
cd jeveuxmonvaccin/lwjgl3 ; gradle run

Ces commandes auront besoin de java, gradle et git.

application publiée en juin sur :https://play.google.com/store/apps/details?id=com.gtasoft.jeveuxmonvaccin&hl=fr

en juillet, ajout des Pays de la Loire.

# Origine

Début février 2021, il était compliqué en Bretagne d'obtenir un rendez-vous pour se faire vacciner. Il fallait regarder régulièrement sur 3 sites différents en espérant qu'un créneau se libère.

L'expérience utilisateur de vérifier s'il y a des places fut dans mon cas toujours anxiogène, souvent décevante, très chronophage et probablement lourde en charge pour les serveurs des plateformes en question.

En Bretagne, lorsqu'en semaine 11 un centre de vaccination comme Lesneven ouvre 200 créneaux de vaccinations pour la semaine 13, il faut moins d'une heure pour que tous ceux-ci aient trouvé preneur. Probablement que des centaines de personnes iront le reste de la journée/semaine vérifier les
disponibilités sans savoir que leur chance est globalement passée jusqu'à la semaine suivante.

Il y avait probablement quelquechose qui pouvait améliorer la situation... De là est née l'idée de cette application.

# Objectif

Fournir une application Androïd qui peut prévenir les utilisateurs que le centre de vaccination qui les intéresse est disponible pour les vacciner.

Au début il s'agit d'une validation du principe en Bretagne avant de voir plus grand.

Cette application dépend donc des 3 plateformes commerciales :

- Doctolib.fr
- Keldoc.fr
- Maiia.com

Ces plateformes proposent les prises de rendez-vous pour la vaccination. Elles sont contactées pour valider, ou pas, la promotion de leurs sites respectifs et l'utilisation partielle de leurs API.

L'ARS Bretagne est aussi contactée pour obtenir son aval nécessaire à l'éventuelle publication de l'application sur le Google Play Store eu égard aux contraintes imposées par Google.

Le but est techniquement de diriger quelques utilisateurs sur les plateformes quand une place est disponible en agissant de manière équitable entre les utilisateurs.

Comme cela

1- il y a moins de charge pour les sites assurant le service.
(un centre est surveillé chaque 1(=x)  minute(s) pour tous les utilisateurs côté serveur x à définir par les plateformes)

2- il y a moins de stress dans le processus car on essaie la prise de rendez-vous uniquement quand il y a une chance de l'obtenir.

## Espoir

Au début du projet 8% à mi-mars 2021 de la population française est vaccinée, mi avril alors qu'une version est en cours de publication et validation mi avril 18% des a reçu sa première dose... il reste encore du monde qui pourra avoir besoin de l'application.

l'utilité de cette application est certainement éphémère. Elle est amenée à disparaître rapidement une fois que le nombre de vaccin disponible a augmenté.

## Contraintes

Cette application se veut totalement gratuite (sans publicité, sans frais, sans coût d'aquisition) et open-source. Le but est d'ajouter un service pour aider une partie de la population dans ce processus.

La licence à respecter est GPL v2, (cela veut dire par exemple que vous pouvez utiliser ce code mais si vous le modifiez, vous devez alors publier votre version du code sous le même type de licence, il vous est en plus demandé de prévenir l'auteur original de cette application qui pourra s'il le
désire intégrer votre code au sien contact@torr-penn.bzh ). Cette licence ne s'applique pas aux sociétés Doctolib, Keldoc et Maiia qui peuvent utiliser ce code comme bon leur semble.

## Termes et conditions

Les différents appels aux API du site torr-penn.com ne sauraient être réalisés sans autorisation en dehors de l'usage par l'application JeVeuxMonVaccin afin de pouvoir garantir une qualité de service maîtrisée aux utilisateurs.

Il est possible de contribuer à cette application via gitHub.  
Cette application nécessite du code serveur hébergé sur Heroku. Sur demande le code serveur sera transmis gracieusement à des développeurs pour permettre la configuration d'autres régions par d'autres développeurs.

Hormis cette introduction, les messsages de l'application et la classe principale, le reste de ce projet est en anglais.

Les données utilisées des sites doctolib, keldoc, maiia ou data.gouv.fr appartiennent à leur producteur respectif qui peuvent autoriser ou pas leur usage par cette application ou demander à tout moment d'arrêter de les mentionner.
(ceci peut être fait sans redéployer une nouvelle version de l'application en retirant les centres concernés des choix possibles du côté server.)

