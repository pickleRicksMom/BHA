import java.awt.*;
    public class Simulation {

    // gravitational constant
    public static final double G = 6.6743e-11;

    // one astronomical unit (AU) is the average distance of earth to the sun.
    public static final double AU = 150e9;

    //Lenght of the Sides from the universe
    public static final double LENGTh = 4*AU;

    // all quantities are based on units of kilogram respectively second and meter.

    // The main simulation method using instances of other classes.

        public static void main(String[] args) {

        CelestialBody sun = new CelestialBody("Body", 1.989e30, 696340e3,
                new Vector3(2*AU,2*AU,0),
                new Vector3(0,0,0), StdDraw.YELLOW);

        CelestialBody earth = new CelestialBody("Body", 5.972e24, 6371e3,
                new Vector3(148e9+2*AU,2*AU,0),
                new Vector3(0,29.29e3,0), StdDraw.BLUE);

        CelestialBody mercury = new CelestialBody("Body", 3.301e23, 2.4397e3,
                new Vector3(-46.0e9+2*AU,2*AU,0),
                new Vector3(0,-47.87e3,0), StdDraw.RED);

        CelestialBody venus = new CelestialBody("Body", 4.87 * 1024,6.0518e3,
                new Vector3(-72.3e9+2*AU, 2*AU, 0),
                new Vector3(0, -35.01e3, 0), StdDraw.BOOK_LIGHT_BLUE);

        CelestialBody mars = new CelestialBody("Body", 6.42 * 1023,3.3895e3,
                new Vector3(-167.3e9+2*AU, 2*AU, 0),
                new Vector3(0, -24.13e3, 0), StdDraw.BOOK_RED);



        StdDraw.setCanvasSize(500, 500);
        StdDraw.setXscale(0,LENGTh);
        StdDraw.setYscale(0,LENGTh);
        double pixelWidth = 4*LENGTh/500;
        StdDraw.enableDoubleBuffering();
        StdDraw.clear(StdDraw.BLACK);


        CelestialBody[] bodies = new CelestialBody[5];
        bodies[0] = sun;
        bodies[1] = earth;
        bodies[2] = mercury;
        bodies[3] = venus;
        bodies[4] = mars;



        CelestialSystem sys = new CelestialSystem();
        Vector3[] forceOnBodyy = new Vector3[bodies.length];

        for (int i = 0; i < bodies.length; i++) {
            sys.add(bodies[i]);
        }

        double seconds = 0;

        // simulation loop
        //Ist die Simulation aus der Übung
        while(true) {

            seconds++; // each iteration computes the movement of the celestial bodies within one second.

            // for each body (with index i): compute the total force exerted on it.
            for (int i = 0; i < bodies.length; i++) {
                forceOnBodyy[i] = new Vector3(0,0,0);;
                forceOnBodyy[i] = sys.updateForce(forceOnBodyy[i], bodies[i]);
            }
            for (int i = 0; i < bodies.length; i++) {
                bodies[i].move(forceOnBodyy[i]);
            }

            // show all movements in StdDraw canvas only every 3 hours (to speed up the simulation)
            if (seconds%(3*3600) == 0) {
                // clear old positions (exclude the following line if you want to draw orbits).
                StdDraw.clear(StdDraw.BLACK);

                // draw new positions
                for (int i = 0; i < bodies.length; i++) {
                    //bodies[i].print();
                    bodies[i].draw();
                }

                // show new positions
                StdDraw.show();
            }

            //reset sys and bodies again with new position
            sys.reset();
            for (int i = 0; i < bodies.length; i++) {
                sys.add(bodies[i]);
            }
        }

    }

}
