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
    private double circleradius;
    private double ringradius;

    private boolean mcollided = false;
    //distance from centre of one hexagon to another
    private double mhexagondistance;

    public static final double EXIT_180=-4 ,EXIT_ABSORB = -3,DOUBLE = -2,ABSORB = -1;
    public Atom(double x,double y,double circleradiusi,double ringradiusi,double hexagondistancei, int hexagonInput) {
        mx = x;
        my = y;
        circleradius = circleradiusi;//50;
        mhexagondistance = hexagondistancei;
        ringradius =ringradiusi;//100;
        hexagon = hexagonInput;
    } //@TODO hexagondistancei is uneeded for this just needed for way draw is implemented rn

    /**
     * Retrieve the index of the Atom's hexagon
     * @return integer, hexagon index
     */
    public int getHexagon() {
        return hexagon;
    }


    /**
     *formats angle of laser based on atom position,here because it's kinda specific to collision function
     * @param angle angle to be formatted
     * @param dx difference between laser x and atom x,in format laserx - atomx
     * @param dy difference between laser y and atom y,in format lasery - atomx
     * @return formatted angle
     */
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

    /**
     * gets angle between laser and x axis
     * @param x xpos of laser
     * @param y ypos of laser
     * @return angle between laser and x axis,in degrees
     */
    double getLaserAndXAxisAngle(double x,double y)
    {
        double dy = y - my;
        double dx =   x - mx;
        double slope = dy/dx;
        double angle = Math.atan(slope);
        angle = Math.toDegrees(angle);
        angle = formatAngle(angle,dx,dy);
        angle = MathUtils.convertToNormal(angle);
        return angle;
    }

    /**
     *
     * @param x xpos of laser
     * @param y ypos of laser
     * @param angle angle of laser
     * @return true if laser moving away from this atom, false otherwise
     */
    boolean LaserMovingAway(double x,double y,double angle)
    {
        double oldDis = MathUtils.squareDouble(y - my) + MathUtils.squareDouble(x - mx);
        double t4 = y + 20 * Math.sin(angle);
        double t5 = x -  20 * Math.cos(angle);
        double newDis = MathUtils.squareDouble(t4 - my) + MathUtils.squareDouble(t5 - mx) ;
        return newDis > oldDis;

    }

    /**
     * checks to see if this atom collides with a laser at a given position and resolves it appropriately
     * @param l laser to check for collision
     * @param hexxs x values of hexagons on board,for centering
     * @param hexys y values of hexagons on board,for centering
     * @return
     */
    public double collide(LaserRay l, TreeSet<Double> hexxs, TreeSet<Double> hexys)
    {
        double laserRadius = l.getRadius();
        double bounce;
        float modifier = 1.0f;
        double x = l.getPosX();
        double y = l.getPosY();
        double laserAngleDegrees = l.getDirection();
        double laserAngleRadians = Math.toRadians(laserAngleDegrees);

       //if they dont collide no effect
       if (!MathUtils.twoCircleColl(x,y,mx,my,ringradius,laserRadius)) return 0;

       if (MathUtils.twoCircleColl(x,y,mx,my,circleradius,laserRadius)){
            return EXIT_ABSORB;
        }
        // if atom moving away it has already collided, or it is at edge
        if (LaserMovingAway(x,y,laserAngleRadians)) {
            if (!mcollided) {
                mcollided = true;
                return EXIT_180;
            }
            return 0;
        }

        mcollided = true;

        //centre ball in current hexagon
        x = MathUtils.closestValue(x -5 * Math.cos(laserAngleRadians),hexxs);
        y = MathUtils.closestValue(y +5 * Math.sin(laserAngleRadians),hexys);


        //angleBetweenLaserAndXAxis between line between laser is at right now and x axis
        double angleBetweenLaserAndXAxis = getLaserAndXAxisAngle(x,y);

        laserAngleDegrees = MathUtils.convertToNormal(laserAngleDegrees);

       //if it has absorbed previously multiply by 2
        if (l.getCollideStatus() == LaserRay.CollideState.absorb ) modifier = 2;
        //it has collide not absorb
        else if ( l.getCollideStatus() == LaserRay.CollideState.bounce) modifier = 3;

        //if angleBetweenLaserAndXAxis is the same as path the laser is going into the atom
        if (Math.abs(angleBetweenLaserAndXAxis - laserAngleDegrees) <4 )
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
            //go in direction away from atom
            double tmpAngle = Math.toRadians(laserAngleDegrees + 60);
            double tmpx = x - mhexagondistance * Math.cos(tmpAngle);
            double tmpy = y + mhexagondistance * Math.sin(tmpAngle);
            //if using 60 collides with atom return -60
            if (MathUtils.twoCircleColl(tmpx,tmpy,mx,my,circleradius,laserRadius))
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
