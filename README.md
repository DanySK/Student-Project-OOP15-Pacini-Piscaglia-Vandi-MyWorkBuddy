# My Workout Buddy #

Il gruppo si pone come obiettivo quello di realizzare un'applicazione orientata agli appassionati di fitness e  al sollevamento pesi.

## ANALISI DEI REQUISITI ##
In Italia sempre più giovani e adulti si dedicano all'attività fisica per mantenersi in forma, mantenere un benessere psicofisico liberando la mente dallo stress accumulato durante la giornata. Il fitness richiede costanza e dedizione alla disciplina per questo va praticata seguendo le giuste indicazioni e tenendo traccia del proprio rendimento e dei progressi raggiunti.
L'obiettivo di questa applicazione è di aiutare gli utenti a monitorare i propri miglioramenti, fornendo consigli e programmi di allenamento adeguati alle proprie capacità ed esigenze.

## ANALISI DEL PROBLEMA ##
Per perseguire gli obiettivi prefissati abbiamo svolto un'attenta analisi dei requisiti e abbiamo deciso si sviluppare le seguenti funzionalità

* Creazione di un allenamento personalizzato da parte dell'utente sulla base delle proprie capacità e caratteristiche fisiche
* Calcolo dell'indice di massa corporea ([BMI](https://en.wikipedia.org/wiki/Body_mass_index))
* Esposizione di grafici per
    * Tener traccia dei valori del proprio fisico
    * Tener traccia dell'aumento della propria forza
* Aggiornamento costante da parte dell'utente dei propri risultati
* Possibilità di programmare il proprio allenamento settimanale

### FEATURE OPZIONALI ###
* Calcolo del proprio metabolismo basale
* Calcolo del proprio indice di massa grassa
* Calcolo del proprio indice di massa magra

### CHALLENGE ###
* Creazione di un API pubblica per il recupero e l'inserimento di dati in un DBMS non relazionale ([MongoDB](https://www.mongodb.org))
* Ricerca e inserimento dei vari esercizi per la composizione dell'allenamento
* Creazione dei relativi grafici con libreria ancora da identificare

## SUDDIVISIONE DEL LAVORO ##
* Mattia Vandi: Model e creazione API per interazione con MongoDB
* Nicola Piscaglia: View e creazione dei grafici
* Lorenzo Pacini: Controller e statistiche