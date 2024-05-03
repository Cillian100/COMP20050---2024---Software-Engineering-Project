package ie.ucd.comp20050.entity;

import ie.ucd.comp20050.MathUtils;
import java.util.TreeSet;

/**
 * Atom entity.
 */
public class Atom {

    /**
     * Index of hexagon where Atom is located.
     */
    private int hexagon;

    private double mx;
    private double my;
    //these two should be changed reallu tbh
    private double circleradius;
    private double ringradius;

    private boolean mcollided = false;
    //distance from centre of one hexagon to another
    private double mhexagondistance;

    public static final double EXIT_180=-4 ,EXIT_ABSORB = -3,DOUBLE = -2,ABSORB = -1;
    /**
     * Constructor.
     * @param hexagonInput integer, index of Atom's hexagon
     */
    public Atom(int hexagonInput) {
        hexagon = hexagonInput;
    }
    //need for collisions
    public Atom(double x,double y,double circleradiusi,double ringradiusi,double hexagondistancei, int hexagonInput) {
        mx = x;
        my = y;
        circleradius = circleradiusi;//50;
        mhexagondistance = hexagondistancei;
        ringradius =ringradiusi;//100;
        hexagon = hexagonInput;
    } //@TODO hexagondistancei is uneeded for this just needed for way draw is implemented rn

    public double getX(){
        return mx;
    }

    public void change(double x, double y, int hexagonInput){
        mx = x;
        my = y;
        hexagon = hexagonInput;
    }

    /**
     * Retrieve the index of the Atom's hexagon
     * @return integer, hexagon index
     */
    public int getHexagon() {
        return hexagon;
    }

    //here because it's kinda specific to collision function
    private double formatAngle(double angle,double dx,double dy)
    {
        if (dy <= 0 && dx <= 0)//2nd quad
        {
            return  -180 - angle;
        }
        else if (dy >= 0 && dx <= 0)//3rd quad
        {
             return 180 - angle;
        }

        else //1st or 4th quad
        {
             return  angle * -1;
        }
    }


    public double collide(LaserRay l, TreeSet<Double> hexxs, TreeSet<Double> hexys)
    {
double laserradius = l.getRadius();
        double bounce;
        float modifier = 1.0f;
        double x = l.getPosX();
        double y = l.getPosY();
        double oolddir = l.getDirection();
        double o = Math.toRadians(oolddir);

       //if they dont collide no effect
       if (!MathUtils.twoCircleColl(x,y,mx,my,ringradius,laserradius)) return 0;

       if (MathUtils.twoCircleColl(x,y,mx,my,circleradius,laserradius)){
            return EXIT_ABSORB;
        }
        // if atom moving away it has already collided, or it is at edge
        double durian = MathUtils.squareDouble(y - my) + MathUtils.squareDouble(x - mx);
        double t4 = y + 20 * Math.sin(o);
        double t5 = x -  20 * Math.cos(o);
        double duri = MathUtils.squareDouble(t4 - my) + MathUtils.squareDouble(t5 - mx) ;
        if (duri > durian) {
            if (!mcollided) {
                mcollided = true;
                return EXIT_180;
            }
            return 0;
        }

        mcollided = true;

        //centre ball in current hexagon
        x = MathUtils.closestValue(x -5 * Math.cos(o),hexxs);
        y = MathUtils.closestValue(y +5 * Math.sin(o),hexys);


        //angle between line between laser is at right now and x axis
        double dy = y - my;
        double dx =   x - mx;
        double slope = dy/dx;
        double angle = Math.atan(slope);
        angle = Math.toDegrees(angle);
        angle = formatAngle(angle,dx,dy);
        angle = MathUtils.convertToNormal(angle);
        oolddir = MathUtils.convertToNormal(oolddir);
       //if it has absorbed previously multply by 2
        if (l.getCollideStatus() == LaserRay.CollideState.absorb ) modifier = 2;
        //it has collide not absorb
        else if ( l.getCollideStatus() == LaserRay.CollideState.bounce) modifier = 3;

        //if angle is the same as path the laser is going into the atom
        if (Math.abs(angle - oolddir) <4 )
        {


            //if already collided multiply previous by 2
         if ( l.getCollideStatus() == LaserRay.CollideState.bounce) {
                return DOUBLE;
            }
            else {
                bounce = ABSORB;
            }
            l.setCollideStatus(LaserRay.CollideState.absorb);
        }
        else {
            //the bad one collides with atom
            double hh = Math.toRadians(oolddir + 60);
            double tmpx = x - mhexagondistance * Math.cos(hh);
            double tmpy = y + mhexagondistance * Math.sin(hh);
            //if using 60 collides with atom return -60
            if (MathUtils.twoCircleColl(tmpx,tmpy,mx,my,circleradius,laserradius))
                bounce =  -60;
            else {
                bounce = 60;
           }
        }

        //laser has collided with one atom, not absorbed
        if  (bounce != ABSORB)
            l.setCollideStatus(LaserRay.CollideState.bounce);

        return bounce * modifier;
    }
    /**
     * Set the index of the Atom's hexagon
     * @param value integer, new hexagon index
     */
    public void setHexagon(int value) {
        hexagon = value;
    }

    /**
     * Set the Atom's collision status
     * @param value boolean, updated collision status
     */
    public void setCollideStatus(boolean value) {
        mcollided = value;
    }

}
