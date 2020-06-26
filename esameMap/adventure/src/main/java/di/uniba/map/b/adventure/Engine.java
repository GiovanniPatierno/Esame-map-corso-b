/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package di.uniba.map.b.adventure;

import di.uniba.map.b.adventure.games.FireHouseGame;
import di.uniba.map.b.adventure.parser.Parser;
import di.uniba.map.b.adventure.parser.ParserOutput;
import di.uniba.map.b.adventure.type.AdvObject;
import di.uniba.map.b.adventure.type.CommandType;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.sql.*;


public class Engine {

    private final GameDescription game;

    private final Parser parser;

    public Engine(GameDescription game) {
        this.game = game;
        try {
            this.game.init();
        } catch (Exception ex) {
            System.err.println(ex);
        }
        parser = new Parser();
    }

    public void run() throws SQLException {

        System.out.println("");
        System.out.println(game.getCurrentRoom().getName());
        System.out.println("================================================");
        System.out.println(game.getCurrentRoom().getDescription());
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            List<AdvObject> allObject = new ArrayList<>();
            allObject.addAll(game.getCurrentRoom().getObjects());
            if (game.getObjectsInObject() != null) {
                allObject.addAll(game.getObjectsInObject().getList());
            }
            String command = scanner.nextLine();
            ParserOutput p = parser.parse(command, game.getCommands(), game.getCurrentRoom().getObjects(), game.getInventory());
            if (p.getCommand() != null && p.getCommand().getType() == CommandType.FINE) {
                System.out.println("Addio!");
                break;
            } else {
                game.nextMove(p, System.out);
                System.out.println("================================================");
            }
        }
    }


    public static void main(String[] args) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "jr", "");
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("select DESCRIPTION from DESCRIZIONI where ID = 1;");
        while(rs.next()){
            System.out.println(rs.getString(1));
        }
        rs.close();
        stm.close();
        conn.close();
        Engine engine = new Engine(new FireHouseGame());
        engine.run();
    }
}

