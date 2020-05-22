import javax.print.DocFlavor;
import javax.swing.text.AsyncBoxView;

public class Octree {
    public CelestialBody body;
    private Octree[] children;
    private double totalMass;
    private double centerX, centerY, centerZ;
    private double lenght;


    public Octree(CelestialBody body, double lenght){
        this.body = body;
        children  = new Octree[8];
        this.lenght = lenght;
        totalMass = body.mass;
        centerX = body.position.x;
        centerY = body.position.y;
        centerZ = body.position.z;
    }

    public void add(CelestialBody body, double lenght, double x, double y, double z) {
        //Returns the index, in which the body is put
        int index = compare(lenght, x, y, z);
        int index2 = -1;
        if (this.body.getName().equals("Body")) {
            //index2: if there is already a "Body" in this octant, they both get new index
            index2 = compare(lenght, this.body.position.x, this.body.position.y, this.body.position.z);
            if (index != index2) {
                centerOfMass(body);
                children[index2] = new Octree(this.body, lenght / 2);
                this.body = new CelestialBody("Oktant", totalMass, 0,
                        centerOfMass(), new Vector3(0,0,0), StdDraw.BLACK);
                children[index] = new Octree(body, lenght / 2);
                return;
            }
            centerOfMass(body);
            //If these two bodies end up wie same index, this octant will be named "Oktankt" to check if it is an external
            //node or not and centerOfMass will update the needed information
            children[index]= new Octree(this.body, lenght/2);
            this.body = new CelestialBody("Oktant", totalMass, 0,
                    centerOfMass(), new Vector3(0,0,0), StdDraw.BLACK);
            children[index].add(body, lenght/2, x/2, y/2, z/2);
            return;
        }
        if (children[index] != null) {
            centerOfMass(body);
            /*this.body = new CelestialBody("Oktant", totalMass, 0,
                    centerOfMass(), new Vector3(0,0,0), StdDraw.BLACK);*/
            children[index].add(body, lenght/2, x/2, y/2, z/2);

            return;
        }
        children[index] = new Octree(body, lenght/2);
        centerOfMass(body);
        /*this.body = new CelestialBody("Oktant", totalMass, 0,
                centerOfMass(), new Vector3(0,0,0), StdDraw.BLACK);*/
        return;
    }


    public boolean isFarEnoughAway(CelestialBody body) {
        //double a and b only here to check if it is right
        //can be deleted afterwards
        //lenght/distance < theta
        double a = lenght / (this.body.distanceTo(body));
        double b = this.body.distanceTo(body);
        return lenght / (this.body.distanceTo(body)) < 0.5;
    }


    public Vector3 updateForce(Vector3 force, CelestialBody body) {
        //If the node is a body, calculate the force
        if (this.body.getName().equals("Body")) {
            if (this.body == body) {
                return force;
            }
            if (this.body != body) {
                return force.plus(body.gravitationalForce(this.body));
            }
        //If the node is an octant that isFarEnoughAway, calculate the octants the force extended on the body
        //Theta is 0.5 like in https://www.cs.princeton.edu/courses/archive/fall03/cs126/assignments/barnes-hut.html
        } else if (isFarEnoughAway(body)) {
            return force.plus(body.gravitationalForce(this.body));
        } else {
            for (int i = 0; i < 8; i++) {
                if (children[i] != null) {
                    force =  force.plus(children[i].updateForce(force, body));
                }
            }
        }
        return force;
    }

    private int compare(double lenght, double x, double y, double z) {
        if (x > lenght/2){
            if (y > lenght/2) {
                if (z > lenght/2) {
                    return 0;
                } else {
                    return 1;
                }
            } else {
                if (z > lenght/2) {
                    return 2;
                } else {
                    return 3;
                }
            }
        } else {
            if (y > lenght / 2) {
                if (z > lenght / 2) {
                    return 4;
                } else {
                    return 5; //******************************
                }
            } else {
                if (z > lenght / 2) {
                    return 6;
                } else {
                    return 7;
                }
            }
        }
    }

    private void centerOfMass(CelestialBody body) {
        //Updates center of mass everytime a body is added
        centerX *= totalMass;
        centerY *= totalMass;
        centerZ *= totalMass;
        totalMass += body.mass;
        centerX = (centerX + body.position.x*body.mass) / totalMass;
        centerY = (centerY + body.position.y*body.mass) / totalMass;
        centerZ = (centerZ + body.position.z*body.mass) / totalMass;
    }

    //returns Vector3 of centerOfMass
    public Vector3 centerOfMass() {
        Vector3 centerOfMass = new Vector3(centerX, centerY, centerZ);
        return centerOfMass;
    }

}
