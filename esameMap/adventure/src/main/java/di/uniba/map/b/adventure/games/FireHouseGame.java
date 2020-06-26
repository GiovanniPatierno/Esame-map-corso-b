/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package di.uniba.map.b.adventure.games;

import di.uniba.map.b.adventure.GameDescription;
import di.uniba.map.b.adventure.parser.ParserOutput;
import di.uniba.map.b.adventure.type.*;

import java.io.PrintStream;
import java.sql.*;
import java.util.Iterator;

/**
 * @author giovanni paolo patierno
 */

public class FireHouseGame extends GameDescription {

    @Override
    public void init() throws Exception {
        //Commands
        Command chiudi = new Command(CommandType.CHIUDI, "chiudi");
        chiudi.setAlias(new String[]{"CHIUDI","c"});
        getCommands().add(chiudi);
        Command avanti = new Command(CommandType.AVANTI, "avanti");
        avanti.setAlias(new String[]{"a", "A", "Avanti", "AVANTI", "Dritto", "DRITO", "DRITTO"});
        getCommands().add(avanti);
        Command iventory = new Command(CommandType.INVENTARIO, "inventario");
        iventory.setAlias(new String[]{"inv", "i", "I"});
        getCommands().add(iventory);
        Command sud = new Command(CommandType.INDIETRO, "indietro");
        sud.setAlias(new String[]{"ind", "dietro", "INDIETRO", "Indietro", "DIETRO"});
        getCommands().add(sud);
        Command est = new Command(CommandType.SINISTRA, "sinistra");
        est.setAlias(new String[]{"s", "S", "Sinistra", "SINISTRA"});
        getCommands().add(est);
        Command ovest = new Command(CommandType.DESTRA, "destra");
        ovest.setAlias(new String[]{"d", "D", "Destra", "DESTRA"});
        getCommands().add(ovest);
        Command look = new Command(CommandType.GUARDA, "osserva");
        look.setAlias(new String[]{"guarda", "vedi", "trova", "cerca", "descrivi"});
        getCommands().add(look);
        Command fine = new Command(CommandType.FINE, "fine");
        fine.setAlias(new String[]{"end"});
        getCommands().add(fine);
        Command pickup = new Command(CommandType.PRENDI, "raccogli");
        pickup.setAlias(new String[]{"prendi"});
        getCommands().add(pickup);
        Command open = new Command(CommandType.APRI, "apri");
        open.setAlias(new String[]{"apri" , "sblocca"});
        getCommands().add(open);
        //Rooms
        Room entrata = new Room(0, "Entrata", "L' entrata di casa, stranamente è più disordinata del solito.. come se qualcuno fosse passato più volte");
        entrata.setLook("sei all' entrata ed è tutto molto buio, con un po di luce che penetra dalle finestre riesci a vedere l armadietto bianco in cui si trovano le chiavi per aprire il quadro elettrico in cucina");

        Room corridoio = new Room(1, "Corridoio", "sei nel corridoio che è quasi completamente buio, a tentoni avanzi per raggiungere il tuo obbiettivo..");
        corridoio.setLook("Il solito vecchio corridoio, di fronte a te c'è la tua meta, la cucina, a destra hai il bagno e a sinistra la tua camera da letto dove ti aspetta la tua console... non è tempo di perdersi in chiacchiere, riattiva la luce cosi potrai andare a giocare al più presto");

        Room cucina = new Room(2, "Cucina", "finalmente sei in cucina, il quadro elettrico è li difronte a te ");
        cucina.setLook("L'unica cosa che riesci a vedere in cucina è il quadro davanti a te, il resto è avvolto nell' oscurità , spero che tu abbia la chiave per aprirlo");

        Room bagno = new Room(3, "Bagno", "sei nel bagno, una piccola luce che penetra dalla finestra risplende sulle mattonelle e una strana calma ti avvolge....ti prendi un momento per riposare ");
        bagno.setLook("la luce della luna ti permette di vedere che vicino al lavandino c'è il cestino dei panni sporchi che volevi mettere in lavatrice prma di andare via...");

        Room cameretta = new Room(4, "La tua cameratta", "eccola qui, la tua cameretta, la tua 'tana',ovviamente disordinata come sempre, per fortuna che nessuno è venuto a trovarti per il tuo compleanno, altrimenti avrebbero capito subito che sei un tipo svogliato per quanto riguarda le faccende");
        cameretta.setLook("Qui vedi il tuo armadio dei vestiti, il tuo letto, i tuoi poster.. ma guardando attentamente sulla scrivania noti un bigliettino");
        //maps
        entrata.setNorth(corridoio);
        corridoio.setNorth(cucina);
        corridoio.setWest(bagno);
        corridoio.setEast(cameretta);
        corridoio.setSouth(entrata);

        cameretta.setWest(corridoio);

        bagno.setEast(corridoio);

        cucina.setSouth(corridoio);

        getRooms().add(cucina);
        getRooms().add(corridoio);
        getRooms().add(entrata);
        getRooms().add(bagno);
        getRooms().add(cameretta);
        //obejcts
        AdvObject chiaveArmadiettoEntrata = new AdvObject(1, "chiaveEntrata", "una chiave con su scritto:'armadietto entrata'...credo sia chiaro il suo scopo", 1);
        chiaveArmadiettoEntrata.setAlias(new String[]{"chiave"});

        AdvObjectContainer cestaPanniSporchi = new AdvObjectContainer(2, "cesta", "la tua cesta dei panni sporchi.... ma all interno c'è uno strano luccichio che ti attira");
        cestaPanniSporchi.setAlias(new String[]{"cesto", "CESTO", "cesta panni sporchi"});
        cestaPanniSporchi.setOpenable(true);
        cestaPanniSporchi.setPickupable(false);
        cestaPanniSporchi.setOpen(false);
        bagno.getObjects().add(cestaPanniSporchi);
        cestaPanniSporchi.add(chiaveArmadiettoEntrata);

        AdvObject chiaveCucina = new AdvObject(4, "chiave Cucina", "vittoria!!.. finalmente puoi aprire il quadro in cucina, cosi' potrai accendere la luce e giocare alla tua nuova console", 2);
        chiaveCucina.setAlias(new String[]{"chiave"});

        AdvObjectContainer armadiettoEntrata = new AdvObjectContainer(3, "armadietto", "l'armadietto in cui è contenuta la chiave della cucina, ma non si aprirà se non hai la sua chiave");
        armadiettoEntrata.setAlias(new String[]{"armadietto", "armadio", "comò"});
        armadiettoEntrata.setOpenable(true);
        armadiettoEntrata.setPickupable(false);
        armadiettoEntrata.setOpen(false);
        entrata.getObjects().add(armadiettoEntrata);
        armadiettoEntrata.add(chiaveCucina);

        AdvObjectContainer quadroelettrico = new AdvObjectContainer(5, "quadro", "il quadro elettrico, inserisci la chiave per aprirlo e poi attiva la corrente");
        quadroelettrico.setAlias(new String[]{"QUADRO", "quadro elettrico"});
        quadroelettrico.setOpenable(true);
        quadroelettrico.setPickupable(false);
        quadroelettrico.setOpen(false);
        cucina.getObjects().add(quadroelettrico);

        AdvObjectContainer armadioCameretta = new AdvObjectContainer(6, "Armadio", "L'armadio dove tieni i vestiti, ti ricorda quello di casa tua quando giocavi con i tuoi fratelli a nascondino");
        armadioCameretta.setAlias(new String[]{"armadio", "Armadio"});
        armadioCameretta.setOpenable(true);
        armadioCameretta.setPickupable(false);
        armadioCameretta.setOpen(false);
        cameretta.getObjects().add(armadioCameretta);

        AdvObject bigliettoCameretta = new AdvObject(8, "biglietto","C'è una scritta sul biglietto del tuo compagno di stanza :'Non trovo più le chiavi dell' armadietto all entrata.'\n"+" probabilmente le hai lasciate in qualche pantalone sporco");
        bigliettoCameretta.setAlias(new String []{"BIGLIETTO","bigliettino", "foglio", "fogliettino"});
        cameretta.getObjects().add(bigliettoCameretta);

        AdvObjectContainer armadiettoBagno = new AdvObjectContainer(9,"armadietto", "Il classico armadietto per il bagno, puoi aprirlo e vedere cosa c'è dentro");
        armadiettoBagno.setAlias(new String[]{"ARMADIETTO","armadio"});
        armadiettoBagno.setOpenable(true);
        armadiettoBagno.setOpen(false);
        armadiettoBagno.setPickupable(false);
        bagno.getObjects().add(armadiettoBagno);

        AdvObject medicinali = new AdvObject(10,"medicinali","i medicinali che hanno tutti in casa, sicuramente non sono quello che stai cercando");
        medicinali.setAlias(new String[]{"medicine","pillole","MEDICINALI"});
        armadiettoBagno.add(medicinali);

        AdvObject spazzolino = new AdvObject(11,"spazzolino","ricordati sempre di lavarti i denti prima di andare a letto, l'igene orale è fondamentale");
        spazzolino.setAlias(new String[]{"SPAZZOLINO"});
        armadiettoBagno.add(spazzolino);

        //set starting room
        setCurrentRoom(entrata);
    }

    @Override
    public void nextMove(ParserOutput p, PrintStream out) throws SQLException {
        boolean noroom = false;
        boolean move = false;
        if (p.getCommand() == null) {
            out.println("Non ho capito cosa devo fare! Prova con un altro comando.");
        } else {

            noroom = false;
            move = false;

            if (p.getCommand().getType() == CommandType.AVANTI) {
                if (getCurrentRoom().getNorth() != null) {
                    setCurrentRoom(getCurrentRoom().getNorth());
                    move = true;
                } else {
                    noroom = true;
                }
            } else if (p.getCommand().getType() == CommandType.INDIETRO) {
                if (getCurrentRoom().getSouth() != null) {
                    setCurrentRoom(getCurrentRoom().getSouth());
                    move = true;
                } else {
                    noroom = true;
                }
            } else if (p.getCommand().getType() == CommandType.SINISTRA) {
                if (getCurrentRoom().getEast() != null) {
                    setCurrentRoom(getCurrentRoom().getEast());
                    move = true;
                } else {
                    noroom = true;
                }
            } else if (p.getCommand().getType() == CommandType.DESTRA) {
                if (getCurrentRoom().getWest() != null) {
                    setCurrentRoom(getCurrentRoom().getWest());
                    move = true;
                } else {
                    noroom = true;
                }
            } else if (p.getCommand().getType() == CommandType.INVENTARIO) {
                out.println("Nel tuo inventario ci sono:");
                for (AdvObject o : getInventory()) {
                    out.println(o.getName() + ": " + o.getDescription());
                }
            } else if (p.getCommand().getType() == CommandType.GUARDA) {
                if (getCurrentRoom().getLook() != null) {
                    out.println(getCurrentRoom().getLook());
                } else {
                    out.println("Non c'è niente di interessante qui.");
                }
            } else if (p.getCommand().getType() == CommandType.PRENDI) {
                if (p.getObject() != null) {
                    if (p.getObject().isPickupable()) {
                        getInventory().add(p.getObject());
                        getCurrentRoom().getObjects().remove(p.getObject());
                        out.println("Hai raccolto: " +p.getObject().getName()+ ": " + p.getObject().getDescription());
                    } else {
                        out.println("Non puoi raccogliere questo oggetto.");
                    }
                } else {
                    out.println("Non c'è niente da raccogliere qui.");
                }
            } else if (p.getCommand().getType() == CommandType.CHIUDI) {
                if (p.getObject() == null) {
                    out.println("non so cosa chiudere.");
                } else {
                    AdvObjectContainer c = (AdvObjectContainer) p.getObject();
                    c.setList(getObjectsInObject().getList());
                    out.println("hai chiuso " + p.getObject().getName());
                    setObjectOpen(false);
                }
            }else if (p.getCommand().getType() == CommandType.APRI) {
                if (p.getObject() == null && p.getInvObject() == null) {
                    out.println("Non c'è niente da aprire qui.");
                }
                if (p.getObject().isOpenable() && p.getObject().isOpen() == false) {
                    if (p.getObject() != null && p.getObject().getId() == 3) {
                        boolean key = false;
                        for (AdvObject o : getInventory()) {
                            if (o.getKey() == 1) {
                                key = true;
                            }
                        }
                        if (key == true) {
                            out.println("hai aperto l'armadietto dell entrata");
                            AdvObjectContainer e = (AdvObjectContainer) p.getObject();
                            if (!e.getList().isEmpty()) {
                                out.print(e.getName() + " contiene:");
                                Iterator<AdvObject> ite = e.getList().iterator();
                                while (ite.hasNext()) {
                                    AdvObject next = ite.next();
                                    getCurrentRoom().getObjects().add(next);
                                    out.print(" " + next.getName());
                                    ite.remove();
                                }
                                out.println();
                            }

                        } else {
                            out.println("non puoi aprire l'armadietto senza la chiave");
                        }
                    }
                }
                if (p.getObject().isOpenable() && p.getObject().isOpen() == false) {
                    if (p.getObject() != null && p.getObject().getId() == 5) {
                        boolean key2 = false;
                        for (AdvObject o : getInventory()) {
                            if (o.getKey() == 2) {
                                key2 = true;
                            }
                        }
                        if (key2 == true) {
                            out.println("hai aperto il quadro elettrico... abbassi la leva e all'imrovviso...\n");
                            end(out);

                        } else {
                            out.println("non puoi aprire l'armadietto senza la chiave");
                        }
                    }
                }
               if (p.getObject() != null && p.getObject().getId() != 3 && p.getObject().getId() != 5) {
                   if (p.getObject().isOpenable() && p.getObject().isOpen() == false) {
                       if (p.getObject() instanceof AdvObjectContainer) {
                           out.println("Hai aperto: " + p.getObject().getName());
                           AdvObjectContainer c = (AdvObjectContainer) p.getObject();
                           setObjectsInObject(c);
                           if (!c.getList().isEmpty()) {
                               out.print(c.getName() + " contiene:");
                               Iterator<AdvObject> it = c.getList().iterator();
                               while (it.hasNext()) {
                                   AdvObject next = it.next();
                                   getCurrentRoom().getObjects().add(next);
                                   out.print(" " + next.getName());
                                   it.remove();
                               }
                               out.println();
                           }setObjectOpen(true);
                       } else {
                           out.println("Hai aperto: " + p.getObject().getName());
                           p.getObject().setOpen(true);
                       }
                   } else {
                       out.println("Non puoi aprire questo oggetto.");
                   }
               }
               if (p.getInvObject() != null && p.getInvObject().getId() != 3 && p.getObject().getId() != 5) {
                   if (p.getInvObject().isOpenable() && p.getInvObject().isOpen() == false) {
                       if (p.getInvObject() instanceof AdvObjectContainer) {
                           AdvObjectContainer c = (AdvObjectContainer) p.getInvObject();
                           if (!c.getList().isEmpty()) {
                               out.print(c.getName() + " contiene:");
                               Iterator<AdvObject> it = c.getList().iterator();
                               while (it.hasNext()) {
                                   AdvObject next = it.next();
                                   getInventory().add(next);
                                   out.print(" " + next.getName());
                                   it.remove();
                               }
                               out.println();
                           }
                       } else {
                           p.getInvObject().setOpen(true);
                       }
                       out.println("Hai aperto nel tuo inventario: " + p.getInvObject().getName());
                   } else {
                       out.println("Non puoi aprire questo oggetto.");
                   }
               }
             }

        }
        if (noroom) {
            out.println("no! non sei ancora un fantasma, purtoppo non puoi ancora attraversare  i muri");
        } else if (move) {
            out.println(getCurrentRoom().getName());
            out.println("================================================");
            out.println(getCurrentRoom().getDescription());
        }
    }


    private void end (PrintStream out) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "jr", "");
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("select DESCRIPTION from DESCRIZIONI where ID = 2;");
        while(rs.next()){
            System.out.println(rs.getString(1));
        }
        rs.close();
        stm.close();
        conn.close();
        System.exit(0);
    }

}


