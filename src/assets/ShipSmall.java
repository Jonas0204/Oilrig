package assets;

public class ShipSmall extends Ship {

    //@author Matthias
    @Override
    public boolean checkCapacity(int currently, int numberofworker) {
        if ((currently + numberofworker) <= 50) {
            return true;
        }
        return false;
    }
}
