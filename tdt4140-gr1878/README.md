#Prosjektbeskrivelse 

	Vi har bestemt oss for å utvikle en applikasjon som motiverer for fysisk aktivitet blant en gruppe mennesker. Programmet skal tracke 		brukernes bevegelse i form av sykling eller løping. En gruppe kan være en vennegjeng, et arbeidskontor eller en familie som samarbeider 	mot 	et felles mål om å nå et antall oppsamlede poeng. Hver bruker samler poeng til gruppene de er medlem av; 2 poeng for hvert antall 		kilometer løpt og 1 poeng for hvert antall kilometer syklet. På gruppesiden i programmet vil det være mulig å se hvor langt gruppen har 	igjen for å nå målet. Prosjektet er et studentprosjekt.

#Hvordan sette opp utviklingsmiljø: :white_check_mark::
	For å sette opp utviklingsmiljøet starter du med å clone git repoet.
	- For å clone med https: https://gitlab.stud.iie.ntnu.no/tdt4140-2018/78.git
	- For å clone med ssh:   git@gitlab.stud.iie.ntnu.no:tdt4140-2018/78.git

	For å kjøre prosjektet lokalt på pcen trenger du Java og et integrert utviklingsmiljø (som eclipse) installert.
	For at applikasjonen skal fungere forbi første side (innlogging) må du være tilkoblet NTNU sitt internett "eduroam".
	Utviklet ved bruk av Eclipse men andre IDEs burde alså funke. Du trenger maven til en standallone install, men burde også ha maven i Eclipse, means of the m2e plugins 
	Det ble burkt, Java 8 and JavaFX. Maven automatisk compiler alle hirarikene. 

Brukt med: 

fx-map-control (in FxMapControl folder): Fork av github

tdt4140.gr1878.app.ui  (in app.ui folder): Main.java er appen.

tdt4140.gr1878.web.server (in web.server folder): Web server + REST API (Ikke fullført)

#For å kjøre testene: 
mvn test og mvn integration-test for å kjøre disse. 

#Built with
[Maven](https://maven.apache.org) - Dependency Management
Jackson - JSON library
HSQLDB - embedded database with SQL support
FX-Map-Control
TestFX - test framework for JavaFX apps
Jetty - embedded HTTP server

#Hvordan starte programmet:
	Kjør filen "Main.java" som er lokalisert i "/tdt4140.gr1878.app.ui/src/main/java/tdt4140/gr1878/app/ui/Main.java" som en Java-applikasjon.
	
**tester**
	
#Authors
	Ahsan Azim
	Fredrik Svendsen
	Janna Hovstad
	Maciej Suchecki
	Mathilde Skeide Ruth
	Nora Strømberg Brask
	Sigrid Eie Ledsaak
	
