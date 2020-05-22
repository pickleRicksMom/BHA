// Body represents a body or object in space with 3D-position, velocity and mass.
// This class will be used by the executable class 'Space'. A body moves through space according to
// its inertia and - if specified - an additional force exerted on it (for example the
// gravitational force of another mass).
public class Body {
    private double mass;
    private double x, y, z;         //Position of body
    private double vx, vy, vz;      //Velocity

    //Setzt die Postion von Body in Meter
    public void setPosition(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    //Setzt den Bewegungsvektor in meter pro sekunde
    public void setVelocity(double vx, double vy, double vz) {
        this.vx = vx;
        this.vy = vy;
        this.vz = vz;
    }

    //setzt die Masse von Body in kg
    public void setMass(double mass) {
        this.mass = mass;
    }

    //Gibt die masse zurück
    public double getMass() {
        return mass;
    }

    //nach einer sec hat sich das objekt (vx, vy, vz) bewegt)
    //Diese wird zur aktuellen Position addiert
    public void move() {
        setPosition(x +vx, y + vy, z + vz);
    }

    //der neue Bewegungsvektor wird aus dem momentanen + (fx,fy,fz) / mass berechnet
    //danach wird die methhode move() aufgerufen, um das objekt zu bewegen
    public void move(double fx, double fy, double fz) {
        setVelocity(vx + fx/mass, vy + fy/mass, vz + fz/mass );
        move();
    }

    public void resetForce() {
        vx = 0;
        vz = 0;
        vz = 0;
    }

    //gibt x-kooridnate zurück
    public double getX() {
        return x;
    }

    //gibt y-kooridnate zurück
    public double getY() {
        return y;
    }

    //gibt z-kooridnate zurück
    public double getZ (){
        return z;
    }

    //gibt den bewegungsvektor vz zurück
    public double getVz() {
        return vz;
    }
}
