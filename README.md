# JeVeuxMonVaccin

Un projet [libGDX](https://libgdx.com/) généré par [gdx-liftoff](https://github.com/tommyettinger/gdx-liftoff).

# Origine

Début février 2021, il était compliqué en Bretagne d'obtenir un rendez-vous pour se faire vacciner.
Il fallait regarder régulièrement sur 3 sites différents en espérant qu'un créneau se libère.

L'expérience utilisateur de vérifier s'il y a des places fut dans mon cas toujours anxiogène, souvent décevante,
très chronophage et probablement lourde en charge pour les serveurs des plateformes en question.

En Bretagne, lorsqu'en semaine 11 un centre de vaccination comme Lesneven ouvre 200 créneaux de vaccinations pour la semaine 13, il faut moins d'une heure
pour que tous ceux-ci aient trouvé preneur.
Probablement que des centaines de personnes iront le reste de la journée/semaine  vérifier les disponibilités
sans savoir que leur chance est globalement passée jusqu'à la semaine suivante.

Il y avait probablement quelquechose qui pouvait améliorer la situation... De là est née l'idée de cette application.

# Objectif

Fournir une application Androïd qui peut prévenir les utilisateurs que le  centre de vaccination
qui les intéresse est disponible pour les vacciner.

Au début il s'agit d'une validation du principe en Bretagne avant de voir plus grand.

Cette application dépend donc des 3 plateformes commerciales : 
 - Doctolib.fr
 - Keldoc.fr
 - Maiia.com
qui proposent les prises de rendez-vous pour la vaccination et qui sont contactées pour valider, ou pas,  la promotion de leurs sites respectifs et l'utilisation partielle de leurs API.
   
L'ARS Bretagne sera aussi contactée pour obtenir son aval nécessaire à l'éventuelle publication
de l'application sur le Google Play Store eu égard aux contraintes imposées par Google.

Le but est techniquement de diriger quelques utilisateurs sur les plateformes quand
une place est disponible en agissant de manière équitable entre les utilisateurs. 

Comme cela 
   1- il y a moins de charge pour les sites assurant le service. 
             (un centre est surveillé chaque 1(=x)  minute(s) pour tous les utilisateurs côté serveur x à définir par les plateformes)
   2- il y a moins de stress dans le processus car on essaie la prise de rendez-vous uniquement quand il y a une chance de l'obtenir.

## Espoir

Au moment d'écrire ces lignes 8% à mi-mars 2021 de la population française est vaccinée, 


l'utilité de cette application est certainement éphémère. Elle est amenée à disparaître rapidement une fois que le nombre de vaccin disponible a augmenté.

## Contraintes 

Cette application se veut totalement gratuite (sans publicité, sans frais, sans coût d'aquisition) et open-source.
Le but est d'ajouter un service pour aider une partie de la population dans ce processus.

La licence à respecter est GPL v2, (cela veut dire par exemple que vous pouvez utiliser ce code mais si vous le modifiez,
vous devez alors publier votre version du code sous le même type de licence, il vous est en plus demandé de prévenir l'auteur original 
de cette application qui pourra s'il le désire intégrer votre code au sien contact@torr-penn.bzh ).
Cette licence ne s'applique pas aux sociétés Doctolib, Keldoc et Maiia qui peuvent utiliser ce code comme bon leur semble. 


## Termes et conditions

Les différents appels aux API du site torr-penn.com  ne sauraient être réalisés sans autorisation en dehors de l'usage par
l'application JeVeuxMonVaccin afin de pouvoir garantir une qualité de service maîtrisée aux utilisateurs.

Il est possible de contribuer à cette application via gitHub.  
Cette application nécessite du code serveur hébergé sur Heroku. Sur demande le code serveur sera transmis 
gracieusement à des développeurs pour permettre la configuration d'autres régions par d'autres développeurs.

Hormis cette introduction et la classe principale, le reste de ce projet est en anglais.



## Gradle
This project uses [Gradle](http://gradle.org/) to manage dependencies. Gradle wrapper was included, so you can run Gradle tasks using `gradlew.bat` or `./gradlew` commands. Useful Gradle tasks and flags:

- `--continue`: when using this flag, errors will not stop the tasks from running.
- `--daemon`: thanks to this flag, Gradle daemon will be used to run chosen tasks.
- `--offline`: when using this flag, cached dependency archives will be used.
- `--refresh-dependencies`: this flag forces validation of all dependencies. Useful for snapshot versions.
- `android:lint`: performs Android project validation.
- `build`: builds sources and archives of every project.
- `cleanEclipse`: removes Eclipse project data.
- `cleanIdea`: removes IntelliJ project data.
- `clean`: removes `build` folders, which store compiled classes and built archives.
- `eclipse`: generates Eclipse project data.
- `idea`: generates IntelliJ project data.
- `lwjgl3:jar`: builds application's runnable jar, which can be found at `lwjgl3/build/libs`.
- `lwjgl3:run`: starts the application.
- `test`: runs unit tests (if any).

Note that most tasks that are not specific to a single project can be run with `name:` prefix, where the `name` should be replaced with the ID of a specific project.
For example, `core:clean` removes `build` folder only from the `core` project.