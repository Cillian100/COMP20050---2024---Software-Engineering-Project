package ie.ucd.comp20050.drawing;

import ie.ucd.comp20050.component.Arrow;
import ie.ucd.comp20050.component.Border;
import ie.ucd.comp20050.component.Hexagon;
import ie.ucd.comp20050.entity.Atom;
import ie.ucd.comp20050.entity.LaserRay;

import java.awt.*;
import java.util.ArrayList;

public class Renderer {
    Graphics graph;
    GamePanel gp;
    double modifier;

    int numHexagons;

    Renderer(GamePanel a)
    {
        gp = a;
        graph = a.graph;
        modifier = gp.modifier;
        numHexagons = gp.hexagonCounter2;
    }

    void drawBackGround()
    {
       drawHexagons();
        drawBorder();
    }

    void drawEntities()
    {
        drawAtoms(gp.getAtoms());
        drawRay(gp.getLaser());
    }

    /**
     * Draws the border, and its index, around the game board hexagon
     * @implNote Must be called after hexagon drawing, else border will be painted over
     * @see #drawHexagons()
     */
    private void drawBorder() {
        Border border = gp.border;
        int posPointer = gp.posPointer;
        Arrow[] arr = gp.arr;
        /* Border lines */
        graph.setColor(Color.RED);
        graph.drawPolygon(border.getX(), border.getY(), border.getCountPoints());

        /* Border indexes */
        for(int i = 0; i < border.getCountPoints(); i++) {
            graph.setColor(Color.BLUE);
            if(posPointer == i) graph.setColor(Color.YELLOW); // Colours player pointer yellow

            graph.drawLine(arr[i].getLineX()[0], arr[i].getLineY()[0], arr[i].getLineX()[1], arr[i].getLineY()[1]);
            graph.drawString(Integer.toString(i), arr[i].getLineX()[1], arr[i].getLineY()[1]);
        }

    }

    /**
     * Draws hexagons on the board
     * @implNote Must be called before border drawing
     * @see #drawBorder()
     */
    private void drawHexagons() {
        graph.setColor(Color.pink);
        Hexagon[] hexagons = gp.hexagons;
        for(int a=0;a<numHexagons;a++){
            graph.drawPolygon(hexagons[a].getXInteger(), hexagons[a].getYInteger(), hexagons[a].getNumberOfPoints());
            graph.drawString(Integer.toString(a), (int)hexagons[a].getMiddleX(), (int)hexagons[a].getMiddleY());
        }
    }




    /**
     * Draws laser ray on board
     */
    private void drawRay(LaserRay ray) {
        graph.setColor(Color.YELLOW);
        graph.drawOval((int)ray.getMidX()-5, (int)ray.getMidY()-5, 10, 10);
    }

    /**
     * Draws Atoms on the game board
     * @param list ArrayList<Atom>, input list of Atoms to draw
     */
    private void drawAtoms(ArrayList<Atom> list) {
        if(list == null) return; // Temp. necessary to alleviate bug on macOS @TODO Better explanation
        graph.setColor(Color.blue);
        for(Atom atom : list) {
            int posX = (int) gp.hexagons[atom.getHexagon()].getMiddleX();
            int posY = (int) gp.hexagons[atom.getHexagon()].getMiddleY();
            int size =(int) (175 * modifier);

            graph.fillOval(posX - size/2, posY - size/2, size, size);
            graph.drawOval(posX - size, posY - size, size*2, size*2);
            graph.setColor(Color.red);
            graph.setColor(Color.blue);
        }
    }

    void setGraph(Graphics g) {graph = g;}
}
