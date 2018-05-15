import java.io.File;
import java.io.IOException;
import java.io.*;
import java.awt.Color;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import edu.princeton.cs.introcs.StdDraw;
import java.util.ArrayList;



public class Main {
    static ArrayList <Territory> Territories = new ArrayList();

    public static void main(String[] args) {
        ReadTheFileHarry();
        //Fenetre fen = new Fenetre();
        //map();
    }

    public static void map() {
        int width = 1125;
        int height = 558;
        StdDraw.setCanvasSize(width,height);
        StdDraw.setXscale(0, width);
        StdDraw.setYscale(0, height);
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("map_Yellow_1125.jpg"));
        } catch (IOException e) {
        }
        while (true) {
            StdDraw.picture(width/2, height/2, "map3.jpg");
            // mouse click
            if (StdDraw.isMousePressed()) {
                //StdDraw.clear();
                // mouse location
                double x = StdDraw.mouseX();
                double y = StdDraw.mouseY();
                int positionX = (int) x;
                int positionY = (height - (int) y); //change Y origin

                //System.out.println(positionX);
                //System.out.println(positionY);
                Color color = new Color(img.getRGB(positionX, positionY));
                System.out.println(color); //Permet d'afficher la couleur sur la carte


                StdDraw.setPenColor(color);
                StdDraw.filledCircle(x, y, 20);
                StdDraw.setPenColor(Color.white);
                StdDraw.circle(x, y, 20);
                StdDraw.show(1);
                StdDraw.clear();
                StdDraw.pause(100);
                String nigga = WhatsTerritoryNigga(color);
                System.out.println(nigga);
            }

        }
    }

    public static String WhatsTerritoryNigga(Color color) {

        for(int i=0; i<Territories.size(); i++)
        {
            if(color.equals(Territories.get(i).getColor()))
            {
                System.out.println(Territories.get(i).getName());
            }
        }

        return " " ;

    }

    public static void  ReadTheFileHarry () {

        try {
            String currentLine;
            BufferedReader br = new BufferedReader(new FileReader("territoires.txt"));  // FileNotFoundException
            while(( currentLine=br.readLine())!=null)
            {
                String[] line = currentLine.split(",", 2); //Permet de séparer le nom du pays avec la couleur
                String country = line[0]; //Met dans une variable le nom du pays
                String color_str = line[1]; //Met la couleur dans une variable
                String[] color_line = color_str.split(","); //Sépare les 3 composantes de la couleur
                String FirstColor = color_line[0].substring(7, color_line[0].length()); //Permet de récupérer la composante r
                String Secondcolor = color_line[1].substring(1,color_line[1].length());
                String ThirdColor = color_line[2].substring(1, color_line[2].length() - 1); //Permet de récupérer la composante b

                int r = Integer.parseInt(FirstColor); //Permet de convertir en int
                int g = Integer.parseInt(Secondcolor);
                int b = Integer.parseInt(ThirdColor);
                Color color = new Color(r,g,b);

                ArrayList adjacent = FindMyMates(country);

                Territory territory = new Territory(country,adjacent,color);
                Territories.add(territory);

            }
        }

        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static ArrayList FindMyMates(String country) {
        ArrayList Adjacents = new ArrayList();
        try {
            String currentLine_adj;
            BufferedReader br_adj = new BufferedReader(new FileReader("adjacents.txt"));  // FileNotFoundException


            while(( currentLine_adj=br_adj.readLine())!=null)
            {
                String[] line_adj = currentLine_adj.split(","); //Permet de séparer le nom du pays avec les adjacents
                if(country == line_adj[0])
                {
                    String[] adjacent = line_adj[1].split(",");
                    for(int i=0; i< adjacent.length;i++)
                    {
                        String nameOfAdjacent = adjacent[i];
                        Adjacents.add(nameOfAdjacent);
                    }

                }
            }
        }

        catch (Exception e) {
            e.printStackTrace();
        }

        return Adjacents;
    } //Permet de trouver les pays adjacents à un pays
}
