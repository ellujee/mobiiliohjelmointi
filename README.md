Viikko 1: Perusteet
Ensimmäisen viikon tavoitteena oli luoda sovelluksen perusrakenne ja käyttöliittymä.

-Tehtävänä oli tehdä sovellus, jossa on lista tehtävistä.
-Käyttäjä voi merkata tehtäviä tehdyiksi, lisätä uusia, poistaa niitä ja lajitella listaa.

Tässä on yksinkertainen ja selkeä README.md-tiedosto, jonka voit kopioida suoraan GitHub-projektisi etusivulle. Se on jaoteltu viikoittain toiveidesi mukaan.

Tehtävälista-sovellus (Android Compose)
Tämä on harjoitusprojekti, jossa on kehitetty Android-sovellus tehtävien hallintaan. Sovellus on rakennettu viikko kerrallaan opitun perusteella.

Viikko 2: ViewModel ja tilanhallinta
Viikkotehtävä 2 tavoitteena oli päivittää aiempaa sovellusta niin, että se käyttää ViewModelia.

-Composessa näyttö päivittyy automaattisesti, kun siellä oleva tieto muuttuu.
-Lista tehtävistä on tallennettu ViewModeliin mutableStateOf-muuttujaan.
-Kun listaan lisätään tehtävä tai checkboxia painetaan, Compose huomaa muutoksen ja näyttää näytön heti uudestaan uusilla tiedoilla.
-Remember muistaa asiat vain niin kauan kuin sovellus pysyy samassa tilassa. Jos esimerkiksi käännät puhelimen pystyasennosta vaaka-asentoon, remember unohtaa kaiken ja lista tyhjenee.
-ViewModel pysyy tallessa, vaikka näyttöä käännettäisiin tai sovelluksessa tapahtuisi muita muutoksia. Se on siis turvallisempi paikka säilyttää tärkeää tietoa kuin pelkkä remember.

Viikko 3: Arkkitehtuuri (MVVM)
Viikolla 3 sovellus siivottiin noudattamaan virallista MVVM-arkkitehtuuria ja mukaan lisättiin tehtävien muokkausdialogi.

MVVM (Model-View-ViewModel) on tapa jakaa koodi kolmeen osaan:
-Model: Sisältää tiedon (esim. Task-luokka).
-View: Näyttää tiedon käyttäjälle (Composables, kuten HomeScreen).
-ViewModel: Toimii siltana näiden välillä. Se hoitaa logiikan ja säilyttää tiedon.
MVVM tekee sovelluksesta selkeämmän ja helpommin testattavan. Se erottaa käyttöliittymän ja logiikan toisistaan, jolloin koodia on helpompi lukea ja laajentaa ilman, että kaikki on yhdessä tiedostossa.

StateFlow toiminta
-Tässä sovelluksessa tiedon välitykseen käytetään StateFlow-muuttujia.
-StateFlow on kuin tietovirta, joka pitää aina tallessaan uusimman arvon.
-Kun ViewModelissa oleva tieto muuttuu, StateFlow lähettää siitä ilmoituksen käyttöliittymälle.
-Käyttöliittymä tarkkailee tätä tietoa ja päivittää itsensä heti, kun uutta tietoa tulee.

Käyttöohje:

1. Lataa koodi koneellesi tai hae se Gitistä.
2. Avaa projekti Android Studiossa.
3. Valitse yläpalkista vihreä Run-nappi.
4. Sovellus aukeaa emulaattoriin
