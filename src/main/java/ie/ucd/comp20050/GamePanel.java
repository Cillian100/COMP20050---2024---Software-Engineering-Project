package ie.ucd.comp20050;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.Math;


class GamePanel extends JPanel implements ActionListener{
    Timer timer;
    QuickMaff maff = new QuickMaff();
    final static int SCREEN_HEIGHT = 9000;
    final static int SCREEN_WIDTH = 900;
    int xConstant=SCREEN_HEIGHT/2;
    int yConstant=SCREEN_WIDTH/2;
    double modifier=maff.getModifier(SCREEN_HEIGHT, SCREEN_WIDTH);
    int x[];
    int y[];
    int counter;

    GamePanel(){
        this.setPreferredSize(new Dimension(SCREEN_HEIGHT, SCREEN_WIDTH));
        this.setBackground(Color.black);
        this.setFocusable(true);
        startGame();
    }

    public void startGame(){
        timer = new Timer(50, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        drawBackground(g);
    }

    public void paintArrows(Graphics g, double angle1, double angle2, int x, int y){
        double test1 = Math.toRadians(angle1);
        double test2 = Math.toRadians(angle2);
        double cos = Math.cos(test1);
        double sin = Math.sin(test2);
        int x1 = x+(int)(sin*100*modifier/2);
        int y1 = y+(int)(cos*100*modifier/2);
        int x2 = x1-(int)(cos*30*modifier);
        int y2 = y1+(int)(sin*30*modifier);

        int[] triX ={
                x1+(int)(cos*30*modifier),
                x1+(int)(sin*16*modifier),
                x1-(int)(sin*16*modifier),
        };
        int[] triY = {
                y1-(int)(sin*30*modifier),
                y1+(int)(cos*16*modifier),
                y1-(int)(cos*16*modifier),
        };
        g.drawLine(x1, y1, x2, y2);
        g.fillPolygon(triX, triY, 3);
    }

    private void drawBackground(Graphics g){
        g.setColor(Color.yellow);
        int numberOfHexagonsX=maff.NumberOfHexagonsX(modifier, SCREEN_WIDTH);
        int numberOfHexagonsY=maff.NumberOfHexagonsY(modifier, SCREEN_HEIGHT);
        xConstant=(int)(SCREEN_HEIGHT-(numberOfHexagonsX)*(174*modifier))/2;
        yConstant=SCREEN_WIDTH/2;

        int firstReference=0;
        boolean flip=true;

        for(int c=0;c<2;c++){
            for(int b=0;b<numberOfHexagonsY;b++){
                for(int a=0;a<numberOfHexagonsX;a++){
                    if(a==0){
                        firstReference=xConstant;
                    }
                    x=maff.xHexagon(xConstant, modifier);
                    y=maff.yHexagon(yConstant, modifier);
                    g.drawPolygon(x, y, 6);

                    if(a==0){
                        paintArrows(g, 0, 0, x[0], y[0]);
                        if(c==0){
                            paintArrows(g, 60, 240, x[1], y[1]);
                        }else{
                            paintArrows(g, 60, 60, x[5], y[5]);
                        }
                    }
                    if(a==numberOfHexagonsX-1){
                        paintArrows(g, 180, 180, x[3], y[3]);
                        if(c==0){
                            paintArrows(g, 240, 240, x[2], y[2]);
                        }else{
                            paintArrows(g, 120, 60, x[4], y[4]);
                        }
                    }
                    if(b==numberOfHexagonsY-1 && c==0){
                        paintArrows(g, 240, 240, x[2], y[2]);
                        paintArrows(g, 60, 240, x[1], y[1]);
                    }
                    if(b==numberOfHexagonsY-1 && c==1){
                        paintArrows(g, 120, 60, x[4], y[4]);
                        paintArrows(g, 60, 60, x[5], y[5]);
                    }
                    xConstant=maff.xHexagonNext(xConstant, modifier,3);
                }
                xConstant=firstReference;
                if(flip==true){
                    xConstant=maff.xHexagonNext(xConstant, modifier, 2);
                    yConstant=maff.yHexagonNext(yConstant, modifier, 2);
                }else{
                    xConstant=maff.xHexagonNext(xConstant, modifier, 5);
                    yConstant=maff.yHexagonNext(yConstant, modifier, 5);
                }
                numberOfHexagonsX=numberOfHexagonsX-1;
            }
            numberOfHexagonsX=maff.NumberOfHexagonsX(modifier, SCREEN_WIDTH);
            yConstant=SCREEN_WIDTH/2;
            xConstant=(int)(SCREEN_HEIGHT-(numberOfHexagonsX)*(174*modifier))/2;
            flip=false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e){
    }
}