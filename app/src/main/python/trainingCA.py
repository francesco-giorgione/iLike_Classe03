from nltk.chat.util import Chat, reflections
from modelling.distaceMetrics import calculateDistaceMetrics
import csv

def main(utterances, listaJava):

    # array contenente coppie domanda - risposta delimitate da parentesi quadre
    # r indica ciò ceh l'utente scrive nel bot (utterances), [] indicano la risposta del bot
    pairs = [
        [
            r"We|Ciao|Hey",
            ["Ciao", "Chi si rivede"]
        ],
        [
            # (.*)= REGEX --> qualsiasi parola scriva l’utente, quella sarà racchiusa in questa categoria
            r"mi chiamo(.*)",
            # %1 ci consente di chiamare il primo REGEX
            ["Ciao %1, come va?"],
        ],
        [
            r"(.*)mi chiamo(.*)",
            # %2 ci consente di chiamare il secondo REGEX
            ["Ciao %2, come va?"],
        ],
        [
            r"il mio nome è(.*)",
            ["Ciao %1, come posso aiutarti?"],
        ],
        [
            r"(.*)come stai(.*)",
            ["Bene grazie tu?"],
        ],
        [
            r"(.*)come va(.*)",
            ["Bene grazie a te?"],
        ],
        [
            r"bene|male|benino|potrebbe andare meglio",
            ["Come posso aiutarti?"],
        ],
        [
            r"cosa sai fare?|cosa sei capace di fare?|come mi puoi aiutare?|come mi aiuti?",
            ["So consigliarti dei film!"],
        ],
        [
            r"ok|va bene|okay",
            ["Come posso esserti utile?"],
        ],
        [
            r"consigliami(.*)|mi dai un consiglio(.*)|(.*)consigli(.*)",
            ["Certo! Scegli l'algoritmo che preferisci utilizzare."],
        ],
        [
            r"kms",
            [calculateDistaceMetrics(listaJava, "kms")],
        ],
        [
            r"dbs",
            [calculateDistaceMetrics(listaJava, "dbs")],
        ],
        [
            r"che fai?",
            ["Adesso niente, ma potrei consigliarti un film!"],
        ],
        [
            r"(.*)quale|quale?",
            ["Chiamare algoritmo!!"],
        ],
        [
            r"cosa sai fare?|cosa sei capace di fare?",
            ["So consigliarti dei film"],
        ],
        [
            # comando d'uscita --> definito dalla libreria non è possibile modificare l'input
            r"arrivederci",
            ["A presto!", "Alla prossima", "Arrivederci!"]
        ]
    ]

    chat = Chat(pairs, reflections)  # serve alla libreria per distinguere domande e risposte
    #chat.converse()  # comando per far sì che inizi la conversazione

    return chat.respond(utterances)