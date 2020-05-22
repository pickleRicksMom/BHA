public class CelestialSystem {
    private Octree root;

    public void add(CelestialBody body) {
        if (root == null) {
            root = new Octree(body, Simulation.LENGTh);
        } else {
            root.add(body, Simulation.LENGTh, body.position.x, body.position.y, body.position.z);
        }
    }

    public void reset () {
        root = null;
    }

    public Vector3 updateForce(Vector3 force, CelestialBody body) {
        return root.updateForce(force, body);
    }
}
